package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Teste extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<Leito> leitos = new ArrayList<Leito>();
    ListView listV_leitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Leitos").orderByChild("sid").equalTo("001").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    listV_leitos = (ListView) findViewById(R.id.lista);
                    leitos = new ArrayList<Leito>();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Leito l = objSnapshot.getValue(Leito.class);
                        leitos.add(l);
                    }
                    ArrayAdapter adapter = new LeitoAdapter(getApplicationContext(), leitos);
                    listV_leitos.setAdapter(adapter);
                    listV_leitos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(Teste.this, "Leito: "+leitos.get(i).getSituacao(), Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(Teste.this, DetalheLeitoActivity.class);
                            it.putExtra("leito", leitos.get(i));
                            startActivity(it);
                        }
                    });
                }
                else{
                    Toast.makeText(Teste.this, "Este setor não possui leitos cadastrados.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

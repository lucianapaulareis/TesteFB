package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeitosStatusActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    private String grupo;

    private ArrayList<Leito> leitos = new ArrayList<>();
    //private ArrayAdapter<Leito> arrayAdapter;
    ListView listV_leitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitos_status);

        //Recebendo dados de outra Activity
        Intent it = getIntent();
        String situacao = it.getStringExtra("status");
        grupo = it.getStringExtra("grupo");
        //Toast.makeText(LeitosStatusActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();

        TextView id = (TextView) findViewById(R.id.situacaoLeito);
        id.setText(situacao);
        //listV_leitos = (ListView) findViewById(R.id.listV_leitos);
        eventoDatabase();
    }

    private void eventoDatabase() {
        Intent intent = getIntent();
        String situacao = intent.getStringExtra("status");
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Leitos").orderByChild("situacao").equalTo(situacao).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listV_leitos = (ListView) findViewById(R.id.listV_leitos);
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
                            Intent it = new Intent(LeitosStatusActivity.this, DetalheLeitoActivity.class);
                            it.putExtra("leito", leitos.get(i));
                            it.putExtra("grupo", grupo);
                            it.putExtra("mudar", "mudar");
                            startActivity(it);
                        }
                    });
                } else {
                    Toast.makeText(LeitosStatusActivity.this, "Este setor não possui leitos cadastrados.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

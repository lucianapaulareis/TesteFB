package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Leito> leitos = new ArrayList<>();
    private ArrayAdapter<Leito> arrayAdapterLeito;
    ListView listV_leitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitos_status);

        //Recebendo dados de outra Activity
        Intent it = getIntent();
        String situacao = it.getStringExtra("status");

        TextView id = (TextView) findViewById(R.id.situacaoLeito);
        id.setText(situacao);


        listV_leitos = (ListView) findViewById(R.id.listV_leitos);

        inicializarFirebase();
        eventoDatabase();
    }

    private void eventoDatabase() {
        Intent intent = getIntent();
        String situacao = intent.getStringExtra("status");
        //Query para listar os leitos agrupando-os pela situacao
        databaseReference.child("Leitos").orderByChild("situacao").equalTo(situacao).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leitos.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Leito l = objSnapshot.getValue(Leito.class);
                    leitos.add(l);
                }
                arrayAdapterLeito = new ArrayAdapter<>(LeitosStatusActivity.this, android.R.layout.simple_list_item_1, leitos);
                listV_leitos.setAdapter(arrayAdapterLeito);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(LeitosStatusActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Alterar o App para ler as informações no modelo atualizado de dados

public class ListaLeitosActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Leito> leitos = new ArrayList<>();
    private ArrayAdapter<Leito> arrayAdapterLeito;
    ListView listV_leitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_leitos);

        Intent intent = getIntent();
        Setor setor = intent.getParcelableExtra("setor");
        final String idSetor = setor.getUid();
        String nomeSetor = setor.getNome();
        TextView id = (TextView) findViewById(R.id.idSetor);
        id.setText(idSetor);
        TextView nome = (TextView) findViewById(R.id.nomeSetorId);
        nome.setText(nomeSetor);
        listV_leitos = (ListView) findViewById(R.id.listV_leitos);
        inicializarFirebase();
        eventoDatabase();

        //Evento de clique em um item da listView
        listV_leitos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(ListaLeitosActivity.this, "Leito: "+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaLeitosActivity.this, DetalheLeitoActivity.class);
                intent.putExtra("leito", leitos.get(position));
                //intent.putExtra("idSetor", idSetor);
                startActivity(intent);
            }
        });
    }

    private void eventoDatabase() {
        Intent intent = getIntent();
        Setor setor = intent.getParcelableExtra("setor");
        String idSetor = setor.getUid();
        //Query para listar os leitos de determinado setor, identificando-o pelo "idSetor"
        databaseReference.child("Leitos").orderByChild("sid").equalTo(idSetor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leitos.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Leito l = objSnapshot.getValue(Leito.class);
                    leitos.add(l);
                }
                arrayAdapterLeito = new ArrayAdapter<>(ListaLeitosActivity.this, android.R.layout.simple_list_item_1, leitos);
                listV_leitos.setAdapter(arrayAdapterLeito);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ListaLeitosActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}

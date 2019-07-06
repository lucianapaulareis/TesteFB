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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaSetoresActivity extends AppCompatActivity {

    private List<Setor> setores = new ArrayList<Setor>();
    private ArrayAdapter<Setor> arrayAdapterSetor;
    private String mudar;
    private String grupo;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_setores);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupo");
        mudar = it.getStringExtra("mudar");
        Toast.makeText(ListaSetoresActivity.this, "Grupo: "+grupo, Toast.LENGTH_LONG).show();

        listV_dados = (ListView) findViewById(R.id.listV_dados);

        inicializarFirebase();
        eventoDatabase();

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ListaSetoresActivity.this, ListaLeitosActivity.class);
                intent.putExtra("setor", setores.get(position));
                intent.putExtra("grupo", grupo);
                if(mudar != null){
                    intent.putExtra("mudar", mudar);
                }
                startActivity(intent);
            }
        });
    }

    private void eventoDatabase() {
        databaseReference.child("Setores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Para não ficar sobrescrevendo
                setores.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    //Traz na ordem que estiver no banco, cada um dos objetos Usuario
                    Setor s = objSnapshot.getValue(Setor.class);
                    setores.add(s);
                }

                //Listando dados do firebase na listView
                arrayAdapterSetor = new ArrayAdapter<Setor>(ListaSetoresActivity.this, android.R.layout.simple_list_item_1,setores);
                listV_dados.setAdapter(arrayAdapterSetor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Método para inicializar o Firebase
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(ListaSetoresActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Permite salvar os dados na nuvem, como também dentro do app
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}

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
    private String editar;
    private String excluir;
    private String excluirSetor;
    private String editarSetor;
    ListView listV_dados;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_setores);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupo");
        mudar = it.getStringExtra("mudar");
        editar = it.getStringExtra("editar");
        excluir = it.getStringExtra("excluir");
        excluirSetor = it.getStringExtra("excluirSetor");
        editarSetor = it.getStringExtra("editarSetor");

        //Toast.makeText(ListaSetoresActivity.this, "Editar: "+editar+"\nGrupo: "+grupo, Toast.LENGTH_LONG).show();

        listV_dados = (ListView) findViewById(R.id.listV_dados);

        databaseReference = ConfiguracaoFirebase.getFirebase();
        eventoDatabase();

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(excluirSetor != null){
                    Intent intent = new Intent(ListaSetoresActivity.this, ExcluirSetorActivity.class);
                    intent.putExtra("setor", setores.get(position));
                    intent.putExtra("grupo", grupo);
                    intent.putExtra("excluirSetor", excluirSetor);
                    startActivity(intent);
                }
                else if(editarSetor != null){
                    Intent intent = new Intent(ListaSetoresActivity.this, EditarSetorActivity.class);
                    intent.putExtra("setor", setores.get(position));
                    intent.putExtra("grupo", grupo);
                    intent.putExtra("editarSetor", editarSetor);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(ListaSetoresActivity.this, ListaLeitosActivity.class);
                    intent.putExtra("setor", setores.get(position));
                    if(grupo == null && mudar != null){
                        intent.putExtra("mudar", mudar);
                        startActivity(intent);
                    }

                    else if(editar != null){
                        intent.putExtra("editar", "editar");
                        intent.putExtra("grupo", grupo);
                        startActivity(intent);
                    }

                    else if(excluir != null){
                        intent.putExtra("excluir", excluir);
                        intent.putExtra("grupo", grupo);
                        startActivity(intent);
                    }
                    else if(grupo != null){
                        intent.putExtra("grupo", grupo);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void eventoDatabase() {
        databaseReference.child("Setores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setores.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Setor s = objSnapshot.getValue(Setor.class);
                    setores.add(s);
                }
                arrayAdapterSetor = new ArrayAdapter<Setor>(ListaSetoresActivity.this, android.R.layout.simple_list_item_1,setores);
                listV_dados.setAdapter(arrayAdapterSetor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GerenciaUsuariosActivity extends AppCompatActivity {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayAdapter<Usuario> arrayAdapterUsuario;
    ListView listUsuarios;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerencia_usuarios);

        listUsuarios = (ListView) findViewById(R.id.list_usuarios);
        firebaseDatabase = ConfiguracaoFirebase.getFirebase();
        eventoDatabase();

        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent it = new Intent(GerenciaUsuariosActivity.this, DetalheUsuarioActivity.class);
                it.putExtra("usuario", usuarios.get(position));
                startActivity(it);
            }
        });
    }

    private void eventoDatabase() {
        //Query para listar os leitos de determinado setor, identificando-o pelo "idSetor"
        firebaseDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Usuario usuario = objSnapshot.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
                arrayAdapterUsuario = new ArrayAdapter<>(GerenciaUsuariosActivity.this, android.R.layout.simple_list_item_1, usuarios);
                listUsuarios.setAdapter(arrayAdapterUsuario);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

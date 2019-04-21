package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText edtNome, edtSobrenome;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // Deu certo
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private ArrayAdapter<Usuario> arrayAdpterUsuario;

    // Deu certo
    private List<Setor> setores = new ArrayList<Setor>();
    private ArrayAdapter<Setor> arrayAdapterSetor;


    private List<Leito> leitos = new ArrayList<>();
    private ArrayAdapter<Leito> arrayAdapterLeito;

    // Deu certo
    private List<Situacao> status = new ArrayList<Situacao>();
    private ArrayAdapter<Situacao> arrayAdapterSituacao;


    private List<GrupoUsuario> grupos = new ArrayList<GrupoUsuario>();
    private ArrayAdapter<GrupoUsuario> arrayAdapterGrupos;
    Usuario usuarioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*edtNome = (EditText) findViewById(R.id.editNome);
        edtSobrenome = (EditText) findViewById(R.id.editSobrenome);*/
        listV_dados = (ListView) findViewById(R.id.listV_dados);

        inicializarFirebase();
        eventoDatabase();

        /*listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(MainActivity.this, Teste.class);
                intent.putExtra("usuario", usuarios.get(position));
                startActivity(intent);
               *//* usuarioSelecionado = (Usuario) adapterView.getItemAtPosition(position);
                edtNome.setText(usuarioSelecionado.getNome());
                edtSobrenome.setText(usuarioSelecionado.getSobrenome());*//*
            }
        });*/

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, NadaActivity.class);
                intent.putExtra("grupos", grupos.get(position));
                startActivity(intent);
            }
        });
    }

    //Listener para monitorar alterações no banco de dados
    private void eventoDatabase() {
        /*databaseReference.child("Setores").addValueEventListener(new ValueEventListener() {
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
                arrayAdapterSetor = new ArrayAdapter<Setor>(MainActivity.this, android.R.layout.simple_list_item_1,setores);
                listV_dados.setAdapter(arrayAdapterSetor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        databaseReference.child("Grupo de Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Para não ficar sobrescrevendo
                grupos.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    //Traz na ordem que estiver no banco, cada um dos objetos Usuario
                    GrupoUsuario u = objSnapshot.getValue(GrupoUsuario.class);
                    grupos.add(u);
                }

                //Listando dados do firebase na listView
                arrayAdapterGrupos = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,grupos);
                listV_dados.setAdapter(arrayAdapterGrupos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Método para inicializar o Firebase
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Permite salvar os dados na nuvem, como também dentro do app
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }


    //Método para alterar o menu
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Verifica qual botão do menu está sendo selecionado
        int id = item.getItemId();
        if(id == R.id.menu_novo){
            Usuario usuario = new Usuario();
            usuario.setUid(UUID.randomUUID().toString());
            usuario.setNome(edtNome.getText().toString());
            databaseReference.child("Usuarios").child(usuario.getUid()).setValue(usuario);
            limparCampos();
        }

        else if(id == R.id.menu_atualiza){
            Usuario usuario = new Usuario();
            //Recebe valor original
            usuario.setUid(usuarioSelecionado.getUid());
            //Recebe valor que será digitado
                //trim para tirar os espaços em branco (no início e no final da palavra)
            usuario.setNome(edtNome.getText().toString().trim());
            databaseReference.child("Usuarios").child(usuario.getUid()).setValue(usuario);
            limparCampos();
        }
        else if(id == R.id.menu_deleta){
            Usuario usuario = new Usuario();
            usuario.setUid(usuarioSelecionado.getUid());
            databaseReference.child("Usuarios").child(usuario.getUid()).removeValue();
            limparCampos();
        }
        else if(id == R.id.menu_pesquisa){
            Intent intent = new Intent(MainActivity.this, PesquisaActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void limparCampos() {
        edtNome.setText("");
        edtSobrenome.setText("");
    }
}

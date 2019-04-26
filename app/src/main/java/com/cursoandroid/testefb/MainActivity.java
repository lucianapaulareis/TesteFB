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

   /* EditText edtNome, edtSobrenome;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private ArrayAdapter<Usuario> arrayAdpterUsuario;


    private List<Setor> setores = new ArrayList<Setor>();
    private ArrayAdapter<Setor> arrayAdapterSetor;


    private List<Leito> leitos = new ArrayList<>();
    private ArrayAdapter<Leito> arrayAdapterLeito;


    private List<Situacao> status = new ArrayList<Situacao>();
    private ArrayAdapter<Situacao> arrayAdapterSituacao;


    private List<GrupoUsuario> grupos = new ArrayList<GrupoUsuario>();
    private ArrayAdapter<GrupoUsuario> arrayAdapterGrupos;
    Usuario usuarioSelecionado;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listarSetores(View View) {
        Intent it = new Intent(this, ListaSetoresActivity.class);
        startActivityForResult(it, 1);
    }


    public void listarLeitos(View View) {
        Intent it = new Intent(this, LeitosOpcoesActivity.class);
        startActivityForResult(it, 1);
    }
}




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






    //Método para alterar o menu
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }*/
    /*@Override
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
    }*/


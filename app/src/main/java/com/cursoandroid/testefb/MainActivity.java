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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference;
    private Button botaoSair;
    private String grupo;
    private Button botaoCadastrarLeito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupoUsuario");
        Toast.makeText(MainActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();

        botaoCadastrarLeito = (Button) findViewById(R.id.bt_cadastrar_leito);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        databaseReference = ConfiguracaoFirebase.getFirebase();

        botaoSair = (Button) findViewById(R.id.bt_sair);
        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.signOut();
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void listarSetores(View View) {
        Intent it = new Intent(this, ListaSetoresActivity.class);
        it.putExtra("grupo",grupo);
        startActivityForResult(it, 1);
    }


    public void listarLeitos(View View) {
        Intent it = new Intent(this, LeitosOpcoesActivity.class);
        startActivityForResult(it, 1);
    }

    public void cadastraSetores (View View){
        Intent it = new Intent(this, CadastroSetorActivity.class);
        startActivity(it);
    }

    public void cadastraLeito(View view){
        Intent it = new Intent(this, CadastroLeitoActivity.class);
        startActivity(it);
    }

    public void gerenciarUsuarios(View view){
        Intent it = new Intent(this, GerenciaUsuariosActivity.class);
        startActivity(it);
    }


}
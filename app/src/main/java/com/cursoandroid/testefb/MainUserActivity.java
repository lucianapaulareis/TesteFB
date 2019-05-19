package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainUserActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        Intent it = getIntent();
        grupo = it.getStringExtra("grupoUsuario");
        Toast.makeText(MainUserActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();

        Button botaoSair = (Button) findViewById(R.id.bt_sair);
        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.signOut();
                Intent it = new Intent(MainUserActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void listarSetores(View View) {
        Intent it = new Intent(this, ListaSetoresActivity.class);
        it.putExtra("grupo",grupo);
        startActivity(it);
    }

    public void listarLeitos(View View) {
        Intent it = new Intent(this, LeitosOpcoesActivity.class);
        it.putExtra("grupo",grupo);
        startActivity(it);
    }
}

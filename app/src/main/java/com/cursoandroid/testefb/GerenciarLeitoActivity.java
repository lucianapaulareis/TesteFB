package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GerenciarLeitoActivity extends AppCompatActivity {

    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_leito);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupo");

    }

    public void cadastrar(View view){
        Intent it = new Intent(GerenciarLeitoActivity.this, CadastroLeitoActivity.class);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void editar(View view){
        Intent it = new Intent(GerenciarLeitoActivity.this, ListaSetoresActivity.class);
        it.putExtra("grupo", grupo);
        it.putExtra("editar", "editar");
        startActivity(it);
    }

    public void excluir(View view){
        Intent it = new Intent(GerenciarLeitoActivity.this, ListaSetoresActivity.class);
        it.putExtra("grupo", grupo);
        it.putExtra("excluir", "excluir");
        startActivity(it);
    }

    public void voltar(View view){
        Intent it = new Intent(GerenciarLeitoActivity.this, MainActivity.class);
        it.putExtra("grupoUsuario", grupo);
        finish();
        startActivity(it);
    }
}

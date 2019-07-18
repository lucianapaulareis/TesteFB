package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GerenciarSetoresActivity extends AppCompatActivity {

    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_setores);

        Intent intent = getIntent();
        grupo = intent.getStringExtra("grupo");
    }

    public void cadastrar(View view){
        Intent it = new Intent(GerenciarSetoresActivity.this, CadastroSetorActivity.class);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void editar(View view){
        Intent it = new Intent(GerenciarSetoresActivity.this, ListaSetoresActivity.class);
        it.putExtra("grupo", grupo);
        it.putExtra("editarSetor", "editarSetor");
        startActivity(it);
    }

    public void excluir(View view){
        Intent it = new Intent(GerenciarSetoresActivity.this, ListaSetoresActivity.class);
        it.putExtra("grupo", grupo);
        it.putExtra("excluirSetor", "excluirSetor");
        startActivity(it);
    }

    public void voltar(View view){
        Intent it = new Intent(GerenciarSetoresActivity.this, MainActivity.class);
        it.putExtra("grupoUsuario", grupo);
        finish();
        startActivity(it);
    }
}

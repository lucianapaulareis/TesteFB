package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LeitosOpcoesActivity extends AppCompatActivity {

    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitos_opcoes);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupo");
        Toast.makeText(LeitosOpcoesActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();
    }

    public void listarOcupados(View View){
        String status = "Ocupado";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarAguardandoHigienizacao(View View){
        String status = "Aguardando Higienização";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarEmHigienizacao(View View){
        String status = "Em Higienização";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarAguardandoForragem(View View){
        String status = "Aguardando Forragem";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarEmForragem(View View){
        String status = "Em Forragem";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarLivre(View View){
        String status = "Livre";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }
}

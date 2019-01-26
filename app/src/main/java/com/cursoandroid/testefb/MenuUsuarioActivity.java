package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        Intent intent = getIntent();
        Usuario usuario = intent.getParcelableExtra("usuario");
        String nomeUsuario = usuario.getNome();

        TextView nome = (TextView) findViewById(R.id.textoUsuario);
        nome.setText(nomeUsuario);


    }

    /*public void listar(View view){
        Intent intent = new Intent(MenuUsuarioActivity.this, DetalheUsuario.class);


        startActivity(intent);
    }*/
}

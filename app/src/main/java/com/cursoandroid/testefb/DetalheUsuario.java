package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DetalheUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_usuario);

        Intent intent = getIntent();
        Usuario usuario = intent.getParcelableExtra("usuario");
        //String idUsuario = usuario.getUid();
        String nomeUsuario = usuario.getNome();
        String sobrenomeUsuario = usuario.getSobrenome();
        String sexoUsuario = usuario.getSexo();
        int idadeUsuario = usuario.getIdade();

        /*TextView id = (TextView) findViewById(R.id.idUsuario);
        id.setText(idUsuario);*/

        EditText nome = (EditText) findViewById(R.id.nomeUsuario);
        nome.setText(nomeUsuario);

        EditText sobrenome = (EditText) findViewById(R.id.sobrenomeUsuario);
        sobrenome.setText(sobrenomeUsuario);

        EditText sexo = (EditText) findViewById(R.id.sexoUsuario);
        sexo.setText(sexoUsuario);

        EditText idade = (EditText) findViewById(R.id.idadeUsuario);
        idade.setText(idadeUsuario);


    }
}

package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Teste extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        /*Intent intent = getIntent();
        Usuario usuario = intent.getParcelableExtra("usuario");
        String idUsuario = usuario.getUid();
        String nomeUsuario = usuario.getNome();
        String sobrenomeUsusario = usuario.getSobrenome();
        String sexoUsuario = usuario.getSexo();*/
        //String idadeUsuario = (String) usuario.getIdade();


        /*EditText id = (EditText) findViewById(R.id.idUsuario);
        id.setText(idUsuario);

        EditText nome = (EditText) findViewById(R.id.nomeUsuario);
        nome.setText(nomeUsuario+" "+sobrenomeUsusario);

        EditText sexo = (EditText) findViewById(R.id.sexoUsuario);
        sexo.setText(sexoUsuario);*/

       /* EditText idade = (EditText) findViewById(R.id.idadeUsuario);
        idade.setText(idadeUsuario);*/

        Intent intent = getIntent();
        Setor setor = intent.getParcelableExtra("setor");
        String idSetor = setor.getUid();
        String nomeSetor = setor.getNome();

        EditText id = (EditText) findViewById(R.id.idSetor);
        id.setText(idSetor);

        EditText nome = (EditText) findViewById(R.id.nomeSetor);
        nome.setText(nomeSetor);
    }
}

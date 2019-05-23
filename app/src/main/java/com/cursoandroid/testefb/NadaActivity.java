package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class NadaActivity extends AppCompatActivity {

    private TextView grupoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nada);

        Intent it = getIntent();
        String grupo = it.getStringExtra("grupo");

        grupoUser = (TextView) findViewById(R.id.edit_grupo);
        grupoUser.setText(grupo);
    }
}

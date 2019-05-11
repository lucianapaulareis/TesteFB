package com.cursoandroid.testefb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class CadastroSetorActivity extends AppCompatActivity {

    private EditText idSetor;
    private EditText nomeSetor;
    private Button cadastroSetor;
    private Setor setor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_setor);

        idSetor = (EditText) findViewById(R.id.edit_uid_setor);
        nomeSetor = (EditText) findViewById(R.id.edit_nome_setor);
        cadastroSetor = (Button) findViewById(R.id.bt_cadastrar_setor);
        cadastroSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setor = new Setor();
                setor.setUid(idSetor.getText().toString());
                setor.setNome(nomeSetor.getText().toString());
                setor.salvar();
                Toast.makeText(CadastroSetorActivity.this, "Cadastro de Setor Realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}

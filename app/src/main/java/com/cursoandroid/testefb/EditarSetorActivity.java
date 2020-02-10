package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarSetorActivity extends AppCompatActivity {

    private String grupo;
    private String editarSetor;
    private TextView idSetor;
    private EditText nomeSetor;
    private Button botaoSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_setor);

        Intent it = getIntent();
        final Setor s = it.getParcelableExtra("setor");
        grupo = it.getStringExtra("grupo");
        editarSetor = it.getStringExtra("excluirSetor");
        Toast.makeText(EditarSetorActivity.this, "Excluir: "+editarSetor, Toast.LENGTH_SHORT).show();

        idSetor = (TextView) findViewById(R.id.idSetor);
        nomeSetor = (EditText) findViewById(R.id.nomeSetor);
        botaoSalvar = (Button) findViewById(R.id.bt_salvar);

        idSetor.setText(s.getUid());
        nomeSetor.setText(s.getNome());

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setor setor = new Setor();
                setor.setNome(nomeSetor.getText().toString());
                setor.setUid(s.getUid());
                setor.salvar();
                Toast.makeText(EditarSetorActivity.this, "Leito editado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                Intent it = new Intent(EditarSetorActivity.this, MainActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
            }
        });
    }
}

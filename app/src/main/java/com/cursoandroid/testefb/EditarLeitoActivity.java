package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class EditarLeitoActivity extends AppCompatActivity {

    private TextView idLeito;
    private EditText nomeLeito;
    private TextView statusLeito;
    private Button salvar;
    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_leito);

        Intent it = getIntent();
        final Leito l = it.getParcelableExtra("leito");
        String edit = it.getStringExtra("editar");
        grupo = it.getStringExtra("grupo");
        //Toast.makeText(EditarLeitoActivity.this, "Veio: "+edit+"\nGrupo: "+grupo, Toast.LENGTH_SHORT).show();

        idLeito = (TextView) findViewById(R.id.idLeito);
        nomeLeito = (EditText) findViewById(R.id.nomeLeito);
        statusLeito = (TextView) findViewById(R.id.situacaoLeito);
        salvar = (Button) findViewById(R.id.bt_salvar);
        idLeito.setText(l.getUid());
        nomeLeito.setText(l.getNome());
        statusLeito.setText(l.getSituacao());
        //final String nomeL = nomeLeito.getText().toString();

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leito leito = new Leito();
                leito.setNome(nomeLeito.getText().toString());
                leito.setUid(l.getUid());
                leito.setSituacao(l.getSituacao());
                leito.setSid(l.getSid());
                leito.salvar();
                Toast.makeText(EditarLeitoActivity.this, "Leito editado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                Intent it = new Intent(EditarLeitoActivity.this, MainActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
            }
        });
    }
}

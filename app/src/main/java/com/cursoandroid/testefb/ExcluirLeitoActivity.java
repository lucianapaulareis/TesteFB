package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ExcluirLeitoActivity extends AppCompatActivity {

    private String excluir;
    private String grupo;
    private TextView idLeito;
    private TextView nomeLeito;
    private TextView statusLeito;
    private Button botaoExcluir;
    private Leito l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_leito);

        Intent it = getIntent();
       l = it.getParcelableExtra("leito");
        excluir = it.getStringExtra("excluir");
        grupo = it.getStringExtra("grupo");

        idLeito = (TextView) findViewById(R.id.idLeito);
        nomeLeito = (TextView) findViewById(R.id.nomeLeito);
        statusLeito = (TextView) findViewById(R.id.situacaoLeito);
        botaoExcluir = (Button) findViewById(R.id.bt_excluir);
        idLeito.setText(l.getUid());
        nomeLeito.setText(l.getNome());
        statusLeito.setText(l.getSituacao());

        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.excluir(l.getUid());
                finish();
                Intent it = new Intent(ExcluirLeitoActivity.this, MainActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
            }
        });

    }
}

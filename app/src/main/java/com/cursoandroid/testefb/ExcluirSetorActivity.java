package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExcluirSetorActivity extends AppCompatActivity {

    private Setor setor;
    private String grupo;
    private String excluirSetor;
    private TextView idSetor;
    private TextView nomeSetor;
    private Button botaoExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_setor);


        Intent intent = getIntent();
        setor = intent.getParcelableExtra("setor");
        grupo = intent.getStringExtra("grupo");
        excluirSetor = intent.getStringExtra("excluirSetor");

        idSetor = (TextView) findViewById(R.id.idSetor);
        nomeSetor = (TextView) findViewById(R.id.nomeSetor);
        botaoExcluir = (Button) findViewById(R.id.bt_excluir);

        idSetor.setText(setor.getUid());
        nomeSetor.setText(setor.getNome());

        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setor.excluir(setor.getUid());
                finish();
                Intent it = new Intent(ExcluirSetorActivity.this, MainActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
            }
        });


    }
}

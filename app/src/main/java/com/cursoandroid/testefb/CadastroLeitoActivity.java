package com.cursoandroid.testefb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroLeitoActivity extends AppCompatActivity {

    private EditText idLeito;
    private EditText nomeLeito;
    private EditText idSetor;
    private Spinner spinner;
    private Button cadastraLeito;
    private String statusLeito;
    private Leito leito;
    private ArrayList<String> nomeS = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_leito);

        idLeito = (EditText) findViewById(R.id.edit_uid_leito);
        nomeLeito = (EditText) findViewById(R.id.edit_nome_leito);
        idSetor = (EditText) findViewById(R.id.edit_uid_setor);
        spinner = (Spinner) findViewById(R.id.spinner);
        cadastraLeito = (Button) findViewById(R.id.bt_cadastrar_leito);

        spinner = (Spinner) findViewById(R.id.spinner);

        nomeS.add("Selecione o novo Status");
        nomeS.add("Ocupado");
        nomeS.add("Aguardando Higienização");
        nomeS.add("Em Higienização");
        nomeS.add("Aguardando Forragem");
        nomeS.add("Em Forragem");
        nomeS.add("Livre");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CadastroLeitoActivity.this,
                android.R.layout.simple_list_item_1, nomeS);
        spinner.setAdapter(arrayAdapter);

        //Escolha do status do leito
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView s1;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                statusLeito = nomeS.get(position);
                if(statusLeito.equals("Selecione o Status")){
                    s1 = (TextView) findViewById(R.id.texto_status);
                    s1.setText(nomeS.get(6));
                }
                else{
                    s1 = (TextView) findViewById(R.id.texto_status);
                    s1.setText(statusLeito);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cadastraLeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leito = new Leito();
                leito.setUid(idLeito.getText().toString());
                leito.setNome(nomeLeito.getText().toString());
                leito.setSid(idSetor.getText().toString());
                leito.setSituacao(statusLeito.toString());
                leito.salvar();
                Toast.makeText(CadastroLeitoActivity.this, "Leito Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}

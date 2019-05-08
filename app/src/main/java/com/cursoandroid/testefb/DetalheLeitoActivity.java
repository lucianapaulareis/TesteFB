package com.cursoandroid.testefb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetalheLeitoActivity extends AppCompatActivity {

    private ArrayList<String> nomeS = new ArrayList<String>();
    private Spinner spinner;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Leito> leitos = new ArrayList<Leito>();
    private ArrayAdapter<Leito> arrayAdapterLeito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_leito);

        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        String idLeito = leito.getUid();
        String nomeLeito = leito.getNome();
        final String situacaoLeito = leito.getSituacao();

        //Spinner
        spinner = (Spinner) findViewById(R.id.seletor);

        nomeS.add("Selecione o novo Status");
        nomeS.add("Ocupado");
        nomeS.add("Aguardando Higienização");
        nomeS.add("Em Higienização");
        nomeS.add("Aguardando Forragem");
        nomeS.add("Em Forragem");
        nomeS.add("Livre");

        //EditText id = (EditText) findViewById(R.id.idLeito);
        TextView id = (TextView) findViewById(R.id.idLeito);
        if(idLeito != null){
            id.setText(idLeito);
        }
        else{
            id.setText("Não está carregando ID do leito!!!");
        }

        TextView nome = (TextView) findViewById(R.id.nomeLeito);
        nome.setText(nomeLeito);
        TextView situacao = (TextView) findViewById(R.id.situacaoLeito);
        situacao.setText(situacaoLeito);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetalheLeitoActivity.this,
                android.R.layout.simple_list_item_1, nomeS);
        spinner.setAdapter(arrayAdapter);

        //Escolha do status do leito
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView s1;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String status = nomeS.get(position);
                if(status.equals("Selecione o novo Status")){
                    s1 = (TextView) findViewById(R.id.situacaoLeito);
                    s1.setText(situacaoLeito);
                }
                else{
                    s1 = (TextView) findViewById(R.id.situacaoLeito);
                    s1.setText(status);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        inicializarFirebase();
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(DetalheLeitoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void alterar(View view){
        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        String idLeito = leito.getUid();
        String nomeLeito = leito.getNome();
        String idSetor = leito.getSid();
        leito = new Leito();
        leito.setUid(idLeito);
        leito.setNome(nomeLeito);
        leito.setSid(idSetor);
        TextView novoSituacao = (TextView) findViewById(R.id.situacaoLeito);
        String status = novoSituacao.getText().toString().trim();
        if(status.equals("")){
            Toast.makeText(DetalheLeitoActivity.this, "Por favor preencha o campo Status do Leito.", Toast.LENGTH_SHORT).show();
        }
        else{
            leito.setSituacao(status);
            databaseReference.child("Leitos").child(idLeito).setValue(leito);
            Toast.makeText(DetalheLeitoActivity.this, "Status do leito: "+nomeLeito+"\nAlterado para: "+status, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

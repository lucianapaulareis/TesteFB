package com.cursoandroid.testefb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    private Button salvarAlteracao;
    private RelativeLayout conjuntoEdicao;
    private List<Leito> leitos = new ArrayList<Leito>();
    private ArrayAdapter<Leito> arrayAdapterLeito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_leito);

        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        String grupo = intent.getStringExtra("grupo");
        String idLeito = leito.getUid();
        String nomeLeito = leito.getNome();
        final String situacaoLeito = leito.getSituacao();
        Toast.makeText(DetalheLeitoActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();

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
        conjuntoEdicao = (RelativeLayout) findViewById(R.id.conjuntoEdicao);
        salvarAlteracao = (Button) findViewById(R.id.bt_salvar_alteracao);

        // -------------------------------------------------
        /*nomeS.add(0, "Selecione o novo Status");
        nomeS.add(1, "Ocupado");
        nomeS.add(2, "Aguardando Higienização");
        nomeS.add(3, "Em Higienização");
        nomeS.add(4, "Aguardando Forragem");
        nomeS.add(5, "Em Forragem");
        nomeS.add(6, "Livre");*/
        // -------------------------------------------------


        //Spinner
        spinner = (Spinner) findViewById(R.id.seletor);

        //Enfermaria
        switch (grupo) {
            case "002":
                switch (situacaoLeito) {
                    case "Livre":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Ocupado");
                        break;
                    case "Aguardando Forragem":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Em Forragem");
                        break;
                    case "Em Forragem":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Livre");
                        break;
                    default:
                        Toast.makeText(DetalheLeitoActivity.this, "Usuário não autorizado a atualizar o Status deste leito", Toast.LENGTH_SHORT).show();
                        conjuntoEdicao.setVisibility(View.INVISIBLE);//Esconder a opção de edição do leito (Spinner e botão salvar)

                        break;
                }
                break;

            //Portaria
            case "003":
                switch (situacaoLeito) {
                    case "Ocupado":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Aguardando Higienização");
                        break;
                    default:
                        Toast.makeText(DetalheLeitoActivity.this, "Usuário não autorizado a atualizar o Status deste leito", Toast.LENGTH_SHORT).show();
                        conjuntoEdicao.setVisibility(View.INVISIBLE);//Esconder a opção de edição do leito (Spinner e botão salvar)
                        break;
                }
                break;

            //SHL
            case "004":
                switch (situacaoLeito) {
                    case "Aguardando Higienização":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Em Higienização");
                        break;
                    case "Em Higienização":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Aguardando Forragem");
                        break;
                    default:
                        Toast.makeText(DetalheLeitoActivity.this, "Usuário não autorizado a atualizar o Status deste leito", Toast.LENGTH_SHORT).show();
                        conjuntoEdicao.setVisibility(View.INVISIBLE);//Esconder a opção de edição do leito (Spinner e botão salvar)

                        break;
                }
                break;

            //Camareira
            case "005":
                switch (situacaoLeito) {
                    case "Aguardando Forragem":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Em Forragem");
                        break;
                    case "Em Forragem":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Livre");
                        break;
                    default:
                        Toast.makeText(DetalheLeitoActivity.this, "Usuário não autorizado a atualizar o Status deste leito", Toast.LENGTH_SHORT).show();
                        conjuntoEdicao.setVisibility(View.INVISIBLE);//Esconder a opção de edição do leito (Spinner e botão salvar)

                        break;
                }
                break;

            //Administrador
            default:
                nomeS.add("Selecione o novo Status");
                nomeS.add("Ocupado");
                nomeS.add("Aguardando Higienização");
                nomeS.add("Em Higienização");
                nomeS.add("Aguardando Forragem");
                nomeS.add("Em Forragem");
                nomeS.add("Livre");
                break;
        }

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
                    s1 = (TextView) findViewById(R.id.edit_novoStatus);
                    s1.setText("");//Quando acontecer isso, não salvar
                }
                else{
                    s1 = (TextView) findViewById(R.id.edit_novoStatus);
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
        TextView novoSituacao = (TextView) findViewById(R.id.edit_novoStatus);
        String status = novoSituacao.getText().toString().trim();
        if(status.equals("")){
            Toast.makeText(DetalheLeitoActivity.this, "Por favor selecione um novo status para o leito.", Toast.LENGTH_SHORT).show();
        }
        else{
            leito.setSituacao(status);
            databaseReference.child("Leitos").child(idLeito).setValue(leito);
            Toast.makeText(DetalheLeitoActivity.this, "Status do leito: "+nomeLeito+"\nAlterado para: "+status, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

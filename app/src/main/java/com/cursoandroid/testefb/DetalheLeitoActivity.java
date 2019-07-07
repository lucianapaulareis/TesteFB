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
import java.util.Set;

public class DetalheLeitoActivity extends AppCompatActivity {

    private ArrayList<String> nomeS = new ArrayList<String>();
    private ArrayList<Setor> novoSetor = new ArrayList<Setor>();
    private Spinner spinner;
    private Spinner spinner2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button salvarAlteracao;
    private RelativeLayout conjuntoEdicao;
    private RelativeLayout conjuntoEdicaoSetor;
    private List<Leito> leitos = new ArrayList<Leito>();
    private ArrayAdapter<Leito> arrayAdapterLeito;
    private String mudar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_leito);

        databaseReference = ConfiguracaoFirebase.getFirebase();
        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        //Toast.makeText(DetalheLeitoActivity.this, "Leito: "+leito.getNome(), Toast.LENGTH_SHORT).show();
        String grupo = intent.getStringExtra("grupo");
        mudar = intent.getStringExtra("mudar");
        String idLeito = leito.getUid();
        String nomeLeito = leito.getNome();
        final String situacaoLeito = leito.getSituacao();
        //Toast.makeText(DetalheLeitoActivity.this, "Grupo: " + grupo+"\nMudar: "+mudar, Toast.LENGTH_SHORT).show();

        TextView id = (TextView) findViewById(R.id.idLeito);
        if (idLeito != null) {
            id.setText(idLeito);
        } else {
            id.setText("Não está carregando ID do leito!!!");
        }
        TextView nome = (TextView) findViewById(R.id.nomeLeito);
        nome.setText(nomeLeito);
        TextView situacao = (TextView) findViewById(R.id.situacaoLeito);
        situacao.setText(situacaoLeito);
        conjuntoEdicao = (RelativeLayout) findViewById(R.id.conjuntoEdicao);
        conjuntoEdicaoSetor = (RelativeLayout) findViewById(R.id.conjuntoEdicaoSetor);
        salvarAlteracao = (Button) findViewById(R.id.bt_salvar_alteracao);

        //Spinner Status
        spinner = (Spinner) findViewById(R.id.seletor);

        //Spinner Setor
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //novoSetor.add("Selecionar novo Setor");
        if (grupo == null) {
            if (mudar != null) {
                Toast.makeText(DetalheLeitoActivity.this, "Mudar: ", Toast.LENGTH_SHORT).show();
                conjuntoEdicao.setVisibility(View.GONE);
                databaseReference = ConfiguracaoFirebase.getFirebase();
                databaseReference.child("Setores").orderByChild("nome").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Setor setor = new Setor();
                            String nomeSetor = (String) snapshot.child("nome").getValue();
                            setor.setNome(nomeSetor);
                            String uidSetor = (String) snapshot.child("uid").getValue();
                            setor.setUid(uidSetor);
                            novoSetor.add(setor);
                            ArrayAdapter<Setor> arrayAdapter = new ArrayAdapter<Setor>(DetalheLeitoActivity.this,
                                    android.R.layout.simple_list_item_1, novoSetor);
                            spinner2.setAdapter(arrayAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                    //Escolha do status do leito
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        TextView s1;
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            Setor novoS = novoSetor.get(position);
                            /*if(novoS.equals("Selecione o novo Status")){
                                s1 = (TextView) findViewById(R.id.edit_novoSetor);
                                s1.setText("");//Quando acontecer isso, não salvar
                            }*/
                            //else{
                                s1 = (TextView) findViewById(R.id.edit_novoSetor);
                                s1.setText(novoS.getUid());
                            //}
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
            }
        }
        else{
        conjuntoEdicaoSetor.setVisibility(View.GONE);
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

            //Internação
            case "006":
                switch (situacaoLeito) {
                    case "Livre":
                        nomeS.add("Selecione o novo Status");
                        nomeS.add("Ocupado");
                        break;
                    default:
                        Toast.makeText(DetalheLeitoActivity.this, "Usuário não autorizado a atualizar o Status deste leito", Toast.LENGTH_SHORT).show();
                        conjuntoEdicao.setVisibility(View.INVISIBLE);//Esconder a opção de edição do leito (Spinner e botão salvar)
                        break;
                }
                break;

            //Administrador
            default:
                    conjuntoEdicaoSetor.setVisibility(View.GONE);
                    nomeS.add("Selecione o novo Status");
                    nomeS.add("Ocupado");
                    nomeS.add("Aguardando Higienização");
                    nomeS.add("Em Higienização");
                    nomeS.add("Aguardando Forragem");
                    nomeS.add("Em Forragem");
                    nomeS.add("Livre");
                break;
        }
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

    }

    public void alterar(View view){
        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        String grupo = intent.getStringExtra("grupo");
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
            if(grupo.equals("001")){
                leito.setSituacao(status);
                databaseReference.child("Leitos").child(idLeito).setValue(leito);
                //Toast.makeText(DetalheLeitoActivity.this, "Status do leito: "+nomeLeito+"\nAlterado para: "+status, Toast.LENGTH_LONG).show();
                Intent it = new Intent(this, MainActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
                finish();
            }
            else{
                leito.setSituacao(status);
                databaseReference.child("Leitos").child(idLeito).setValue(leito);
                Toast.makeText(DetalheLeitoActivity.this, "Grupo Detalhe: "+grupo+"\nAlterado para: "+status, Toast.LENGTH_SHORT).show();
                Intent it = new Intent(this, MainUserActivity.class);
                it.putExtra("grupoUsuario", grupo);
                startActivity(it);
                finish();
            }

        }
    }

    public void alterarSetor(View view){
        Intent intent = getIntent();
        Leito leito = intent.getParcelableExtra("leito");
        String idLeito = leito.getUid();
        String nomeLeito = leito.getNome();
        String situacaoLeito = leito.getSituacao();
        leito = new Leito();
        leito.setUid(idLeito);
        leito.setNome(nomeLeito);
        leito.setSituacao(situacaoLeito);
        TextView setorNovo = (TextView) findViewById(R.id.edit_novoSetor);
        String s = setorNovo.getText().toString().trim();
        if(s.equals("")){
            Toast.makeText(DetalheLeitoActivity.this, "Por favor selecione um novo status para o leito.", Toast.LENGTH_SHORT).show();
        }
        else{
            leito.setSid(s);
            databaseReference.child("Leitos").child(idLeito).setValue(leito);
            Toast.makeText(DetalheLeitoActivity.this, "Setor do leito: "+nomeLeito, Toast.LENGTH_LONG).show();
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
        }
    }
}

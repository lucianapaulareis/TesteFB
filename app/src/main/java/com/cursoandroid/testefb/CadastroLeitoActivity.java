package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.UUID;

import java.util.ArrayList;

public class CadastroLeitoActivity extends AppCompatActivity {

    private EditText nomeLeito;
    private EditText idSetor;
    private Spinner spinner;
    private Button cadastraLeito;
    private int numeroDeLeitos = 0;
    private String statusLeito;
    private Spinner spinner3;
    private Leito leito;
    private ArrayList<Situacao> nomesSituacao = new ArrayList<Situacao>();
    private ArrayList<Setor> nomesSetor = new ArrayList<Setor>();
    private DatabaseReference databaseReference;
    private Setor setorSelecionado;
    private Situacao situacaoSelecionada;
    private String grupo;

    public static int generateUniqueId() {
        UUID idOne = UUID.randomUUID();
        String str=""+idOne;
        int uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_leito);

        Intent it = getIntent();
        grupo = it.getStringExtra("grupo");

        nomeLeito = (EditText) findViewById(R.id.edit_nome_setor);
        spinner = (Spinner) findViewById(R.id.spinner);
        cadastraLeito = (Button) findViewById(R.id.bt_cadastrar_leito);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        databaseReference = ConfiguracaoFirebase.getFirebase();


        //Escolher Setor do novo leito
        databaseReference.child("Setores").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Setor setor = new Setor();
                    String nomeSetor = (String) snapshot.child("nome").getValue();
                    setor.setNome(nomeSetor);
                    String uidSetor = (String) snapshot.child("uid").getValue();
                    setor.setUid(uidSetor);
                    nomesSetor.add(setor);
                    ArrayAdapter<Setor> arrayAdapter = new ArrayAdapter<Setor>(CadastroLeitoActivity.this,
                            android.R.layout.simple_list_item_1, nomesSetor);
                    spinner3.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Setando escolha do setor do novo leito
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView s2;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Setor s = nomesSetor.get(position);
                setorSelecionado = s;
                s2 = (TextView) findViewById(R.id.texto_setor);
                s2.setText(s.getNome());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        //Escolher Situação do novo leito
        databaseReference.child("Situacao").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Situacao s = new Situacao();
                    String nomeSituacao = (String) snapshot.child("descricao").getValue();
                    s.setDescricao(nomeSituacao);
                    String idSituacao = (String) snapshot.child("sit_id").getValue();
                    s.setSit_id(idSituacao);
                    nomesSituacao.add(s);
                    ArrayAdapter<Situacao> arrayAdapter = new ArrayAdapter<Situacao>(CadastroLeitoActivity.this,
                        android.R.layout.simple_list_item_1, nomesSituacao);
                    spinner.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Setando escolha do status do leito
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView s1;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Situacao novoS = nomesSituacao.get(position);
                situacaoSelecionada = novoS;
                s1 = (TextView) findViewById(R.id.texto_status);
                s1.setText(novoS.getDescricao());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //Contar número de leitos
        databaseReference.child("Leitos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cadastraLeito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leito = new Leito();
                        String id = String.valueOf(generateUniqueId());
                        leito.setUid(id);
                        leito.setNome(nomeLeito.getText().toString());
                        leito.setSid(setorSelecionado.getUid());
                        leito.setSituacao(situacaoSelecionada.getDescricao());
                        leito.salvar();
                        Toast.makeText(CadastroLeitoActivity.this, "Leito cadastrado com sucesso: ", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(CadastroLeitoActivity.this, MainActivity.class);
                        it.putExtra("grupoUsuario", grupo);
                        startActivity(it);
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.cursoandroid.testefb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PesquisaActivity extends AppCompatActivity {

    private EditText editPalavra;
    private ListView listV_pesquisa;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private ArrayAdapter<Usuario> usuarioArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        inicializarComponentes();
        inicializaFirebase();
        eventoEdit();
    }

    private void eventoEdit() {
        editPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //A cada vez que digitar, a cada letra que digitar, pega o texto que está atualmente
                //no edit e mandar para String palavra
                String palavra = editPalavra.getText().toString().trim();
                pesquisarPalavra(palavra);
            }
        });
    }

    private void pesquisarPalavra(String palavra) {
        Query query;
        if(palavra.equals("")){//Se a palavra está vazia
            query = databaseReference.child("Usuarios").orderByChild("nome");
        }
        else{//Se não está vazia, traz todos os objetos, onde o primeiro objeto tem o campo nome exatamente igual
             //à palavra que passei para ele por parâmetro, e os últimos elementos é a palavra passar mais qualquer coisa
            query = databaseReference.child("Usuarios").orderByChild("nome").startAt(palavra).endAt(palavra+"\uf8ff");
        }
        usuarios.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Usuario u = objSnapshot.getValue(Usuario.class);
                    usuarios.add(u);
                }
                usuarioArrayAdapter = new ArrayAdapter<Usuario>(PesquisaActivity.this,
                        android.R.layout.simple_list_item_1, usuarios);
                listV_pesquisa.setAdapter(usuarioArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void inicializaFirebase() {
        FirebaseApp.initializeApp(PesquisaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private void inicializarComponentes() {
        editPalavra = (EditText) findViewById(R.id.editPalavra);
        listV_pesquisa = (ListView) findViewById(R.id.listV_pesquisa);
    }

    @Override
    protected void onResume() {
        //Para exibir todos os elementos, pois não quero que a classe venha vazia
        super.onResume();
        pesquisarPalavra("");

    }
}

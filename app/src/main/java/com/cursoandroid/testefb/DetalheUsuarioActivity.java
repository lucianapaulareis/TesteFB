package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalheUsuarioActivity extends AppCompatActivity {

    private TextView grupoUsuario;
    private String grupoUser;
    DatabaseReference databaseReference;
    private ArrayList<String> grupos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_usuario2);

        TextView nomeUsuario = (TextView) findViewById(R.id.texto_nome_usuario);
        TextView emailUsuario = (TextView) findViewById(R.id.texto_email_usuario);
        grupoUsuario = (TextView) findViewById(R.id.texto_grupo_usuario);
        Spinner spinner = (Spinner) findViewById(R.id.edit_grupo_usuario);

        Intent it = getIntent();
        Usuario usuario = it.getParcelableExtra("usuario");
        String nome = usuario.getNome();
        nomeUsuario.setText(nome);
        String email = usuario.getEmail();
        emailUsuario.setText(email);
        String grupo = usuario.getGrupo();
        if(grupo != null){
            //Pesquisando nome do Grupo de Usuário
            databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("Grupo de Usuario").child(grupo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*GrupoUsuario grupoUsuario = new GrupoUsuario();
                grupoUsuario.setGid((String) dataSnapshot.child("gid").getValue());
                grupoUsuario.setGrupo((String) dataSnapshot.child("grupo").getValue());*/
                    grupoUser = (String) dataSnapshot.child("grupo").getValue();
                    grupoUsuario.setText(grupoUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            grupoUsuario.setText("Usuário não possui grupo.");
        }

        grupos.add("Selecione o novo Grupo de Usuário");
        grupos.add("Enfermaria");
        grupos.add("Portaria");
        grupos.add("SHL");
        grupos.add("Camareira");
        grupos.add("Internação");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetalheUsuarioActivity.this,
                android.R.layout.simple_list_item_1, grupos);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView novoGrupo = (TextView) findViewById(R.id.edit_novo_grupo);;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String grupo = grupos.get(position);
                if(grupo.equals("Selecione o novo Grupo de Usuário")){
                    novoGrupo.setText("");//Quando acontecer isso, não salvar
                }
                else{
                    novoGrupo.setText(grupo);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void alterar(View view){

        final TextView grupoNovo = (TextView) findViewById(R.id.edit_novo_grupo);
        final String grupo = grupoNovo.getText().toString().trim();
        //Testar se o novo grupo é igual ao anterior

        if(grupo.equals("")){
            Toast.makeText(DetalheUsuarioActivity.this, "Por favor selecione um novo Grupo de Usuário para este usuário.", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent it = getIntent();
            Usuario usuario = it.getParcelableExtra("usuario");
            final String idUsuario = usuario.getUid();
            final String nome = usuario.getNome();
            final String email = usuario.getEmail();
            usuario = new Usuario();
            usuario.setUid(idUsuario);
            usuario.setNome(nome);
            usuario.setEmail(email);
            final Usuario finalUsuario = usuario;
            //Fazer a pesquisa para saber qual o ID do novo grupo
            databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("Grupo de Usuario").orderByChild("grupo").equalTo(grupo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for( DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String g = (String) snapshot.child("gid").getValue();
                        Toast.makeText(DetalheUsuarioActivity.this, "Grupo: " + g, Toast.LENGTH_SHORT).show();
                        finalUsuario.setGrupo(g);
                        databaseReference.child("Usuarios").child(idUsuario).setValue(finalUsuario);
                        Toast.makeText(DetalheUsuarioActivity.this, "O Usuário: "+nome+"\nE-mail: "+email+"\nTeve seu grupo de usuário atualizado para: "+grupo, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}

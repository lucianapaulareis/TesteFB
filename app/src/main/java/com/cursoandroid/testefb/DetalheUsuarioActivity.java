package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DetalheUsuarioActivity extends AppCompatActivity {

    private TextView nomeUsuario;
    private TextView emailUsuario;
    private TextView grupoUsuario;
    private String grupoUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_usuario2);

        nomeUsuario = (TextView) findViewById(R.id.texto_nome_usuario);
        emailUsuario = (TextView) findViewById(R.id.texto_email_usuario);
        grupoUsuario = (TextView) findViewById(R.id.texto_grupo_usuario);

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
               /* GrupoUsuario grupoUsuario = new GrupoUsuario();
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





    }
}

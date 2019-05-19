package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() == null){
            setContentView(R.layout.activity_login);
            email = (EditText) findViewById(R.id.edit_login_email);
            senha = (EditText) findViewById(R.id.edit_login_senha);
            Button botaoLogar = (Button) findViewById(R.id.botao_logar);

            botaoLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuario = new Usuario();
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    validarLogin();
                }
            });
        }
        else{
            String idUsuario = autenticacao.getUid();
            verificarUsuarioLogado(idUsuario);
        }
    }

    private void verificarUsuarioLogado(String idUser) {
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Usuarios").child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String group = (String) dataSnapshot.child("grupo").getValue();
                if(group == null){
                    Toast.makeText(LoginActivity.this, "Usuário não está alocado em um grupo de permissão!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LoginActivity.this, NadaActivity.class);
                    startActivity(it);
                    finish();
                }
                else{
                    if(group.equals("001")){
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("grupoUsuario", group);
                        startActivity(it);
                        finish();
                    }
                    else{
                        Intent it = new Intent(LoginActivity.this, MainUserActivity.class);
                        it.putExtra("grupoUsuario", group);
                        startActivity(it);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void validarLogin() {
        databaseReference = ConfiguracaoFirebase.getFirebase();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//Se a autenticação deu certo
                    if(autenticacao.getCurrentUser() != null){
                        String id = autenticacao.getUid();
                        databaseReference.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String group = (String) dataSnapshot.child("grupo").getValue();
                                if(group == null){
                                    Toast.makeText(LoginActivity.this, "Usuário não está alocado em um grupo de permissão!", Toast.LENGTH_LONG).show();
                                    autenticacao.signOut();
                                }
                                else{
                                    abrirTelaPrincipal(group);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                else{
                    String erroExecao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExecao = "Usuário desativado ou excluído.";
                    } catch (Exception e) {
                        erroExecao = "Erro ao efeutar o login.";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, "Erro: "+erroExecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal(String id) {
        if(id.equals("001")){
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            it.putExtra("grupoUsuario", id);
            startActivity(it);
            finish();
        }
        else{
            Intent it = new Intent(LoginActivity.this, MainUserActivity.class);
            it.putExtra("grupoUsuario", id);
            startActivity(it);
            finish();
        }
    }

    public void abrirCadastroUsuario(View view){
        Intent it = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(it);
    }
}

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
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.edit_login_email);
        senha = (EditText) findViewById(R.id.edit_login_senha);
        botaoLogar = (Button) findViewById(R.id.botao_logar);

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

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//Se a autenticação deu certo
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer login!", Toast.LENGTH_SHORT).show();
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

    private void abrirTelaPrincipal() {
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(it);
        //finish();
    }

    public void abrirCadastroUsuario(View view){
        Intent it = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(it);
    }

}

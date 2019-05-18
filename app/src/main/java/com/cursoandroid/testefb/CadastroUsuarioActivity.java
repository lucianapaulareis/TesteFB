package com.cursoandroid.testefb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confirmacaoSenha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.edit_cadastro_nome);
        email = (EditText) findViewById(R.id.edit_cadastro_email);
        senha = (EditText) findViewById(R.id.edit_cadastro_senha);
        confirmacaoSenha = (EditText) findViewById(R.id.edit_cadastro_senhaConfirma);
        botaoCadastrar = (Button) findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    String senhaUser = senha.getText().toString();
                    String confirmaSenha = confirmacaoSenha.getText().toString();
                    usuario.setSenha(senha.getText().toString());
                if(senhaUser.equals(confirmaSenha)){
                    cadastrarUsuario();
                }
                else{
                    Toast.makeText(CadastroUsuarioActivity.this, "Senha não confere.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //Criação do Usuário
        //Neste método também é necessário inserir um usuário com os mesmos dados no database
    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setUid(usuarioFirebase.getUid());
                    usuario.salvar();
                    autenticacao.signOut();//Para que o usuário faça o login após se cadastrar
                    finish();
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                }
                else{
                    String erroExecao = "";
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        erroExecao = "Digite uma senha mais forte, contendo mais caracteres, com letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExecao = "E-mail digitado é inválido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExecao = "Usuário já cadastrado.";
                    } catch (Exception e) {
                        erroExecao = "Erro ao efetuar o cadastro.";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: "+erroExecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

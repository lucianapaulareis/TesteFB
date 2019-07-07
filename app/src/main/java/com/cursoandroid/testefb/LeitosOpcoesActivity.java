package com.cursoandroid.testefb;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LeitosOpcoesActivity extends AppCompatActivity {

    private Button voltar;
    private String uidUsuario;
    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference;
    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitos_opcoes);
        //Se vier da Notificação
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(R.mipmap.ic_not);

        voltar = (Button) findViewById(R.id.bt_voltar);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null) {
            uidUsuario = autenticacao.getUid();
            databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("Usuarios").child(uidUsuario).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    grupo = (String) dataSnapshot.child("grupo").getValue();
                    Toast.makeText(LeitosOpcoesActivity.this, "Grupo: " + grupo, Toast.LENGTH_SHORT).show();
                    voltar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (grupo.equals("001")) {
                                Intent it = new Intent(getBaseContext(), MainActivity.class);
                                it.putExtra("grupoUsuario", grupo);
                                startActivity(it);
                                finish();
                            } else {
                                Intent it = new Intent(getBaseContext(), MainUserActivity.class);
                                it.putExtra("grupoUsuario", grupo);
                                startActivity(it);
                                finish();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void listarOcupados(View View){
        String status = "Ocupado";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarAguardandoHigienizacao(View View){
        String status = "Aguardando Higienização";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarEmHigienizacao(View View){
        String status = "Em Higienização";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarAguardandoForragem(View View){
        String status = "Aguardando Forragem";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarEmForragem(View View){
        String status = "Em Forragem";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }

    public void listarLivre(View View){
        String status = "Livre";
        Intent it = new Intent(LeitosOpcoesActivity.this, LeitosStatusActivity.class);
        it.putExtra("status", status);
        it.putExtra("grupo", grupo);
        startActivity(it);
    }
}

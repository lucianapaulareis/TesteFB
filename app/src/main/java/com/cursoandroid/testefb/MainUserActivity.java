package com.cursoandroid.testefb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class MainUserActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    DatabaseReference databaseReference;
    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        Intent it = getIntent();
        grupo = it.getStringExtra("grupoUsuario");
        //Toast.makeText(MainUserActivity.this, "Grupo: "+grupo, Toast.LENGTH_LONG).show();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Leitos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Leito l = dataSnapshot.getValue(Leito.class);
                switch (grupo){
                    case "002":
                        if(l.getSituacao().equals("Livre") || l.getSituacao().equals("Aguardando Forragem") || l.getSituacao().equals("Em Forragem")){
                            enviarNotificacao(l.getNome(), l.getSituacao());
                        }
                        break;

                    case "003":
                        if(l.getSituacao().equals("Ocupado")){
                            enviarNotificacao(l.getNome(), l.getSituacao());
                        }
                        break;

                    case "004":
                        if(l.getSituacao().equals("Aguardando Higienização") || l.getSituacao().equals("Em Higienização")){
                            enviarNotificacao(l.getNome(), l.getSituacao());
                        }
                        break;

                    case "005":
                        if(l.getSituacao().equals("Aguardando Forragem") || l.getSituacao().equals("Em Forragem")){
                            enviarNotificacao(l.getNome(), l.getSituacao());
                        }
                        break;

                    case "006":
                        if(l.getSituacao().equals("Livre")){
                            enviarNotificacao(l.getNome(), l.getSituacao());
                        }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button botaoSair = (Button) findViewById(R.id.bt_sair);
        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.signOut();
                Intent it = new Intent(MainUserActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void listarSetores(View View) {
        Intent it = new Intent(this, ListaSetoresActivity.class);
        it.putExtra("grupo",grupo);
        startActivity(it);
    }

    public void listarLeitos(View View) {
        Intent it = new Intent(this, LeitosOpcoesActivity.class);
        startActivity(it);
    }

    //Envia a notificação para os devidos usuários
    private void enviarNotificacao(String nome, String situacao) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent it = new Intent(MainUserActivity.this, LeitosOpcoesActivity.class);
        PendingIntent p = PendingIntent.getActivity(MainUserActivity.this, 0, it, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainUserActivity.this);
        builder.setTicker("tickerText");
        builder.setContentTitle("Mudança de status de Leito");
        builder.setContentText("O status do leito: "+nome+" \nmudou para: "+situacao);
        builder.setSmallIcon(R.mipmap.ic_not);
        builder.setContentIntent(p);
        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        nm.notify(R.mipmap.ic_not, n);
        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(getApplicationContext(), som);
            toque.play();
        }catch (Exception e){

        }
    }
}

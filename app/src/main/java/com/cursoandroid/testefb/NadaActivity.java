package com.cursoandroid.testefb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class NadaActivity extends AppCompatActivity {

    private Button voltar;
    private String uidUsuario;
    private String grupo;
    private TextView uidLeito;
    private TextView nomeLeito;
    private TextView statusLeito;
    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nada);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        voltar = (Button) findViewById(R.id.bt_voltar);
        if(autenticacao.getCurrentUser() != null){
            uidUsuario = autenticacao.getUid();
            databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("Usuarios").child(uidUsuario).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    grupo = (String) dataSnapshot.child("grupo").getValue();
                    Toast.makeText(NadaActivity.this, "Grupo: "+grupo, Toast.LENGTH_SHORT).show();
                    voltar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(grupo.equals("001")){
                                Intent it = new Intent(getBaseContext(), MainActivity.class);
                                it.putExtra("grupoUsuario", grupo);
                                startActivity(it);
                                finish();
                            }
                            else{
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

}

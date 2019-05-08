package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

//public class Usuario implements Parcelable
public class Usuario implements Serializable{
    public String uid;
    public String nome;
    public String email;
    public String senha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Usuario() {

    }

    public Usuario(String uid, String nome, String email, String senha) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    /*protected Usuario(Parcel in) {
        uid = in.readString();
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
    }*/

    /*public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };*/

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public void salvar(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios").child(getUid()).setValue(this);//Seta os dados desse próprio objeto usuário
    }*/

    /*private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }*/


    /*@Override
    public String toString() {
        return nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeString(senha);
    }*/
}
package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

//public class Usuario implements Parcelable
public class Usuario implements Parcelable{
    private String uid;
    private String nome;
    private String email;
    private String senha;
    private String grupo;

    public Usuario() {

    }



    protected Usuario(Parcel in) {
        uid = in.readString();
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        grupo = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };


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

    //Quando o firebase for salvar esse objeto, não vai levar em consideração a senha (Não vai salvar a senha)
    @Exclude
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

    public String getGrupo(){
        return grupo;
    }

    public void setGrupo(String grupo){
        this.grupo = grupo;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Usuarios").child(getUid()).setValue(this);//Seta os dados desse próprio objeto usuário
    }


    @Override
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
        parcel.writeString(grupo);
    }
}
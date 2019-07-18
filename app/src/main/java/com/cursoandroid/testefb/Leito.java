package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;

public class Leito implements Parcelable {
    private String uid;
    private String nome;
    private String situacao;
    private String sid;

    public Leito() {

    }

    protected Leito(Parcel in) {
        uid = in.readString();
        nome = in.readString();
        situacao = in.readString();
        sid = in.readString();
    }

    public static final Creator<Leito> CREATOR = new Creator<Leito>() {
        @Override
        public Leito createFromParcel(Parcel in) {
            return new Leito(in);
        }

        @Override
        public Leito[] newArray(int size) {
            return new Leito[size];
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }


    public int getImagemLivre(){
        return R.mipmap.ic_livre;
    }

    public int getImagemOcupado(){
        return R.mipmap.ic_ocupado;
    }

    public int getImagemHigienizacao(){
        return R.mipmap.ic_emhig;
    }

    public int getImagemAguardandoHigienizacao(){
        return R.mipmap.ic_aghig;
    }

    public int getImagemEmForragem(){
        return R.mipmap.ic_forro;
    }

    public int getImagemAguardandoForragem(){
        return R.mipmap.ic_agforro;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Leitos").child(getUid()).setValue(this);//Seta os dados desse próprio objeto usuário
    }

    public void excluir(String id){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Leitos").child(id).removeValue();
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
        parcel.writeString(situacao);
        parcel.writeString(sid);
    }
}
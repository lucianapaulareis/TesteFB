package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

public class Leito implements Parcelable {
    private String id;
    private String nome;
    private String situacao;

    public Leito() {

    }

    protected Leito(Parcel in) {
        id = in.readString();
        nome = in.readString();
        situacao = in.readString();
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
        return id;
    }

    public void setUid(String uid) {
        this.id = uid;
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
        parcel.writeString(id);
        parcel.writeString(nome);
        parcel.writeString(situacao);
    }
}
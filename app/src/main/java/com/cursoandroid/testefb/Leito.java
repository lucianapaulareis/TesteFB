package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
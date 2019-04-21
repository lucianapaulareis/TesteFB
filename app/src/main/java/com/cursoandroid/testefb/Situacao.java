package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

public class Situacao implements Parcelable{
    private String descricao;
    private String sit_id;

    public Situacao() {

    }

    protected Situacao(Parcel in) {
        descricao = in.readString();
        sit_id = in.readString();
    }


    public static final Creator<Situacao> CREATOR = new Creator<Situacao>() {
        @Override
        public Situacao createFromParcel(Parcel in) {
            return new Situacao(in);
        }

        @Override
        public Situacao[] newArray(int size) {
            return new Situacao[size];
        }
    };



    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSit_id() {
        return sit_id;
    }

    public void setSit_id(String sit_id) {
        this.sit_id = sit_id;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sit_id);
        parcel.writeString(descricao);
    }
}
package com.cursoandroid.testefb;

import android.os.Parcel;
import android.os.Parcelable;

public class GrupoUsuario implements Parcelable {
    private String gid;
    private String grupo;

    public GrupoUsuario(){
        
    }

    public GrupoUsuario(Parcel in) {
        gid = in.readString();
        grupo = in.readString();
    }


    public static final Creator<GrupoUsuario> CREATOR = new Creator<GrupoUsuario>() {
        @Override
        public GrupoUsuario createFromParcel(Parcel in) {
            return new GrupoUsuario(in);
        }

        @Override
        public GrupoUsuario[] newArray(int size) {
            return new GrupoUsuario[size];
        }
    };

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return grupo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gid);
        parcel.writeString(grupo);
    }
}
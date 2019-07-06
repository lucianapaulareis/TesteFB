package com.cursoandroid.testefb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeitoAdapter extends ArrayAdapter{

    private final Context context;
    private final ArrayList<Leito> leitos;

    public LeitoAdapter(Context context, ArrayList<Leito> leitos){
        super(context, R.layout.linha, leitos);
        this.context = context;
        this.leitos = leitos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView nomeLeito = (TextView) rowView.findViewById(R.id.nomeLeito);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);
        nomeLeito.setText(leitos.get(position).getNome());
        if(leitos.get(position).getSituacao().equals("Livre")) {
            imagem.setImageResource(leitos.get(position).getImagemLivre());
        }
        else if(leitos.get(position).getSituacao().equals("Ocupado")) {
            imagem.setImageResource(leitos.get(position).getImagemOcupado());
        }
        else if(leitos.get(position).getSituacao().equals("Em Higienização")) {
            imagem.setImageResource(leitos.get(position).getImagemHigienizacao());
        }
        else if(leitos.get(position).getSituacao().equals("Aguardando Higienização")) {
            imagem.setImageResource(leitos.get(position).getImagemAguardandoHigienizacao());
        }
        else if(leitos.get(position).getSituacao().equals("Em Forragem")) {
            imagem.setImageResource(leitos.get(position).getImagemEmForragem());
        }
        else if(leitos.get(position).getSituacao().equals("Aguardando Forragem")) {
            imagem.setImageResource(leitos.get(position).getImagemAguardandoForragem());
        }
        return rowView;
    }
}
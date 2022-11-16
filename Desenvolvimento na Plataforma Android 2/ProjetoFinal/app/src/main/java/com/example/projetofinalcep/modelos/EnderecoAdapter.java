package com.example.projetofinalcep.modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projetofinalcep.R;

import java.util.ArrayList;
import java.util.List;

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoAdapter.MyViewHolder> {
    private List<Endereco> enderecos = new ArrayList<Endereco>();
    private Context context;

    public EnderecoAdapter(List<Endereco> lista_enderecos, Context context){
        this.enderecos = lista_enderecos;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enderecos_adapter_view, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull EnderecoAdapter.MyViewHolder holder, int position) {
        holder.cep.setText(this.enderecos.get(position).getCep());
        holder.rua.setText(this.enderecos.get(position).getLogradouro());
        holder.numero.setText(this.enderecos.get(position).getComplemento());
        holder.bairro.setText(this.enderecos.get(position).getBairro());
        holder.cidade.setText(this.enderecos.get(position).getCidade());
        holder.estado.setText(this.enderecos.get(position).getUF());

    }

    @Override
    public int getItemCount() {
        return this.enderecos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cep, rua, numero, bairro, cidade, estado;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            cep = itemView.findViewById(R.id.txtCep);
            rua = itemView.findViewById(R.id.txtRua);
            numero = itemView.findViewById(R.id.txtNumero);
            bairro = itemView.findViewById(R.id.txtBairro);
            cidade = itemView.findViewById(R.id.txtCidade);
            estado = itemView.findViewById(R.id.txtUf);

        }

    }
}

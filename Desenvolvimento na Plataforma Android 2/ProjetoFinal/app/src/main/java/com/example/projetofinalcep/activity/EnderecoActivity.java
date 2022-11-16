package com.example.projetofinalcep.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projetofinalcep.R;
import com.example.projetofinalcep.helper.EnderecoDAO;
import com.example.projetofinalcep.helper.RecyclerItemClickListener;
import com.example.projetofinalcep.modelos.Endereco;
import com.example.projetofinalcep.modelos.EnderecoAdapter;

import java.util.ArrayList;
import java.util.List;


public class EnderecoActivity extends AppCompatActivity {
    //db
    private EnderecoAdapter enderecoAdapter;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<Endereco> listaEnderecos = new ArrayList<>();
    private Endereco enderecoSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        recyclerView = findViewById(R.id.recyclerview);
        //ver o que precisa ser feito aqui depois
        recyclerView.setAdapter(new EnderecoAdapter(listaEnderecos,this));
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    }

                    @Override
                    public void onItemClick(View view, int position) {
                        enderecoSelecionado = listaEnderecos.get(position);
                        Intent intent = new Intent(EnderecoActivity.this, MapsActivity.class);
                        intent.putExtra("endereco", enderecoSelecionado.getCep());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        enderecoSelecionado = listaEnderecos.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EnderecoActivity.this);
                        builder.setTitle("Confirmar exclusão");
                        builder.setMessage("Você deseja confirmar a remoção do cep: " + enderecoSelecionado.getCep() + "?");
                        builder.setPositiveButton("Sim, excluir!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EnderecoDAO enderecoDAO = new EnderecoDAO(getApplicationContext());
                                if (enderecoDAO.deletar(enderecoSelecionado)) {
                                    carregarEnderecos();
                                    Toast.makeText(getApplicationContext(), "Cep removido!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao remover o cep " + enderecoSelecionado.getCep() + "!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Não", null);
                        builder.create().show();
                    }
                }));
    }
    private void carregarEnderecos() {
        EnderecoDAO enderecoDAO = new EnderecoDAO(getApplicationContext());
        listaEnderecos = enderecoDAO.listar();
        enderecoAdapter = new EnderecoAdapter(listaEnderecos,this);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL));
        recyclerView.setAdapter(enderecoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarEnderecos();
    }
}
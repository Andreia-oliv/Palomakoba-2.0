package com.example.projetofinalcep.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetofinalcep.interfaces.InterfaceEnderecoDAO;
import com.example.projetofinalcep.modelos.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements InterfaceEnderecoDAO {
    private SQLiteDatabase leitura;
    private SQLiteDatabase escrita;

    public EnderecoDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        leitura = dbHelper.getReadableDatabase();
        escrita = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean salvar(Endereco endereco) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cep", endereco.getCep());
        contentValues.put("rua", endereco.getLogradouro());
        contentValues.put("numero", endereco.getComplemento());
        contentValues.put("bairro", endereco.getBairro());
        contentValues.put("cidade", endereco.getCidade());
        contentValues.put("uf", endereco.getUF());
        this.escrita.insert(DbHelper.TABELA_ENDERECOS, null, contentValues);
        return true;
    }

    @Override
    public boolean deletar(Endereco endereco) {
        String [] args = {String.valueOf(endereco.getId())};
        this.escrita.delete(DbHelper.TABELA_ENDERECOS, "id=?", args);
        return true;
    }

    @SuppressLint("Range")
    @Override
    public List<Endereco> listar(){
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_ENDERECOS + ";";
        Cursor cursor = leitura.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String cep = cursor.getString(cursor.getColumnIndex("cep"));
            String rua = cursor.getString(cursor.getColumnIndex("rua"));
            String numero = cursor.getString(cursor.getColumnIndex("numero"));
            String bairro = cursor.getString(cursor.getColumnIndex("bairro"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String uf = cursor.getString(cursor.getColumnIndex("uf"));
            enderecos.add(new Endereco(cep, rua, numero, bairro, cidade, uf, id));
        }

        return enderecos;
    }
}

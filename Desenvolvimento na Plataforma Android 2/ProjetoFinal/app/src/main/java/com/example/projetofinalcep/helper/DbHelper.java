package com.example.projetofinalcep.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;
    static final String NOME_DB = "DB_ENDERECOS";
    static final String TABELA_ENDERECOS = "enderecos";

    public DbHelper(@Nullable Context context){
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_ENDERECOS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "cep TEXT NOT NULL, " +
                "rua TEXT," +
                "numero TEXT," +
                "bairro TEXT," +
                "cidade TEXT," +
                "uf TEXT);";
        try{
            sqLiteDatabase.execSQL(sql);
            Log.i("Info DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("Info DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_ENDERECOS + ";";
        try{
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
            Log.i("Info DB", "Sucesso ao atualizar a tabela");
        }catch (Exception e){
            Log.i("Info DB", "Erro ao atualizar a tabela" + e.getMessage());
        }
    }
}

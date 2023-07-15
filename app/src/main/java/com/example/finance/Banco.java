package com.example.finance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper
{
    public static final String NOME_BANCO_DE_DADOS = "Banco";
    public static final int VERSAO_BANCO_DE_DADOS = 1;
    private static Banco instance;

    private Banco(Context context)
    {
        super(context, NOME_BANCO_DE_DADOS, null, VERSAO_BANCO_DE_DADOS);
    }
    public static Banco getInstance(Context context)
    {
        if(instance == null)
            instance = new Banco(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(UserBanco.sqlCreateLogin);
        db.execSQL(DespreceBanco.sqlCreateDesprece);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(UserBanco.scriptDropLogin);
        db.execSQL(DespreceBanco.scriptDropDesprece);
        onCreate(db);
    }
}


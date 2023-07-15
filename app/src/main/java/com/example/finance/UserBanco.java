package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserBanco implements InterfaceBanco
{
    public static final String TABLE_NAME = "Users";
    public static final String COLUNA_ID = "idUser";
    public static final String COLUNA_USERNAME = "Username";
    public static final String COLUNA_PASSWORD = "Password";
    public static final String sqlCreateLogin = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+  COLUNA_USERNAME + " TEXT," + COLUNA_PASSWORD + " TEXT" + ")";
    public static final String scriptDropLogin= "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase db;
    private static Banco Banco;
    private static UserBanco instance;

    public static UserBanco getInstance(Context context)
    {
        if(instance == null)
            instance = new UserBanco(context);
        return instance;
    }

    private UserBanco(Context context)
    {
        Banco = Banco.getInstance(context);
        db = Banco.getWritableDatabase();
        createData();
    }

    public void createData()
    {
        db = Banco.getWritableDatabase();
        db.execSQL(UserBanco.sqlCreateLogin);
    }

    public boolean adicionaUser(String nome, String senha)
    {
        db = Banco.getWritableDatabase();

        if (verificaCadastro(db,nome,senha) || verificaCadastro(db,nome))
        {
            return false;
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(COLUNA_USERNAME, nome);
            values.put(COLUNA_PASSWORD, senha);
            db.insert(TABLE_NAME, null, values);
            return  true;
        }
    }

    public boolean verificaCadastro(SQLiteDatabase db, String username, String password)
    {
        String[] projection = {COLUNA_USERNAME, COLUNA_PASSWORD};
        String selection = COLUNA_USERNAME + " = ?" + " AND " + COLUNA_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();

        return userExists;
    }

    public boolean verificaCadastro(SQLiteDatabase db, String username)
    {
        String[] projection = {COLUNA_USERNAME, COLUNA_PASSWORD};
        String selection = COLUNA_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();

        return userExists;
    }

    public Usuario getUsuarioLogado(Usuario user, String username, String senha)
    {
        SQLiteDatabase db = Banco.getReadableDatabase();

        String[] projection = {COLUNA_ID, COLUNA_USERNAME, COLUNA_PASSWORD}; // Colunas que deseja recuperar
        String selection = COLUNA_USERNAME + " = ?" + " AND " + COLUNA_PASSWORD + " = ?";
        String[] selectionArgs = {username, senha };

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        int index = cursor.getColumnIndex(COLUNA_USERNAME), indexx = cursor.getColumnIndex(COLUNA_PASSWORD), indexxx = cursor.getColumnIndex(COLUNA_ID);

        if (cursor.moveToFirst())
        {
            user = new Usuario(cursor.getString(index), cursor.getString(indexx), cursor.getInt(indexxx));
        }
        return user;
    }

    public void mostraUser(EditText user, EditText senha)
    {
        db = Banco.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int index = cursor.getColumnIndex(COLUNA_USERNAME), indexx = cursor.getColumnIndex(COLUNA_PASSWORD), indexxx = cursor.getColumnIndex(COLUNA_ID);

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(indexxx);
            String nome = cursor.getString(index);
            String senhaa = cursor.getString(indexx);
            user.setText(nome);
            senha.setText(senhaa);
        }
        cursor.close();
    }

    public boolean verificaLogin(String username, String password)
    {
        SQLiteDatabase db = Banco.getReadableDatabase();

        String[] projection = {COLUNA_USERNAME, COLUNA_PASSWORD};
        String selection = COLUNA_USERNAME + " = ?" + " AND " + COLUNA_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean existente = cursor.moveToFirst();

        return existente;
    }

    @Override
    public void salvar(Object obj) {}
}


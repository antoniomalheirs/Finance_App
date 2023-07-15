package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Switch;

public class DespreceBanco implements InterfaceBanco {

    public static final String TABLE_NAME = "Desprece";
    public static final String COLUNA_ID = "Iddesprece";
    public static final String COLUNA_IDUSER = "idUser";
    public static final String COLUNA_TIPO = "Desprece";
    public static final String COLUNA_DESCRICAO = "Descricao";
    public static final String COLUNA_VALOR = "Valor";
    public static final String sqlCreateDesprece = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_IDUSER + " INTEGER, " +
            COLUNA_TIPO + " TEXT, " +
            COLUNA_DESCRICAO + " TEXT, " +
            COLUNA_VALOR + " REAL" + ")";
    public static final String scriptDropDesprece= "DROP TABLE IF EXISTS " + TABLE_NAME;
    private SQLiteDatabase db;
    private static Banco Banco;
    private static DespreceBanco instance;
    public static DespreceBanco getInstance(Context context)
    {
        if(instance == null)
            instance = new DespreceBanco(context);
        return instance;
    }
    private DespreceBanco(Context context)
    {
        Banco = Banco.getInstance(context);
        db = Banco.getWritableDatabase();
    }
    public boolean adicionarDado(Desprece desprece)
    {
        //db = Banco.getReadableDatabase();

        if (verificaHistorico(db,desprece.getDescricao(),Float.toString(desprece.getValor()), desprece))
        {
           return false;
        }
        else
        {
            db = Banco.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUNA_DESCRICAO, desprece.getDescricao());
            values.put(COLUNA_VALOR, desprece.getValor());
            values.put(COLUNA_IDUSER, desprece.getIdUser());
            values.put(COLUNA_TIPO, desprece.getTipo());
            db.insert(TABLE_NAME, null, values);
            return true;
        }
    }
    public boolean verificaHistorico(SQLiteDatabase db,String descricao, String valor,  Desprece desprece) {
        db = Banco.getReadableDatabase();
        String[] projection = {COLUNA_IDUSER, COLUNA_TIPO, COLUNA_DESCRICAO, COLUNA_VALOR};
        String selection = COLUNA_IDUSER + " = ?" + " AND " + COLUNA_TIPO + " = ?" + " AND " + COLUNA_DESCRICAO + " = ?" + " AND " + COLUNA_VALOR + " = ?";
        String[] selectionArgs = {String.valueOf(desprece.getId()), desprece.getTipo(), descricao, valor};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean entryExists = cursor.moveToFirst();

        cursor.close();

        return entryExists;
    }
    public void mostraUser(EditText descricaoo, EditText valorr, Switch r, Switch d)
    {
        db = Banco.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int index = cursor.getColumnIndex(COLUNA_DESCRICAO), indexx = cursor.getColumnIndex(COLUNA_VALOR), indexxx = cursor.getColumnIndex(COLUNA_ID),indexxxx = cursor.getColumnIndex(COLUNA_IDUSER),indexxxxx = cursor.getColumnIndex(COLUNA_TIPO);

        while (cursor.moveToNext()) {
            String descricao = cursor.getString(index);
            Float valor = cursor.getFloat(indexx);
            float id = cursor.getFloat(indexxx);
            int iduser = cursor.getInt(indexxxx);
            String tipo = cursor.getString(indexxxxx);
            descricaoo.setText(descricao);
            valorr.setText(Float.toString(valor));

            if (tipo.compareTo("Receita") == 0)
            {
                r.setChecked(true);
                d.setChecked(false);
                //Desprece.setTipo("Despesa");
            }
            else if (tipo.compareTo("Despesa") == 0)
            {
                d.setChecked(true);
                r.setChecked(false);
                //Desprece.setTipo("Despesa");
            }



            // Faça algo com os dados lidos, como exibi-los em um TextView
        }
        cursor.close();
    }

    public void deleteData()
    {
        db = Banco.getWritableDatabase();
        db.execSQL(DespreceBanco.scriptDropDesprece);
    }
    public void createData()
    {
        db = Banco.getWritableDatabase();
        db.execSQL(DespreceBanco.scriptDropDesprece);
        db.execSQL(DespreceBanco.sqlCreateDesprece);
        ContentValues values = new ContentValues();
        values.put(COLUNA_DESCRICAO, "Default");
        values.put(COLUNA_VALOR, 192.7);
        values.put(COLUNA_TIPO, "Receita");
        db.insert(TABLE_NAME, null, values);
    }
    private ContentValues gerarContentValuesUsuario(Desprece desprece)
    {
        ContentValues values = new ContentValues();
        values.put(COLUNA_IDUSER, desprece.getIdUser());
        values.put(COLUNA_TIPO, desprece.getTipo());
        values.put(COLUNA_DESCRICAO, desprece.getDescricao());
        values.put(COLUNA_VALOR, desprece.getValor());
        // Serve para linkar a coluna com o registro:
        return values;
    }
    @Override
    public void salvar(Object obj)
    {
        Desprece desprece = (Desprece) obj;   // casting
        ContentValues values = gerarContentValuesUsuario(desprece);
        db.insert(TABLE_NAME, null, values);
    }
}


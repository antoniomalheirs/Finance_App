package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DespreceBanco implements InterfaceBanco
{
    public static final String TABLE_NAME = "Desprece";
    public static final String COLUNA_ID = "Iddesprece";
    public static final String COLUNA_IDUSER = "idUser";
    public static final String COLUNA_TIPO = "DespreceT";
    public static final String COLUNA_DESCRICAO = "Descricao";
    public static final String COLUNA_VALOR = "Valor";
    public static final String COLUNA_DATA = "Data";
    public static final String sqlCreateDesprece = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_IDUSER + " INTEGER, " + COLUNA_TIPO + " TEXT, " + COLUNA_DESCRICAO + " TEXT, " + COLUNA_DATA + " TEXT, " + COLUNA_VALOR + " INT" + ")";
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
        createData();
    }

    public void createData()
    {
        db = Banco.getWritableDatabase();
        db.execSQL(DespreceBanco.sqlCreateDesprece);
    }

    public boolean adicionarDado(Desprece desprece)
    {
        db = Banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_DESCRICAO, desprece.getDescricao());
        values.put(COLUNA_VALOR, desprece.getValor());
        values.put(COLUNA_IDUSER, desprece.getIdUser());
        values.put(COLUNA_TIPO, desprece.getTipo());
        values.put(COLUNA_DATA, desprece.getData());

        db.insert(TABLE_NAME, null, values);
        return true;
    }

    public void exibirPesquisaNoListView(ListView listView, Context context, String Data, String Descricao, int iduse)
    {
        db = Banco.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUNA_DATA + " = ? " + " AND " + COLUNA_IDUSER + " = ? " +"OR " + COLUNA_DESCRICAO + " = ? " + " AND " + COLUNA_IDUSER + " = ?", new String[]{Data, Integer.toString(iduse), Descricao, Integer.toString(iduse)}, null, null, null);
        int index = cursor.getColumnIndex(COLUNA_DESCRICAO), indexx = cursor.getColumnIndex(COLUNA_VALOR), indexxx = cursor.getColumnIndex(COLUNA_TIPO),indexxxx = cursor.getColumnIndex(COLUNA_DATA);
        List<String> dataList = new ArrayList<>();

        while (cursor.moveToNext())
        {
            String descricao = cursor.getString(index);
            int valor = cursor.getInt(indexx);
            String tipo = cursor.getString(indexxx);
            String data = cursor.getString(indexxxx);
            String linha = data + " - " + tipo + " - " + descricao + " - " + valor;

            dataList.add(linha);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, dataList)
        {
            @Override
            public View getView (int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                int cor = Color.parseColor("#FF0000");
                ((TextView) view).setTextColor(cor);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    public void exibirDadosNoListView(ListView listView, Context context, int iduse)
    {
        db = Banco.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUNA_IDUSER + " = ? ", new String[]{Integer.toString(iduse)}, null, null, null);
        int index = cursor.getColumnIndex(COLUNA_DESCRICAO), indexx = cursor.getColumnIndex(COLUNA_VALOR), indexxx = cursor.getColumnIndex(COLUNA_ID),indexxxx = cursor.getColumnIndex(COLUNA_IDUSER),indexxxxx = cursor.getColumnIndex(COLUNA_TIPO),indexxxxxx = cursor.getColumnIndex(COLUNA_DATA);
        List<String> dataList = new ArrayList<>();

        while (cursor.moveToNext())
        {
            String descricao = cursor.getString(index);
            int valor = cursor.getInt(indexx);
            int id = cursor.getInt(indexxx);
            int iduser = cursor.getInt(indexxxx);
            String tipo = cursor.getString(indexxxxx);
            String data = cursor.getString(indexxxxxx);
            String linha = data + " - " + iduser + " - " + tipo + " - " + descricao + " - " + valor;

            dataList.add(linha);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, dataList)
        {
            @Override
            public View getView (int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                int cor = Color.parseColor("#FF0000");
                ((TextView) view).setTextColor(cor);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void salvar(Object obj) {}
}


package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Ophistorico extends AppCompatActivity
{
    private final Handler handler = new Handler();
    private Runnable runnable;

    ListView displaylistdados;
    EditText descricao,data;
    TextView displayuser;

    DespreceBanco BancoD;
    int iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ophistorico);

        displayuser = findViewById(R.id.displayuser);
        displaylistdados = findViewById(R.id.displaylistdados);
        data = findViewById(R.id.displaydata);
        descricao = findViewById(R.id.displaydescricao);

        BancoD = DespreceBanco.getInstance(this);
        iduser = getIntent().getIntExtra ("idu",0);
        BancoD.exibirDadosNoListView(displaylistdados, getBaseContext(),iduser);

        String name = getIntent().getStringExtra("nome");
        displayuser.setText(name + " este é seu");
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.fecharhistorico)
        {
            finish();
        }
    }
}
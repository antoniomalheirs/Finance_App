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

        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                BancoD.exibirDadosNoListView(displaylistdados, getBaseContext(),iduser);
                handler.postDelayed(this, 35000);
            }
        };

        handler.postDelayed(runnable, 1000);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.fecharhistorico)
        {
            finish();
        }

        if (v.getId() == R.id.pesquisarhistorico)
        {
            String da = data.getText().toString(),de = descricao.getText().toString();

            if (da.isEmpty() && de.isEmpty())
            {
                Toast.makeText(this, "Preencha um dos campos para pesquisar", Toast.LENGTH_SHORT).show();
            }
            else
            {
                BancoD.exibirPesquisaNoListView(displaylistdados, getBaseContext(),data.getText().toString(),descricao.getText().toString(),iduser);
            }
        }
    }
}
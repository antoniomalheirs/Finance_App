package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Oplanca extends AppCompatActivity
{
    EditText descricao,valor;
    Switch receita,despesa;

    Date Data;
    SimpleDateFormat DataF;

    ListView listdados;

    TextView Tuser;

    private final Handler handler = new Handler();
    private Runnable runnable;

    UserBanco BancoU;
    Usuario User;
    DespreceBanco BancoD;
    Desprece Desprece;

    public  void updateUser(TextView u)
    {
        Tuser = u;
        u.setText(User.getNome() + ", Bem Vindo ao Family");
    }

    public void onClickop( View v)
    {
        if (v.getId() == R.id.adicionar)
        {
            String ddescricao = descricao.getText().toString(), vvalor = valor.getText().toString();

            if (ddescricao.isEmpty() || vvalor.isEmpty() || !receita.isChecked() && !despesa.isChecked())
            {
                Toast.makeText(this, "Por Favor, Informe os dados a serem gravados", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Desprece = new Desprece(User, Desprece.getTipo(), ddescricao, Float.parseFloat(vvalor),DataF.format(Data));

                boolean log = BancoD.adicionarDado(Desprece);

                if (log)
                {
                    Toast.makeText(this, "Lançamento gravado", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Erro de gravação", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (v.getId() == R.id.historico)
        {
            Intent Ophist = new Intent(getApplicationContext(),Ophistorico.class);
            Ophist.putExtra("nome",User.getNome());
            Ophist.putExtra("idu",User.getId());
            startActivity(Ophist);
        }

        if (v.getId() == R.id.sair)
        {
            finish();
        }
    }

    public void onClickswiop( View v)
    {
        if (v.getId() == R.id.despesa)
        {
            receita.setChecked(false);
            Desprece.setTipo("Despesa");
        }

        if (v.getId() == R.id.receita)
        {
           despesa.setChecked(false);
            Desprece.setTipo("Receita");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oplanca);

        Tuser =findViewById(R.id.username);
        descricao = findViewById(R.id.descricao);
        despesa = findViewById(R.id.despesa);
        receita = findViewById(R.id.receita);
        valor = findViewById(R.id.valor);
        listdados = findViewById(R.id.listdados);

        BancoU = UserBanco.getInstance(this);
        BancoD = DespreceBanco.getInstance(this);
        DataF = new SimpleDateFormat("dd/YY");
        Data = new Date();

        String name = getIntent().getStringExtra("nome");
        String senha = getIntent().getStringExtra("senha");
        User = BancoU.getUsuarioLogado(User,name,senha);
        Desprece = new Desprece(User);
        BancoD.exibirDadosNoListView(listdados, this,User.getId());
        updateUser(Tuser);

        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                BancoD.exibirDadosNoListView(listdados, getBaseContext(),User.getId());
                handler.postDelayed(this, 15000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
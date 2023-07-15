package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Oplanca extends AppCompatActivity {

    Switch receita,despesa;
    TextView Tuser;
    EditText descricao,valor;
    UserBanco BancoU;
    DespreceBanco BancoD;
    Usuario User;
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
                Desprece = new Desprece(User, Desprece.getTipo(), ddescricao, Float.parseFloat(vvalor));

                boolean log = BancoD.adicionarDado(Desprece);

                if (log == true)
                {
                    Toast.makeText(this, "S", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "N", Toast.LENGTH_SHORT).show();
                }


            }
        }

        if (v.getId() == R.id.historico)
        {
            Toast.makeText(this, User.getSenha(), Toast.LENGTH_SHORT).show();
            BancoD.mostraUser(descricao,valor, receita, despesa);
            //Intent Op = new Intent(getApplicationContext(),Ophistorico.class);
            //startActivity(Op);
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
        BancoU = UserBanco.getInstance(this);
        BancoD = DespreceBanco.getInstance(this);
        BancoD.createData();
        Tuser =findViewById(R.id.username);
        descricao = findViewById(R.id.descricao);
        valor = findViewById(R.id.valor);
        despesa = findViewById(R.id.despesa);
        receita = findViewById(R.id.receita);
        String name = getIntent().getStringExtra("nome");
        String senha = getIntent().getStringExtra("senha");
        User = BancoU.getUsuarioLogado(User,name,senha);
        Desprece = new Desprece(User);
        updateUser(Tuser);
    }
}
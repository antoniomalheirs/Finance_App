package com.example.finance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
{
    EditText user,senha;
    UserBanco Banco;
    Usuario User;
    public void onClick(View v)
    {
        if (v.getId() == R.id.validar)
        {
            boolean log = Banco.verificaLogin(user.getText().toString(),senha.getText().toString());

            if (log == true)
            {
                Intent Op = new Intent(getApplicationContext(),Oplanca.class);
                Op.putExtra("nome",user.getText().toString());
                Op.putExtra("senha",senha.getText().toString());

                Toast.makeText(this, "Acesso Permitido", Toast.LENGTH_SHORT).show();
                startActivity(Op);
            }
            else
            {
                Toast.makeText(this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.criar)
        {
            String nome = user.getText().toString(), senhaaa = senha.getText().toString();

            if (nome.isEmpty()  || senhaaa.isEmpty())
            {
                Toast.makeText(this, "Preencha os campos Login e Senha", Toast.LENGTH_SHORT).show();
            }
            else
            {
                boolean log = Banco.adicionaUser(nome,senhaaa);

                if (log == true )
                {
                    Toast.makeText(this, "Usuário Cadastrados", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Usuário existente", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Banco = UserBanco.getInstance(this);
        User = new Usuario("Jonas","1234");
        user = findViewById(R.id.enuser);
        senha =findViewById(R.id.ensenha);
    }
}
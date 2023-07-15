package com.example.finance;

import android.telephony.mbms.StreamingServiceInfo;

public class Usuario {

    private int idUser;
    private String nome;
    private String senha;

    public Usuario(String nome, String senha, int idUser)
    {
        this.nome = nome;
        this.senha  = senha;
        this.idUser = idUser;
    }

    public Usuario(String nome, String senha)
    {
        this.nome = nome;
        this.senha = senha ;
    }

    public String getNome()
    {
        return nome;
    }

    public String getSenha()
    {
        return senha;
    }

    public int getId()
    {
        return idUser;
    }
}

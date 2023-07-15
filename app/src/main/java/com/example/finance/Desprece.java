package com.example.finance;

public class Desprece {

    private int Iddesprece;
    private int IdUser;
    private String Tipo;
    private String Descricao;
    private float Valor;
    public Desprece(Usuario user)
    {
        this.IdUser = user.getId();
    }
    public Desprece(Usuario user,String Tipo, String Descricao, float Valor)
    {
        this.IdUser = user.getId();
        this.Tipo = Tipo;
        this.Descricao = Descricao;
        this.Valor = Valor;
    }
    public int getId()
    {
        return Iddesprece;
    }
    public int getIdUser()
    {
        return IdUser;
    }
    public String getTipo()
    {
        return Tipo;
    }
    public void setTipo(String tipo)
    {
        this.Tipo = tipo;
    }
    public String getDescricao()
    {
        return Descricao;
    }
    public float getValor() {return Valor; }
}

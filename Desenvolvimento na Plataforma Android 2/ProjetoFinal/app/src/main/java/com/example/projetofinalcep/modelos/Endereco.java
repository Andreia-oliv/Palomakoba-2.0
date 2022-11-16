package com.example.projetofinalcep.modelos;


import java.io.Serializable;

public class Endereco implements Serializable {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String UF;
    private long id;

    public Endereco(String cep, String nomeRua, String numeroRua, String nomeBairro, String nomeCidade, String UF, long id) {
        this.cep = cep;
        this.logradouro = nomeRua;
        this.complemento = numeroRua;
        this.bairro = nomeBairro;
        this.cidade = nomeCidade;
        this.UF = UF;
        this.id = id;
    }

    public Endereco(){

    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getUF(){
        return UF;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public long getId() {
        return id;
    }

}

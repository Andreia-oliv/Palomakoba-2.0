package com.example.projetofinalcep.interfaces;

import com.example.projetofinalcep.modelos.Endereco;

import java.util.List;

public interface InterfaceEnderecoDAO {
    public boolean salvar(Endereco endereco);
    public boolean deletar(Endereco endereco);
    public List<Endereco> listar();
}

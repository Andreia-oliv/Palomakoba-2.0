package com.example.projetofinalcep.interfaces;

import com.example.projetofinalcep.modelos.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {
    @GET("{cep}/json/")
    Call<CEP> consultarCEP(@Path("cep") String cep);
}

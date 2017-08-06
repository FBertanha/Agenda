package br.com.alura.agenda.services;

import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.modelo.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Felipe on 25/07/2017.
 */

public interface AlunoService {

    @POST("aluno")
    Call<Void> insert(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSync> list();

    @DELETE("aluno/{id}")
    Call<Void> delete(@Path("id") String id);
}

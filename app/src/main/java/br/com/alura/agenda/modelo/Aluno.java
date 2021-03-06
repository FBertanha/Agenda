package br.com.alura.agenda.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by felipe on 15/04/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aluno implements Serializable {

    private String id;
    private String nome;
    private String endereco;
    private String telefone;
    private String site;
    private Float nota;
    private String caminhoFoto;
    private int desativado;
    private int sicronizado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public int getDesativado() {
        return desativado;
    }

    public boolean isDesativado() {
        return desativado == 1;
    }

    public void setDesativado(int desativado) {
        this.desativado = desativado;
    }

    public int getSicronizado() {
        return sicronizado;
    }

    public boolean isSicronizado() {
        return sicronizado == 1;
    }

    public void setSicronizado(int sicronizado) {
        this.sicronizado = sicronizado;
    }

    public void sicroniza() {
        sicronizado = 1;
    }

    public void desicroniza() {
        sicronizado = 0;
    }

    public void desativa() {
        desativado = 1;
        desicroniza();
    }
}

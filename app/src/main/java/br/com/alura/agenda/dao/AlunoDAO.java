package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by felipe on 15/04/17.
 */

public class AlunoDAO extends SQLiteOpenHelper{


    public AlunoDAO(Context context) {
        super(context, "agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table alunos (" +
                "id integer primary key autoincrement, " +
                "nome varchar(255) not null, " +
                "endereco varchar(255), " +
                "telefone varchar(12), " +
                "site varchar(255), " +
                "nota real, " +
                "caminhoFoto varchar(255)" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "alter table alunos " +
                        "add column caminhoFoto varchar(255)";

        }
        db.execSQL(sql);
        //onCreate(db);
    }

    public void addAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosAluno(aluno);

        db.insert("alunos", null, dados);
    }

    @NonNull
    private ContentValues getDadosAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();

        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());

        return dados;
    }

    public List<Aluno> allAlunos() {
        String sql = "SELECT * FROM alunos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos =  new ArrayList<Aluno>();

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getFloat(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public void deleteAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};

        db.delete("alunos", "id = ?", params);
    }

    public void editAluno(Aluno aluno) {
        SQLiteDatabase db  = getWritableDatabase();
        ContentValues dados = getDadosAluno(aluno);

        String[] params = {aluno.getId().toString()};

        db.update("alunos", dados, "id = ?", params);
    }

    public Aluno findAlunoByTelefone(String campo) {
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {campo};
        Cursor c = db.rawQuery("SELECT * FROM alunos WHERE telefone = ?", params);

        if (c.moveToFirst()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getFloat(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            c.close();

            return aluno;
        }
        c.close();
        return null;
    }

    public boolean isAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM alunos where telefone = ?", new String[] {telefone});
        int resultados = c.getCount();
        c.close();

        return resultados > 0;
    }


}

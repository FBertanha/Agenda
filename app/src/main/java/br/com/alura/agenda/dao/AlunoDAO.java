package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by felipe on 15/04/17.
 */

public class AlunoDAO extends SQLiteOpenHelper{


    public AlunoDAO(Context context) {
        super(context, "agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos (" +
                "id CHAR(36) PRIMARY KEY, " +
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
                sql = "";
        }
        db.execSQL(sql);
        //onCreate(db);
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

    public void addAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        if (aluno.getId() == null)
            aluno.setId(geraUUID());
        ContentValues dados = getDadosAluno(aluno);
        db.insert("alunos", null, dados);
        //aluno.setId(id);
    }

    @NonNull
    private ContentValues getDadosAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
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

        List<Aluno> alunos = populaAlunos(c);
        c.close();
        return alunos;
    }

    @NonNull
    private List<Aluno> populaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getString(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getFloat(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        return alunos;
    }

    public void deleteAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId()};

        db.delete("alunos", "id = ?", params);
    }

    public void editAluno(Aluno aluno) {
        SQLiteDatabase db  = getWritableDatabase();
        ContentValues dados = getDadosAluno(aluno);

        String[] params = {aluno.getId()};

        db.update("alunos", dados, "id = ?", params);
    }

    public Aluno findAlunoByTelefone(String campo) {
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {campo};
        Cursor c = db.rawQuery("SELECT * FROM alunos WHERE telefone = ?", params);

        if (c.moveToFirst()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getString(c.getColumnIndex("id")));
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


    public void syncAlunos(List<Aluno> alunos) {
        for (Aluno aluno :
                alunos) {
            if (!exists(aluno))
                addAluno(aluno);
            else
                editAluno(aluno);

        }
    }

    private boolean exists(Aluno aluno) {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery("SELECT id FROM alunos WHERE id = ? LIMIT 1", new String[]{aluno.getId()});
        boolean b = cursor.getCount() > 0;
        cursor.close();
        return b;
    }
}

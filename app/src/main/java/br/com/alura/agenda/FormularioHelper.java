package br.com.alura.agenda;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by felipe on 15/04/17.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        campoNome = (EditText)activity.findViewById(R.id.formulario_nome);

        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);

        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);

        campoSite = (EditText) activity.findViewById(R.id.formulario_site);

        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);

        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota((float) campoNota.getProgress());
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void setAluno(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        loadFoto(aluno.getCaminhoFoto());
        this.aluno = aluno;

    }

    public void loadFoto(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            bitmap = bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

    }
}

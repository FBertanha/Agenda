package br.com.alura.agenda.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;

/**
 * Created by Felipe on 06/08/2017.
 */

public class AgendaMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        Log.i("onMessageReceived: ", data.toString());
        converteParaAluno(data);


    }

    private void converteParaAluno(Map<String, String> data) {
        String chaveDeAcesso = "alunoSync";
        if (data.containsKey(chaveDeAcesso)) {
            String json = data.get(chaveDeAcesso);

            ObjectMapper mapper = new ObjectMapper();

            try {
                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);
                AlunoDAO alunoDAO = new AlunoDAO(this);
                alunoDAO.syncAlunos(alunoSync.getAlunos());
                alunoDAO.close();

                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new AtualizaListaAlunoEvent());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

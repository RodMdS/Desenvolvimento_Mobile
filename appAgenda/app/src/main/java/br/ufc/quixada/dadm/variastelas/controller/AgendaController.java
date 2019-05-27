package br.ufc.quixada.dadm.variastelas.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.quixada.dadm.variastelas.transactions.Agenda;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class AgendaController {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String TEMPKEY = "myagenda";
    private Agenda agenda;
    private static int id = 0;
    private static String nome = "Pessoa";
    private static int idade = 22;

    public AgendaController() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //Log.d("WHOAMI", databaseReference.getKey());

        initializeAgenda();
    }

    private void initializeAgenda() {
        Log.d("FROM_BEGIN", "INICIALIZANDO DATABASE");
        agenda = new Agenda(nome, id, idade);
        databaseReference.child(TEMPKEY).setValue(agenda);
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void updateAgenda() {
        Map<String, Object> update = new HashMap<>();
        update.put(TEMPKEY, agenda);
        databaseReference.updateChildren(update);
    }

    public void addContato(Contato contato) {
        if(contato != null) {
            if(agenda.getListaTelefone() == null) {
                agenda.setListaTelefone(new ArrayList<Contato>());
            }
            agenda.getListaTelefone().add(contato);
            updateAgenda();
        }
    }

    public void editContato(int id, String nome, String telefone, String endereco) {
        if(agenda.getListaTelefone() != null) {
            if(nome != "" && telefone != "" && endereco != "") {
                for(Contato contato : agenda.getListaTelefone()) {
                    if(contato.getId() == id) {
                        contato.setNome(nome);
                        contato.setEndereco(endereco);
                        contato.setTelefone(telefone);
                    }
                }
                updateAgenda();
            }
        }
    }

    public void rmContato(int selected) {
        agenda.getListaTelefone().remove(selected);
        updateAgenda();
    }

    private void getAgendaFromFirebase() {
        Log.d("CHAMEI", "essa desgraça está rodando");
//        final Agenda[] temp = new Agenda[1];
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("WHY?", "WTF????????");
//                if(dataSnapshot.getKey().equals(TEMPKEY)) {
//                    Log.d("WHOAMI", dataSnapshot.getKey());
//                    temp[0] = dataSnapshot.getValue(Agenda.class);
//                            /*agenda = snapFather.getValue(Agenda.class);
//                            Log.d("AGENDA", agenda.toString());
//                            if(agenda.getListaTelefone() == null) {
//                                agenda.setListaTelefone(new ArrayList<Contato>());
//                            }*/
//                }
//
//                initializeAgenda(temp[0]);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("error:", databaseError.toException());
//            }
//        });
    }
}

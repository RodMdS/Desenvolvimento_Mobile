package br.ufc.quixada.dadm.variastelas.transactions;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContatoDao {

    @Query("SELECT * FROM contato")
    List<Contato> getAll();

    @Query("SELECT * FROM contato WHERE id IN (:contatoIds)")
    List<Contato> loadAllByIds(int[] contatoIds);

    @Query("SELECT * FROM contato WHERE nome LIKE :nome LIMIT 1")
    Contato findByName(String nome);

    @Query("SELECT * FROM contato WHERE id LIKE :id LIMIT 1")
    Contato findById(int id);

    @Insert
    void insertAll(Contato... contatos);

    @Delete
    void delete(Contato contato);

    @Update
    void update(Contato contato);
}


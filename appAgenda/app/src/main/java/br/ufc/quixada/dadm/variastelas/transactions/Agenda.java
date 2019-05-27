package br.ufc.quixada.dadm.variastelas.transactions;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

    String nome;
    int id;
    int idade;
    List<Contato> listaTelefone;

    public Agenda() {}

    public Agenda(String nome, int idade, int id) {
        super();
        this.nome = nome;
        this.id = id;
        this.idade = idade;
        this.listaTelefone = new ArrayList<Contato>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Contato> getListaTelefone() {
        return listaTelefone;
    }
    public void setListaTelefone(List<Contato> listaTelefone) {
        this.listaTelefone = listaTelefone;
    }

    @Override
    public String toString() {
        if(this.listaTelefone == null) {
            return "Agenda_ToString_[nome: " + nome +
                    ", id: " + id +
                    ", idade: " + idade + "]";
        }
        return "Agenda_ToString_[nome: " + nome +
                ", id: " + id +
                ", idade: " + idade +
                ", quantidade de contatos: " + this.listaTelefone.size() + "]";
    }



}

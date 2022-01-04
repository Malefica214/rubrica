package com.example.demoSpring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//desfinisce la persistenza
@Entity
public class Voce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generazione id univoca del valore di id
    Long id;

    @NotEmpty(message = "Il campo è richiesto")
    @Size(min = 2, max=55, message = "I caratteri devono essre compresi tra 2 e 33")
    String nome, cognome, telefono;

    public Voce(){}

    public Voce(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Voce{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}

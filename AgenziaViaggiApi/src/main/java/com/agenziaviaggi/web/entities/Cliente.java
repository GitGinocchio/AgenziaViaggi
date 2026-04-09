package com.agenziaviaggi.web.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Entity
@Table(name = "CLIENTI", schema = "APP")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="NOME", nullable = false)
    private String nome;
    @Column(name="EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name="PREFERENZE")
    private String preferenze;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    public Cliente() {}

    public Cliente(int id, String nome, String email, String preferenze) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.preferenze = preferenze;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferenze() {
        return preferenze;
    }

    public void setPreferenze(String preferenze) {
        this.preferenze = preferenze;
    }
}

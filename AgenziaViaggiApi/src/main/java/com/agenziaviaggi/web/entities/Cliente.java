package com.agenziaviaggi.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Giulio Tognetto
 */
@Entity
@Table(name = "CLIENTI", schema = "APP")
public class Cliente {
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

    public Cliente() {}

    public Cliente(int id, String nome, String email, String preferenze) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.preferenze = preferenze;
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

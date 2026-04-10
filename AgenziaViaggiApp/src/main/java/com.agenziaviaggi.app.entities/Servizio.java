/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.app;

/**
 *
 * @author Giulio Tognetto
 */
public class Servizio {
    private Integer id;
    private int idPacchetto;
    private String tipo;
    private String nome;
    private double prezzo = 0.0;
    private String descrizione;
    private String immagine;

    public Servizio() {}

    public Servizio(Integer id, int idPacchetto, String tipo, String nome, String descrizione, String immagine) {
        this.id = id;
        this.idPacchetto = idPacchetto;
        this.tipo = tipo;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
    
}
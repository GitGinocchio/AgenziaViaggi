/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.web.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Giulio Tognetto
 */
@Entity
@Table(name = "SERVIZI", schema = "APP")
public class Servizio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    //@Column(name="ID_PACCHETTO", nullable=false)
    //private int idPacchetto;
    @Column(name="TIPO")
    private String tipo;
    @Column(name="NOME", nullable=false)
    private String nome;
    @Column(name="PREZZO", nullable=false)
    private double prezzo = 0.0;
    @Column(name="DESCRIZIONE")
    private String descrizione;
    @Column(name="IMMAGINE")
    private String immagine;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACCHETTO", nullable = false)
    @JsonbTransient
    private Pacchetto pacchetto;

    public Servizio() {}

    public Servizio(Integer id, int idPacchetto, String tipo, String nome, String descrizione, String immagine) {
        this.id = id;
        //this.idPacchetto = idPacchetto;
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

    public void setPacchetto(Pacchetto pacchetto) {
        this.pacchetto = pacchetto;
    }

    /*
    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }
    */
    
    public Integer getIdPacchetto() {
        if (this.pacchetto == null) return null;
        return this.pacchetto.getId();
    }

    public Pacchetto getPacchetto() {
        return pacchetto;
    }
    
    public void setIdPacchetto(int idPacchetto) {
        if (this.pacchetto == null) {
            this.pacchetto = new Pacchetto();
        }
        
        this.pacchetto.setId(idPacchetto);
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.web.entities;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
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
    private Integer id;
    private String tipo;
    private String nome;
    private double prezzo;
    private String descrizione;
    private String immagine;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACCHETTO", nullable = false)
    @JsonbTransient
    private Pacchetto pacchetto;

    public Servizio() {}

    @JsonbProperty("idPacchetto")
    public Integer getIdPacchetto() {
        return (pacchetto != null) ? pacchetto.getId() : null;
    }

    public void setIdPacchetto(Integer idPacchetto) {
        if (idPacchetto == null) { this.pacchetto = null; return; }
        if (this.pacchetto == null) this.pacchetto = new Pacchetto();
        this.pacchetto.setId(idPacchetto);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public Pacchetto getPacchetto() { return pacchetto; }
    public void setPacchetto(Pacchetto pacchetto) { this.pacchetto = pacchetto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
}
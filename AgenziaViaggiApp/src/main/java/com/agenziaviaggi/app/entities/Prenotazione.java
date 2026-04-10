/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.app.entities;

/**
 *
 * @author Giulio Tognetto
 */
import java.time.Instant;
import java.util.Date;

public class Prenotazione {
    private Integer id;
    private int idCliente;
    private int idPacchetto;
    private Date dataPrenotazione = Date.from(Instant.now());
    private double prezzoPagato;
    private String stato = "PENDENTE";

    public Prenotazione() {}

    public Prenotazione(int id, int idCliente, int idPacchetto, Date dataPrenotazione, String stato) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPacchetto = idPacchetto;
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public Double getPrezzoPagato() {
        return prezzoPagato;
    }

    public void setPrezzoPagato(Double prezzoPagato) {
        this.prezzoPagato = prezzoPagato;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}

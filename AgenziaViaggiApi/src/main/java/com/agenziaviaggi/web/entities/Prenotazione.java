/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.web.entities;

/**
 *
 * @author Giulio Tognetto
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "PRENOTAZIONI", schema = "APP")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="ID_CLIENTE", nullable = false)
    private int idCliente;
    @Column(name="ID_PACCHETTO", nullable = false)
    private int idPacchetto;
    @Column(name="DATA_PRENOTAZIONE")
    private Date dataPrenotazione = Date.from(Instant.now());
    @Column(name="PREZZO_PAGATO", nullable = false)
    private double prezzoPagato;
    @Column(name="STATO", nullable = false)
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

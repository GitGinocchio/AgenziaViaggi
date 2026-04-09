/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agenziaviaggi.web.entities;

/**
 *
 * @author Giulio Tognetto
 */
import jakarta.json.bind.annotation.JsonbProperty;
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
import java.util.Date;

@Entity
@Table(name = "PRENOTAZIONI", schema = "APP")
public class Prenotazione implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="DATA_PRENOTAZIONE")
    private Date dataPrenotazione = new Date();

    @Column(name="PREZZO_PAGATO", nullable = false)
    private double prezzoPagato;

    @Column(name="STATO", nullable = false)
    private String stato = "PENDENTE";
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACCHETTO")
    private Pacchetto pacchetto;

    public Prenotazione() {}

    @JsonbProperty("idCliente")
    public Integer getIdCliente() {
        return (cliente != null) ? cliente.getId() : null;
    }

    @JsonbProperty("idPacchetto")
    public Integer getIdPacchetto() {
        return (pacchetto != null) ? pacchetto.getId() : null;
    }

    public void setIdCliente(Integer idCliente) {
        if (idCliente == null) { this.cliente = null; return; }
        if (this.cliente == null) this.cliente = new Cliente();
        this.cliente.setId(idCliente);
    }

    public void setIdPacchetto(Integer idPacchetto) {
        if (idPacchetto == null) { this.pacchetto = null; return; }
        if (this.pacchetto == null) this.pacchetto = new Pacchetto();
        this.pacchetto.setId(idPacchetto);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Date getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(Date dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }
    public double getPrezzoPagato() { return prezzoPagato; }
    public void setPrezzoPagato(double prezzoPagato) { this.prezzoPagato = prezzoPagato; }
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Pacchetto getPacchetto() { return pacchetto; }
    public void setPacchetto(Pacchetto pacchetto) { this.pacchetto = pacchetto; }
}

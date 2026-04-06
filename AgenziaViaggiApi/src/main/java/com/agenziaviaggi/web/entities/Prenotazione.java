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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    //@Column(name="ID_CLIENTE", nullable = false)
    //private int idCliente;
    //@Column(name="ID_PACCHETTO", nullable = false)
    //private int idPacchetto;
    @Column(name="DATA_PRENOTAZIONE")
    private Date dataPrenotazione = Date.from(Instant.now());
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

    public Prenotazione(int id, int idCliente, int idPacchetto, Date dataPrenotazione, String stato) {
        this.id = id;
        //this.idCliente = idCliente;
        //this.idPacchetto = idPacchetto;
        this.setIdCliente(idCliente);
        this.setIdPacchetto(idPacchetto);
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /*
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    */
    
    public int getIdCliente() {
        return (cliente != null) ? cliente.getId() : 0;
    }
    
    public final void setIdCliente(int idCliente) {
        if (this.cliente == null) {
            this.cliente = new Cliente();
        }
        this.cliente.setId(idCliente);
    }
    
    public Integer getIdPacchetto() {
        if (this.pacchetto == null) return null;
        return this.pacchetto.getId();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPacchetto(Pacchetto pacchetto) {
        this.pacchetto = pacchetto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pacchetto getPacchetto() {
        return pacchetto;
    }
    
    public final void setIdPacchetto(int idPacchetto) {
        if (this.pacchetto == null) {
            this.pacchetto = new Pacchetto();
        }
        
        this.pacchetto.setId(idPacchetto);
    }
    
    /*
    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }
    */
    

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

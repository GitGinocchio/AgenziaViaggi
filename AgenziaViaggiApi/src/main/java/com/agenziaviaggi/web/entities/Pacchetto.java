package com.agenziaviaggi.web.entities;

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
@Table(name = "PACCHETTI", schema = "APP")
public class Pacchetto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="TITOLO")
    private String titolo;
    @Column(name="DESCRIZIONE")
    private String descrizione;
    @Column(name="TAGS")
    private String tags;
    @Column(name="PREZZO", nullable = false)
    private Double prezzo;
    @Column(name="IS_OFFERTA")
    private Boolean isOfferta = false;
    @Column(name="IMMAGINE")
    private String immagine;
    
    @OneToMany(mappedBy = "pacchetto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servizio> servizi = new ArrayList<>();

    public Pacchetto(Integer id, String titolo, String descrizione, String tags, Double prezzo, String immagine) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tags = tags;
        this.prezzo = prezzo;
        this.immagine = immagine;
    }

    public Pacchetto() {}

    public List<Servizio> getServizi() {
        return servizi;
    }

    public void setServizi(List<Servizio> servizi) {
        this.servizi = servizi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Boolean getIsOfferta() {
        return isOfferta;
    }

    public void setIsOfferta(Boolean isOfferta) {
        this.isOfferta = isOfferta;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
    
    
}

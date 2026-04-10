package com.agenziaviaggi.app.entities;

/**
 *
 * @author Giulio Tognetto
 */
public class Pacchetto {
    private Integer id;
    private String titolo;
    private String descrizione;
    private String tags;
    private Double prezzo;
    private Boolean isOfferta = false;
    private String immagine;

    public Pacchetto(Integer id, String titolo, String descrizione, String tags, Double prezzo, String immagine) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tags = tags;
        this.prezzo = prezzo;
        this.immagine = immagine;
    }

    public Pacchetto() {}

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

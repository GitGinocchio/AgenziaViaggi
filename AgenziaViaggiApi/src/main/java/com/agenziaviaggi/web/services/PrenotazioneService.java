package com.agenziaviaggi.web.services;

import com.agenziaviaggi.web.entities.Prenotazione;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Stateless
public class PrenotazioneService {
    @PersistenceContext
    private EntityManager em;

    public List<Prenotazione> findAll() {
        return em.createQuery(
            "SELECT p FROM Prenotazione p JOIN FETCH p.cliente JOIN FETCH p.pacchetto", 
            Prenotazione.class)
            .getResultList();
    }

    public Prenotazione findById(Integer id) {
        try {
            return em.createQuery(
                "SELECT p FROM Prenotazione p JOIN FETCH p.cliente JOIN FETCH p.pacchetto WHERE p.id = :id", 
                Prenotazione.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Prenotazione> findByCliente(Integer idCliente) {
        return em.createQuery(
            "SELECT p FROM Prenotazione p " +
            "JOIN FETCH p.pacchetto " +
            "WHERE p.cliente.id = :id", Prenotazione.class)
            .setParameter("id", idCliente)
            .getResultList();
    }
    
    public void save(Prenotazione p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            em.merge(p);
        }
    }

    public void updateStato(Integer id, String nuovoStato) {
        Prenotazione p = em.find(Prenotazione.class, id);
        if (p != null) {
            p.setStato(nuovoStato);
        }
    }

    public void delete(Integer id) {
        Prenotazione p = em.find(Prenotazione.class, id);
        if (p != null) {
            em.remove(p);
        }
    }
}
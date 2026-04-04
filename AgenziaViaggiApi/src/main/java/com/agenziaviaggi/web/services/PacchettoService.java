package com.agenziaviaggi.web.services;

import com.agenziaviaggi.web.entities.Pacchetto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Stateless
public class PacchettoService {
    @PersistenceContext
    private EntityManager em;

    public List<Pacchetto> findAll() {
        return em.createQuery("SELECT p FROM Pacchetto p", Pacchetto.class).getResultList();
    }

    public Pacchetto findById(Integer id) {
        return em.find(Pacchetto.class, id);
    }
    
    public Pacchetto findByPrenotazione(Integer idPrenotazione) {
        try {
            return em.createQuery(
                "SELECT pr.pacchetto FROM Prenotazione pr WHERE pr.id = :id", 
                Pacchetto.class)
                .setParameter("id", idPrenotazione)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Pacchetto> search(String query) {
        String pattern = "%" + query.toLowerCase() + "%";
        
        return em.createQuery(
            "SELECT p FROM Pacchetto p WHERE " +
            "LOWER(p.titolo) LIKE :ptrn OR " +
            "LOWER(p.descrizione) LIKE :ptrn OR " +
            "LOWER(p.tags) LIKE :ptrn", Pacchetto.class)
            .setParameter("ptrn", pattern)
            .getResultList();
    }

    public void save(Pacchetto p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            em.merge(p);
        }
    }
}
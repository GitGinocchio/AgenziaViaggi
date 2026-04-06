package com.agenziaviaggi.web.services;

import com.agenziaviaggi.web.entities.Pacchetto;
import com.agenziaviaggi.web.entities.Servizio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Stateless
public class ServizioService {
    @PersistenceContext
    private EntityManager em;

    public List<Servizio> findAll() {
        return em.createQuery("SELECT s FROM Servizio s", Servizio.class).getResultList();
    }

    public Servizio findById(Integer id) {
        return em.find(Servizio.class, id);
    }

    public List<Servizio> findByPacchetto(Integer idPacchetto) {
        return em.createQuery(
            "SELECT s FROM Servizio s WHERE s.pacchetto.id = :idp ORDER BY s.inizio ASC", 
            Servizio.class)
            .setParameter("idp", idPacchetto)
            .getResultList();
    }

    public void save(Servizio s) {
        // Se il client ha inviato l'ID del pacchetto tramite setIdPacchetto
        if (s.getPacchetto() != null && s.getPacchetto().getId() != null) {
            // Troviamo l'entità Pacchetto reale gestita dall'EntityManager
            Pacchetto p = em.find(Pacchetto.class, s.getPacchetto().getId());
            s.setPacchetto(p);
        }

        if (s.getId() == null) {
            em.persist(s);
        } else {
            em.merge(s);
        }
    }

    public void delete(Integer id) {
        Servizio s = em.find(Servizio.class, id);
        if (s != null) {
            em.remove(s);
        }
    }
}

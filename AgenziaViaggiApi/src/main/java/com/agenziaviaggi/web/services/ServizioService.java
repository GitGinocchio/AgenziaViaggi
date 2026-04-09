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
        if (s.getPacchetto() != null && s.getPacchetto().getId() != null) {
            Pacchetto p = em.find(Pacchetto.class, s.getPacchetto().getId());

            if (p != null) {
                s.setPacchetto(p);

                if (s.getId() == null) {
                    p.getServizi().add(s);
                    em.persist(s);
                } else {
                    Servizio managedServizio = em.merge(s);

                    p.getServizi().removeIf(existing -> existing.getId().equals(managedServizio.getId()));
                    p.getServizi().add(managedServizio);
                }
            }
        } else {
            if (s.getId() == null) {
                em.persist(s);
            } else {
                em.merge(s);
            }
        }

        em.flush();
    }

    public void delete(Integer id) {
        Servizio s = em.find(Servizio.class, id);
        if (s != null) {
            Pacchetto p = s.getPacchetto();

            if (p != null) {
                p.getServizi().remove(s);
            }

            em.remove(s);

            em.flush();
        }
    }
}

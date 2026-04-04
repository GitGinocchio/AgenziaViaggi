package com.agenziaviaggi.web.services;

import com.agenziaviaggi.web.entities.Cliente;
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
public class ClienteService {
    @PersistenceContext
    private EntityManager em;

    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }
    
    public Cliente findById(Integer id) {
        return em.find(Cliente.class, id);
    }

    public Cliente findByEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.email = :e", Cliente.class)
                     .setParameter("e", email)
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Cliente findByPrenotazione(Integer idPrenotazione) {
        try {
            return em.createQuery(
                "SELECT pr.cliente FROM Prenotazione pr WHERE pr.id = :id", Cliente.class)
                .setParameter("id", idPrenotazione)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Cliente c) {
        if (c.getId() == null) {
            em.persist(c);
        } else {
            em.merge(c);
        }
    }
    
    public void delete(Integer id) {
        Cliente c = em.find(Cliente.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
}

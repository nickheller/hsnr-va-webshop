package com.hsnr.webshop.core.dataaccess;

import com.hsnr.webshop.core.entities.Bestellung;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BestellungRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Bestellung bestellung) {
        em.persist(bestellung);
    }

    public Bestellung findById(Long id) {
        return em.find(Bestellung.class, id);
    }

    public List<Bestellung> findAll() {
        return em.createQuery("SELECT b FROM Bestellung b", Bestellung.class).getResultList();
    }

    public List<Bestellung> findByKundennummer(Long kundennummer) {
        return em.createQuery("SELECT b FROM Bestellung b WHERE b.kunde.kundennummer = :kid", Bestellung.class)
                .setParameter("kid", kundennummer)
                .getResultList();
    }
}
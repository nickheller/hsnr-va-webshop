package com.hsnr.webshop.core.dataaccess;

import com.hsnr.webshop.core.entities.Kunde;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class KundeRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Kunde kunde) {
        em.persist(kunde);
    }

    public Kunde findById(Long id) {
        return em.find(Kunde.class, id);
    }

    public void update(Kunde kunde) {
        em.merge(kunde);
    }

    public void delete(Kunde kunde) {
        if (!em.contains(kunde)) {
            kunde = em.merge(kunde);
        }
        em.remove(kunde);
    }

    public List<Kunde> findAll() {
        return em.createQuery("SELECT k FROM Kunde k", Kunde.class)
                 .getResultList();
    }

    public Kunde findByBenutzerkennung(String benutzerkennung) {
        try {
            return em.createQuery(
                    "SELECT k FROM Kunde k WHERE k.benutzer.benutzerkennung = :kennung",
                    Kunde.class)
                     .setParameter("kennung", benutzerkennung)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
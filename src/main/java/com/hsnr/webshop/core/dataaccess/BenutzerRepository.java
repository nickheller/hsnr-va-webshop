package com.hsnr.webshop.core.dataaccess;

import com.hsnr.webshop.core.entities.Benutzer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BenutzerRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Benutzer benutzer) {
        em.persist(benutzer);
    }

    public Benutzer findById(String benutzerkennung) {
        return em.find(Benutzer.class, benutzerkennung);
    }

    public Benutzer findByBenutzerkennung(String kennung) {
        return em.find(Benutzer.class, kennung);
    }

    public void update(Benutzer benutzer) {
        em.merge(benutzer);
    }

    public void delete(Benutzer benutzer) {
        if (!em.contains(benutzer)) {
            benutzer = em.merge(benutzer);
        }
        em.remove(benutzer);
    }

    public List<Benutzer> findAll() {
        return em.createQuery("SELECT b FROM Benutzer b", Benutzer.class).getResultList();
    }
}
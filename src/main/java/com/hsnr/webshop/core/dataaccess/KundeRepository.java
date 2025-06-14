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

    /**
     * Persistiert einen neuen Kunden.
     */
    public void save(Kunde kunde) {
        em.persist(kunde);
    }

    /**
     * Findet einen Kunden anhand seiner ID.
     */
    public Kunde findById(Long id) {
        return em.find(Kunde.class, id);
    }

    /**
     * Aktualisiert einen bestehenden Kunden.
     */
    public void update(Kunde kunde) {
        em.merge(kunde);
    }

    /**
     * Löscht einen Kunden.
     */
    public void delete(Kunde kunde) {
        if (!em.contains(kunde)) {
            kunde = em.merge(kunde);
        }
        em.remove(kunde);
    }

    /**
     * Liefert alle Kunden.
     */
    public List<Kunde> findAll() {
        return em.createQuery("SELECT k FROM Kunde k", Kunde.class)
                 .getResultList();
    }

    /**
     * Findet einen Kunden über seine Benutzerkennung.
     * Gibt null zurück, wenn kein Ergebnis gefunden wurde.
     */
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
package com.hsnr.webshop.core.dataaccess;

import com.hsnr.webshop.core.entities.Produkt;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProduktRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Produkt produkt) {
        em.persist(produkt);
    }

    public Produkt findById(Long id) {
        return em.find(Produkt.class, id);
    }

    public void update(Produkt produkt) {
        em.merge(produkt);
    }

    public void delete(Produkt produkt) {
        if (!em.contains(produkt)) {
            produkt = em.merge(produkt);
        }
        em.remove(produkt);
    }

    public List<Produkt> findAll() {
        return em.createQuery("SELECT p FROM Produkt p", Produkt.class).getResultList();
    }

    public List<Produkt> findAvailable() {
        return em.createQuery("SELECT p FROM Produkt p WHERE p.bestand > 0", Produkt.class)
                 .getResultList();
    }

    public List<Produkt> findByNameOrKategorie(String query) {
        return em.createQuery(
                "SELECT p FROM Produkt p WHERE LOWER(p.name) LIKE :q OR LOWER(p.kategorie) LIKE :q",
                Produkt.class)
                .setParameter("q", "%" + query.toLowerCase() + "%")
                .getResultList();
    }
}
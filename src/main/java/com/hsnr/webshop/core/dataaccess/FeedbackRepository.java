package com.hsnr.webshop.core.dataaccess;

import com.hsnr.webshop.core.entities.Feedback;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FeedbackRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Feedback feedback) {
        em.persist(feedback);
    }

    public Feedback findById(Long id) {
        return em.find(Feedback.class, id);
    }

    public List<Feedback> findAll() {
        return em.createQuery("SELECT f FROM Feedback f", Feedback.class).getResultList();
    }

    public List<Feedback> findByBestellnummer(Long bestellnummer) {
        return em.createQuery("SELECT f FROM Feedback f WHERE f.bestellung.bestellnummer = :bid", Feedback.class)
                .setParameter("bid", bestellnummer)
                .getResultList();
    }
}
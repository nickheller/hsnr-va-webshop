package com.hsnr.webshop.core.usecases;

import com.hsnr.webshop.core.entities.Bestellung;
import com.hsnr.webshop.core.entities.Feedback;
import com.hsnr.webshop.core.dataaccess.BestellungRepository;
import com.hsnr.webshop.core.dataaccess.FeedbackRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class FeedbackService {

    @Inject
    private FeedbackRepository feedbackRepo;

    @Inject
    private BestellungRepository bestellungRepo;

    public void feedbackAbgeben(Long bestellnummer, String text) {
        Bestellung bestellung = bestellungRepo.findById(bestellnummer);
        if (bestellung == null) {
            throw new IllegalArgumentException("Bestellung nicht gefunden");
        }

        if (!"versendet".equalsIgnoreCase(bestellung.getStatus())) {
            throw new IllegalStateException("Feedback nur für versendete Bestellungen erlaubt");
        }

        Feedback feedback = new Feedback();
        feedback.setBestellung(bestellung);
        feedback.setText(text);

        feedbackRepo.save(feedback);
    }

    public List<Feedback> feedbackZuBestellung(Long bestellnummer) {
        return feedbackRepo.findByBestellnummer(bestellnummer);
    }

    public List<Feedback> alleFeedbacks() {
        return feedbackRepo.findAll();
    }
}
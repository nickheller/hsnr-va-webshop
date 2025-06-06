package com.hsnr.webshop.core.usecases;

import com.hsnr.webshop.core.entities.Produkt;
import com.hsnr.webshop.core.dataaccess.ProduktRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class ProduktVerwaltungService {

    @Inject
    private ProduktRepository produktRepo;

    public void produktAnlegen(Produkt produkt) {
        produktRepo.save(produkt);
    }

    public void produktBearbeiten(Produkt produkt) {
        produktRepo.update(produkt);
    }

    public void produktLoeschen(Long id) throws IllegalStateException {
        Produkt produkt = produktRepo.findById(id);
        if (produkt == null) {
            throw new IllegalStateException("Produkt nicht gefunden.");
        }
        if (produkt.getBestand() <= 0) {
            produktRepo.delete(produkt);
        } else {
            throw new IllegalStateException("Produkt kann nicht gelöscht werden, solange Bestand vorhanden ist.");
        }
    }

    public List<Produkt> alleProdukte() {
        return produktRepo.findAll();
    }

    public List<Produkt> verfuegbareProdukte() {
        return produktRepo.findAvailable();
    }

    public List<Produkt> sucheProdukte(String suchbegriff) {
        return produktRepo.findByNameOrKategorie(suchbegriff);
    }
}
package com.hsnr.webshop.core.usecases;

import com.hsnr.webshop.core.entities.Kunde;
import com.hsnr.webshop.core.dataaccess.KundeRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class KundenVerwaltungService {

    @Inject
    private KundeRepository kundeRepo;

    public void kundeAnlegen(Kunde kunde) {
        kundeRepo.save(kunde);
    }

    public void kundeAktualisieren(Kunde kunde) {
        kundeRepo.update(kunde);
    }

    public Kunde findeKundeNachId(Long id) {
        return kundeRepo.findById(id);
    }

    public Kunde findeKundeNachBenutzerkennung(String benutzerkennung) {
        return kundeRepo.findByBenutzerkennung(benutzerkennung);
    }

    public List<Kunde> alleKunden() {
        return kundeRepo.findAll();
    }
    
}
package com.hsnr.webshop.core.usecases;


import com.hsnr.webshop.core.entities.Benutzer;
import com.hsnr.webshop.core.dataaccess.BenutzerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class BenutzerVerwaltungService {

    @Inject
    private BenutzerRepository benutzerRepo;

    public void benutzerAnlegen(Benutzer benutzer) {
        benutzerRepo.save(benutzer);
    }

    public Benutzer findeBenutzer(String benutzerkennung) {
        return benutzerRepo.findByBenutzerkennung(benutzerkennung);
    }

    public void benutzerAktualisieren(Benutzer benutzer) {
        benutzerRepo.update(benutzer);
    }

    public void benutzerLoeschen(Benutzer benutzer) {
        benutzerRepo.delete(benutzer);
    }

    // Optional: Benutzer nach Rolle abrufen (z. B. für Admin-UI)
    public List<Benutzer> findeBenutzerNachRolle(String rolle) {
        // Wenn du diese Query brauchst, musst du sie im Repository ergänzen.
        // Beispiel: SELECT b FROM Benutzer b WHERE b.rolle = :rolle
        return null;
    }
}
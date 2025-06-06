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

    public List<Benutzer> findeBenutzerNachRolle(String rolle) {
        return null;
    }
}
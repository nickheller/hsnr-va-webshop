package com.hsnr.webshop.core.usecases;

import com.hsnr.webshop.core.entities.Benutzer;
import com.hsnr.webshop.core.entities.Kunde;
import com.hsnr.webshop.core.dataaccess.BenutzerRepository;
import com.hsnr.webshop.core.dataaccess.KundeRepository;
import com.hsnr.webshop.core.facade.dto.BenutzerDTO;
import com.hsnr.webshop.core.facade.dto.NeuerBenutzerDTO;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@Stateless
public class BenutzerVerwaltungService {

    @Inject
    private BenutzerRepository benutzerRepo;

    @Inject
    private KundeRepository kundeRepo;

    public void benutzerAnlegen(Benutzer benutzer) {
        benutzerRepo.save(benutzer);

        if ("kunde".equalsIgnoreCase(benutzer.getRolle())) {
            Kunde kunde = new Kunde();
            kunde.setBenutzer(benutzer);
            kundeRepo.save(kunde);
        }
    }

    public void benutzerAnlegen(NeuerBenutzerDTO dto) {
        Benutzer benutzer = new Benutzer();
        benutzer.setBenutzerkennung(dto.getBenutzerkennung());
        benutzer.setPasswort(dto.getPasswort());
        benutzer.setName(dto.getName());
        benutzer.setTelefonnummer(dto.getTelefonnummer());
        benutzer.setRolle(dto.getRolle());

        benutzerRepo.save(benutzer);

        if ("kunde".equalsIgnoreCase(benutzer.getRolle())) {
            Kunde kunde = new Kunde();
            kunde.setBenutzer(benutzer);
            kundeRepo.save(kunde);
        }
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

    public void benutzerLoeschen(String benutzerkennung) {
        Benutzer benutzer = benutzerRepo.findByBenutzerkennung(benutzerkennung);
        if (benutzer == null) {
            throw new NotFoundException("Benutzer mit Kennung '" + benutzerkennung + "' nicht gefunden");
        }
        benutzerRepo.delete(benutzer);
    }

    public List<Benutzer> findeBenutzerNachRolle(String rolle) {
        return null; // noch nicht implementiert
    }

    public BenutzerDTO findeAngemeldetenBenutzer(String benutzerkennung) {
        Benutzer benutzer = benutzerRepo.findByBenutzerkennung(benutzerkennung);
        if (benutzer == null) {
            throw new NotFoundException("Benutzer nicht gefunden");
        }

        return new BenutzerDTO(
                benutzer.getBenutzerkennung(),
                benutzer.getRolle(),
                benutzer.getName(),
                benutzer.getTelefonnummer()
        );
    }

    public List<Benutzer> alleBenutzer() {
        return benutzerRepo.findAll();
    }
}
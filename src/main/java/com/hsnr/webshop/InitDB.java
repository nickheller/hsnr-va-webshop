package com.hsnr.webshop;

import com.hsnr.webshop.core.dataaccess.BenutzerRepository;
import com.hsnr.webshop.core.dataaccess.KundeRepository;
import com.hsnr.webshop.core.entities.Benutzer;
import com.hsnr.webshop.core.entities.Kunde;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.time.LocalDate;

@Startup
@Singleton
public class InitDB {

    @Inject
    private BenutzerRepository benutzerRepo;

    @Inject
    private KundeRepository kundeRepo;

    @PostConstruct
    public void init() {
        if (benutzerRepo.findByBenutzerkennung("admin") == null) {
            Benutzer admin = new Benutzer();
            admin.setBenutzerkennung("admin");
            admin.setPasswort("geheim");
            admin.setName("Admin User");
            admin.setTelefonnummer("123456789");
            admin.setRolle("admin");

            benutzerRepo.save(admin);
            System.out.println("[InitDB] Standard-Admin angelegt");
        }

        if (benutzerRepo.findByBenutzerkennung("mitarbeiter") == null) {
            Benutzer mitarbeiter = new Benutzer();
            mitarbeiter.setBenutzerkennung("mitarbeiter");
            mitarbeiter.setPasswort("passwort123");
            mitarbeiter.setName("Mitarbeiter Mustermann");
            mitarbeiter.setTelefonnummer("987654321");
            mitarbeiter.setRolle("mitarbeiter");

            benutzerRepo.save(mitarbeiter);
            System.out.println("[InitDB] Standard-Mitarbeiter angelegt");
        }

        if (benutzerRepo.findByBenutzerkennung("kunde") == null) {
            Benutzer kundeBenutzer = new Benutzer();
            kundeBenutzer.setBenutzerkennung("kunde");
            kundeBenutzer.setPasswort("kunde123");
            kundeBenutzer.setName("Kunde Beispiel");
            kundeBenutzer.setTelefonnummer("555123456");
            kundeBenutzer.setRolle("kunde");

            benutzerRepo.save(kundeBenutzer);

            // Neuen Kunden anlegen und mit Benutzer verknüpfen
            Kunde kunde = new Kunde();
            kunde.setAdresse("Musterstraße 1");
            kunde.setEmail("kunde@example.com");
            kunde.setGeburtsdatum(LocalDate.of(1990, 1, 1));
            kunde.setTelefonnummer("555123456");
            kunde.setZahlungsmethode("Rechnung");
            kunde.setBenutzer(kundeBenutzer);

            kundeRepo.save(kunde);

            System.out.println("[InitDB] Standard-Kunde samt Kundendaten angelegt");
        }
    }
}
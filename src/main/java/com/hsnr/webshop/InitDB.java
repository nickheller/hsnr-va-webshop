package com.hsnr.webshop;

import com.hsnr.webshop.core.dataaccess.BenutzerRepository;
import com.hsnr.webshop.core.entities.Benutzer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Startup
@Singleton
public class InitDB {

    @Inject
    private BenutzerRepository benutzerRepo;

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
            Benutzer kunde = new Benutzer();
            kunde.setBenutzerkennung("kunde");
            kunde.setPasswort("kunde123");
            kunde.setName("Kunde Beispiel");
            kunde.setTelefonnummer("555123456");
            kunde.setRolle("kunde");

            benutzerRepo.save(kunde);
            System.out.println("[InitDB] Standard-Kunde angelegt");
        }
    }
}
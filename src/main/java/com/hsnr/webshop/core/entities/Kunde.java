package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Kunde implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kundennummer;
    private String adresse;
    private String telefonnummer;
    private String email;
    private LocalDate geburtsdatum;
    private String zahlungsmethode;

    @OneToOne(optional = false)
    @JoinColumn(name = "benutzerkennung", referencedColumnName = "benutzerkennung", unique = true)
    private Benutzer benutzer;

    public Kunde() {}

    public Long getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(Long kundennummer) {
        this.kundennummer = kundennummer;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getZahlungsmethode() {
        return zahlungsmethode;
    }

    public void setZahlungsmethode(String zahlungsmethode) {
        this.zahlungsmethode = zahlungsmethode;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
}
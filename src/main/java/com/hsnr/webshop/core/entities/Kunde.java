package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Kunde implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kundennummer;

    private String name;
    private String adresse;
    private String telefonnummer;
    private String email;
    private LocalDate geburtsdatum;
    private String zahlungsmethode;

    // Standard-Konstruktor
    public Kunde() {}

    // Getter & Setter
    public Long getKundennummer() { return kundennummer; }
    public void setKundennummer(Long kundennummer) { this.kundennummer = kundennummer; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getGeburtsdatum() { return geburtsdatum; }
    public void setGeburtsdatum(LocalDate geburtsdatum) { this.geburtsdatum = geburtsdatum; }

    public String getZahlungsmethode() { return zahlungsmethode; }
    public void setZahlungsmethode(String zahlungsmethode) { this.zahlungsmethode = zahlungsmethode; }
}
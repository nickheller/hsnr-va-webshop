package com.hsnr.webshop.core.facade.dto;

import java.time.LocalDate;

public class KundeDTO {
    public String benutzerkennung; // nur für POST relevant
    public String adresse;
    public String telefonnummer;
    public String email;
    public LocalDate geburtsdatum;
    public String zahlungsmethode;
    public String vorname;  // aus Benutzer-Name extrahiert
    public String nachname; // aus Benutzer-Name extrahiert

    // Leerer Konstruktor für JSON-Binding und JSF-Kompatibilität
    public KundeDTO() {}

    // Vollständiger Konstruktor für REST-Responses
    public KundeDTO(String adresse, String telefonnummer, String email,
                    LocalDate geburtsdatum, String zahlungsmethode,
                    String benutzerkennung, String vorname, String nachname) {
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.zahlungsmethode = zahlungsmethode;
        this.benutzerkennung = benutzerkennung;
        this.vorname = vorname;
        this.nachname = nachname;
    }
}
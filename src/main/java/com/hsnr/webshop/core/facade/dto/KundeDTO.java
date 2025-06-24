package com.hsnr.webshop.core.facade.dto;

import java.time.LocalDate;

public class KundeDTO {
    public String benutzerkennung;
    public String adresse;
    public String telefonnummer;
    public String email;
    public LocalDate geburtsdatum;
    public String zahlungsmethode;
    public String vorname;
    public String nachname;

    public KundeDTO() {}

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
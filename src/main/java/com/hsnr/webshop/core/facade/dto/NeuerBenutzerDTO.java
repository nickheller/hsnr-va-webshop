package com.hsnr.webshop.core.facade.dto;

public class NeuerBenutzerDTO {

    private String benutzerkennung;
    private String passwort;
    private String name;
    private String telefonnummer;
    private String rolle;

    public String getBenutzerkennung() { return benutzerkennung; }
    public void setBenutzerkennung(String benutzerkennung) { this.benutzerkennung = benutzerkennung; }

    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }

    public String getRolle() { return rolle; }
    public void setRolle(String rolle) { this.rolle = rolle; }
}
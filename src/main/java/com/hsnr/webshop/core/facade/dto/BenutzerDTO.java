package com.hsnr.webshop.core.facade.dto;

public class BenutzerDTO {
    private String benutzerkennung;
    private String rolle;
    private String name;
    private String telefonnummer;

    public BenutzerDTO(String benutzerkennung, String rolle, String name, String telefonnummer) {
        this.benutzerkennung = benutzerkennung;
        this.rolle = rolle;
        this.name = name;
        this.telefonnummer = telefonnummer;
    }

    public String getBenutzerkennung() { return benutzerkennung; }
    public String getRolle() { return rolle; }
    public String getName() { return name; }
    public String getTelefonnummer() { return telefonnummer; }
}
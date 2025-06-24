package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Produkt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produktnummer;
    private String name;
    private String beschreibung;
    private double preis;
    private String kategorie;
    private int bestand;
    private String lieferzeit;


    public Produkt() {
    }

    public Produkt(String name, String beschreibung, double preis, String kategorie, int bestand, String lieferzeit) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.kategorie = kategorie;
        this.bestand = bestand;
        this.lieferzeit = lieferzeit;
    }


    public Long getProduktnummer() {
        return produktnummer;
    }

    public void setProduktnummer(Long produktnummer) {
        this.produktnummer = produktnummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public int getBestand() {
        return bestand;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public String getLieferzeit() {
        return lieferzeit;
    }

    public void setLieferzeit(String lieferzeit) {
        this.lieferzeit = lieferzeit;
    }
}
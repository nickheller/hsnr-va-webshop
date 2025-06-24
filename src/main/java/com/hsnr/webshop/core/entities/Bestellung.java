package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Bestellung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bestellnummer;
    private LocalDate bestelldatum;
    private String status;
    private String lieferadresse;
    private String zahlungsmethode;
    private double gesamtpreis;

    @ManyToOne
    private Kunde kunde;

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL)
    private List<Bestellposition> positionen;

    public Bestellung() {}

    public Long getBestellnummer() { return bestellnummer; }
    public void setBestellnummer(Long bestellnummer) { this.bestellnummer = bestellnummer; }

    public LocalDate getBestelldatum() { return bestelldatum; }
    public void setBestelldatum(LocalDate bestelldatum) { this.bestelldatum = bestelldatum; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLieferadresse() { return lieferadresse; }
    public void setLieferadresse(String lieferadresse) { this.lieferadresse = lieferadresse; }

    public String getZahlungsmethode() { return zahlungsmethode; }
    public void setZahlungsmethode(String zahlungsmethode) { this.zahlungsmethode = zahlungsmethode; }

    public double getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(double gesamtpreis) { this.gesamtpreis = gesamtpreis; }

    public Kunde getKunde() { return kunde; }
    public void setKunde(Kunde kunde) { this.kunde = kunde; }

    public List<Bestellposition> getPositionen() { return positionen; }
    public void setPositionen(List<Bestellposition> positionen) { this.positionen = positionen; }
}
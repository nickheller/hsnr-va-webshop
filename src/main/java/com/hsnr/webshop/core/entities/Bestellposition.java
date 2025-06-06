package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Bestellposition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int menge;
    private double einzelpreis;

    @ManyToOne
    private Produkt produkt;

    @ManyToOne
    private Bestellung bestellung;

    public Bestellposition() {}

    // Getter & Setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getMenge() { return menge; }
    public void setMenge(int menge) { this.menge = menge; }

    public double getEinzelpreis() { return einzelpreis; }
    public void setEinzelpreis(double einzelpreis) { this.einzelpreis = einzelpreis; }

    public Produkt getProdukt() { return produkt; }
    public void setProdukt(Produkt produkt) { this.produkt = produkt; }

    public Bestellung getBestellung() { return bestellung; }
    public void setBestellung(Bestellung bestellung) { this.bestellung = bestellung; }
}
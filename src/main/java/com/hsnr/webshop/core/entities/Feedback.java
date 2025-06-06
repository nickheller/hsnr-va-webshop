package com.hsnr.webshop.core.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nummer;

    @ManyToOne
    private Bestellung bestellung;

    private String text;

    public Feedback() {}

    // Getter & Setter
    public Long getNummer() { return nummer; }
    public void setNummer(Long nummer) { this.nummer = nummer; }

    public Bestellung getBestellung() { return bestellung; }
    public void setBestellung(Bestellung bestellung) { this.bestellung = bestellung; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
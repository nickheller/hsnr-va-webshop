package com.hsnr.webshop.core.usecases;

import com.hsnr.webshop.core.dataaccess.BestellungRepository;
import com.hsnr.webshop.core.dataaccess.KundeRepository;
import com.hsnr.webshop.core.dataaccess.ProduktRepository;
import com.hsnr.webshop.core.entities.Kunde;
import com.hsnr.webshop.core.entities.Bestellposition;
import com.hsnr.webshop.core.entities.Produkt;
import com.hsnr.webshop.core.entities.Bestellung;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BestellService {

    @Inject
    private BestellungRepository bestellRepo;

    @Inject
    private KundeRepository kundeRepo;

    @Inject
    private ProduktRepository produktRepo;

    public void bestellungAnlegen(Long kundennummer, List<Bestellposition> positionen, String lieferadresse, String zahlungsmethode) {
        Kunde kunde = kundeRepo.findById(kundennummer);
        if (kunde == null) {
            throw new IllegalArgumentException("Kunde nicht gefunden");
        }

        Bestellung bestellung = new Bestellung();
        bestellung.setKunde(kunde);
        bestellung.setBestelldatum(LocalDate.now());
        bestellung.setStatus("in Bearbeitung");
        bestellung.setLieferadresse(lieferadresse);
        bestellung.setZahlungsmethode(zahlungsmethode);

        double gesamtpreis = 0;
        List<Bestellposition> persistiertePositionen = new ArrayList<>();

        for (Bestellposition pos : positionen) {
            if (pos.getProdukt() == null || pos.getProdukt().getProduktnummer() == null) {
                throw new IllegalArgumentException("Produkt oder Produktnummer fehlt in der Bestellposition");
            }

            Produkt produkt = produktRepo.findById(pos.getProdukt().getProduktnummer());
            if (produkt == null) {
                throw new IllegalArgumentException("Produkt mit Nummer " + pos.getProdukt().getProduktnummer() + " nicht gefunden");
            }

            if (produkt.getBestand() < pos.getMenge()) {
                throw new IllegalStateException("Nicht genügend Bestand für Produkt: " + produkt.getName());
            }

            produkt.setBestand(produkt.getBestand() - pos.getMenge());
            produktRepo.update(produkt);

            pos.setEinzelpreis(produkt.getPreis());
            pos.setBestellung(bestellung);
            pos.setProdukt(produkt);

            persistiertePositionen.add(pos);
            gesamtpreis += produkt.getPreis() * pos.getMenge();
        }

        bestellung.setPositionen(persistiertePositionen);
        bestellung.setGesamtpreis(gesamtpreis);

        bestellRepo.save(bestellung);
    }

    public List<Bestellung> bestellungenFuerKunde(Long kundennummer) {
        return bestellRepo.findByKundennummer(kundennummer);
    }

    public void bestellungStornieren(Long bestellnummer) {
        Bestellung bestellung = bestellRepo.findById(bestellnummer);
        if (bestellung == null) {
            throw new IllegalArgumentException("Bestellung nicht gefunden");
        }

        bestellung.setStatus("storniert");
        bestellRepo.save(bestellung);

        for (Bestellposition pos : bestellung.getPositionen()) {
            Produkt p = pos.getProdukt();
            p.setBestand(p.getBestand() + pos.getMenge());
            produktRepo.update(p);
        }
    }
    public void bestellungVersenden(Long bestellnummer) {
    Bestellung bestellung = bestellRepo.findById(bestellnummer);
    if (bestellung == null) {
        throw new IllegalArgumentException("Bestellung nicht gefunden");
    }
    if (!"in Bearbeitung".equalsIgnoreCase(bestellung.getStatus())) {
        throw new IllegalStateException("Nur Bestellungen im Status 'in Bearbeitung' können versendet werden");
    }
    bestellung.setStatus("versendet");
    bestellRepo.save(bestellung);
}
}
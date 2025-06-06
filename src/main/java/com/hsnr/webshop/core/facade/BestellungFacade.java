package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.entities.Bestellposition;
import com.hsnr.webshop.core.entities.Bestellung;
import com.hsnr.webshop.core.entities.Produkt;
import com.hsnr.webshop.core.facade.dto.BestellpositionDTO;
import com.hsnr.webshop.core.facade.dto.BestellungsRequestDTO;
import com.hsnr.webshop.core.facade.dto.BestellungDTO;
import com.hsnr.webshop.core.usecases.BestellService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/bestellungen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BestellungFacade {

    @Inject
    private BestellService service;

    @POST
    @RolesAllowed({"kunde", "mitarbeiter", "admin"})
    public void bestellungAufgeben(BestellungsRequestDTO dto) {
        // DTO → Entity-Konvertierung
        List<Bestellposition> positionen = new ArrayList<>();
        for (BestellpositionDTO dtoPos : dto.positionen) {
            Bestellposition pos = new Bestellposition();

            Produkt produkt = new Produkt();
            produkt.setProduktnummer(dtoPos.produktnummer); // Nur die ID reicht für die Verknüpfung
            pos.setProdukt(produkt);

            pos.setMenge(dtoPos.menge);
            positionen.add(pos);
        }

        service.bestellungAnlegen(
                dto.kundennummer,
                positionen,
                dto.lieferadresse,
                dto.zahlungsmethode
        );
    }

    @GET
    @Path("/kunde/{kundennummer}")
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public List<BestellungDTO> getBestellungen(@PathParam("kundennummer") Long kundennummer) {
        List<Bestellung> bestellungen = service.bestellungenFuerKunde(kundennummer);
        List<BestellungDTO> dtos = new ArrayList<>();

        for (Bestellung b : bestellungen) {
            BestellungDTO dto = new BestellungDTO();
            dto.bestellnummer = b.getBestellnummer();
            dto.bestelldatum = b.getBestelldatum();
            dto.status = b.getStatus();
            dto.lieferadresse = b.getLieferadresse();
            dto.zahlungsmethode = b.getZahlungsmethode();
            dto.gesamtpreis = b.getGesamtpreis();

            List<BestellpositionDTO> positionen = new ArrayList<>();
            for (Bestellposition pos : b.getPositionen()) {
                BestellpositionDTO posDto = new BestellpositionDTO();
                posDto.produktnummer = pos.getProdukt().getProduktnummer();
                posDto.produktname = pos.getProdukt().getName();
                posDto.menge = pos.getMenge();
                posDto.einzelpreis = pos.getEinzelpreis();
                positionen.add(posDto);
            }

            dto.positionen = positionen;
            dtos.add(dto);
        }

        return dtos;
    }

    @PUT
    @Path("/stornieren/{bestellnummer}")
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public void storniere(@PathParam("bestellnummer") Long bestellnummer) {
        service.bestellungStornieren(bestellnummer);
    }
    
        /**
     * Mitarbeiter markiert eine Bestellung als versendet.
     */
    @PUT
    @Path("/versenden/{bestellnummer}")
    @RolesAllowed({"admin", "mitarbeiter"})
    public void versendeBestellung(@PathParam("bestellnummer") Long bestellnummer) {
        service.bestellungVersenden(bestellnummer);
    }
}
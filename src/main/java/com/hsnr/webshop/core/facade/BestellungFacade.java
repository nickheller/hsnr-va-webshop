package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.dataaccess.KundeRepository;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

@Path("/bestellungen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BestellungFacade {

    @Inject
    private BestellService service;
    
    @Inject
    private KundeRepository kundeRepo;

    @Context
    private SecurityContext secCtx;

    @POST
    @RolesAllowed({"kunde", "mitarbeiter", "admin"})
    public void bestellungAufgeben(BestellungsRequestDTO dto) {
        List<Bestellposition> positionen = new ArrayList<>();
        for (BestellpositionDTO dtoPos : dto.positionen) {
            Bestellposition pos = new Bestellposition();
            Produkt produkt = new Produkt();
            produkt.setProduktnummer(dtoPos.produktnummer);
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
    
    @PUT
    @Path("/versenden/{bestellnummer}")
    @RolesAllowed({"admin", "mitarbeiter"})
    public void versendeBestellung(@PathParam("bestellnummer") Long bestellnummer) {
        service.bestellungVersenden(bestellnummer);
    }
    
    @POST
    @Path("/me")
    @RolesAllowed("kunde")
    public void bestellungFuerMichAufgeben(BestellungsRequestDTO dto) {
        String kennung = secCtx.getUserPrincipal().getName();
        var kunde = kundeRepo.findByBenutzerkennung(kennung);
        if (kunde == null) {
            throw new WebApplicationException("Kunde nicht gefunden", 400);
        }

        List<Bestellposition> positionen = dto.positionen.stream().map(dtoPos -> {
            Bestellposition pos = new Bestellposition();
            Produkt p = new Produkt();
            p.setProduktnummer(dtoPos.produktnummer);
            pos.setProdukt(p);
            pos.setMenge(dtoPos.menge);
            return pos;
        }).toList();

        service.bestellungAnlegen(
            kunde.getKundennummer(),
            positionen,
            dto.lieferadresse,
            dto.zahlungsmethode
        );
    }
    
    @GET
    @Path("/me")
    @RolesAllowed("kunde")
    public List<BestellungDTO> getMeineBestellungen() {
        // Benutzerkennung aus SecurityContext
        String benutzerkennung = secCtx.getUserPrincipal().getName();
        var kunde = kundeRepo.findByBenutzerkennung(benutzerkennung);
        if (kunde == null) {
            throw new WebApplicationException("Kunde nicht gefunden", 400);
        }
        // Wiederverwende die Methode für kundenspezifische Abfrage
        return getBestellungen(kunde.getKundennummer());
    }
}
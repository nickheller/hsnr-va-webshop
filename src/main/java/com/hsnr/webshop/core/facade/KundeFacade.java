package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.entities.Benutzer;
import com.hsnr.webshop.core.entities.Kunde;
import com.hsnr.webshop.core.facade.dto.KundeDTO;
import com.hsnr.webshop.core.usecases.KundenVerwaltungService;
import com.hsnr.webshop.core.dataaccess.BenutzerRepository;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeFacade {

    @Inject
    private KundenVerwaltungService service;

    @Inject
    private BenutzerRepository benutzerRepo;

    @Context
    private SecurityContext securityContext;

    @POST
    @RolesAllowed("admin")
    public void addKunde(KundeDTO dto) {
        Benutzer benutzer = benutzerRepo.findById(dto.benutzerkennung);
        if (benutzer == null) {
            throw new WebApplicationException("Benutzer nicht gefunden", 404);
        }

        Kunde kunde = new Kunde();
        kunde.setAdresse(dto.adresse);
        kunde.setTelefonnummer(dto.telefonnummer);
        kunde.setEmail(dto.email);
        kunde.setGeburtsdatum(dto.geburtsdatum);
        kunde.setZahlungsmethode(dto.zahlungsmethode);
        kunde.setBenutzer(benutzer);

        service.kundeAnlegen(kunde);
    }

    @PUT
    @RolesAllowed("kunde")
    public void updateKunde(KundeDTO dto) {
        String benutzerkennung = securityContext.getUserPrincipal().getName();
        Kunde kunde = service.findeKundeNachBenutzerkennung(benutzerkennung);

        if (kunde == null) {
            throw new WebApplicationException("Kunde nicht gefunden", 404);
        }

        kunde.setAdresse(dto.adresse);
        kunde.setTelefonnummer(dto.telefonnummer);
        kunde.setEmail(dto.email);
        kunde.setGeburtsdatum(dto.geburtsdatum);
        kunde.setZahlungsmethode(dto.zahlungsmethode);

        if (dto.vorname != null && dto.nachname != null) {
            kunde.getBenutzer().setName(dto.vorname + " " + dto.nachname);
        }

        service.kundeAktualisieren(kunde);
    }

    @GET
    @RolesAllowed("admin")
    public List<Kunde> alleKunden() {
        return service.alleKunden();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}")
    public Kunde findeKunde(@PathParam("id") Long id) {
        return service.findeKundeNachId(id);
    }

    @GET
    @Path("/me")
    @RolesAllowed("kunde")
    public Kunde findeEigeneKundendaten() {
        String benutzerkennung = securityContext.getUserPrincipal().getName();
        Kunde kunde = service.findeKundeNachBenutzerkennung(benutzerkennung);

        if (kunde == null) {
            throw new WebApplicationException("Kunde nicht gefunden", 404);
        }

        return kunde;
    }

    @GET
    @Path("/meinprofil")
    @RolesAllowed("kunde")
    public KundeDTO meinProfil() {
        String benutzerkennung = securityContext.getUserPrincipal().getName();
        Kunde kunde = service.findeKundeNachBenutzerkennung(benutzerkennung);

        if (kunde == null) {
            throw new WebApplicationException("Kein Kunde für diesen Benutzer gefunden", 404);
        }

        String name = kunde.getBenutzer().getName();
        String vorname = "", nachname = "";
        if (name != null) {
            String[] parts = name.split(" ", 2);
            vorname = parts.length > 0 ? parts[0] : "";
            nachname = parts.length > 1 ? parts[1] : "";
        }

        KundeDTO dto = new KundeDTO();
        dto.adresse = kunde.getAdresse();
        dto.telefonnummer = kunde.getTelefonnummer();
        dto.email = kunde.getEmail();
        dto.geburtsdatum = kunde.getGeburtsdatum();
        dto.zahlungsmethode = kunde.getZahlungsmethode();
        dto.benutzerkennung = benutzerkennung;
        dto.vorname = vorname;
        dto.nachname = nachname;

        return dto;
    }
}
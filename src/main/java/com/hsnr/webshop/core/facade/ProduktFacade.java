package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.entities.Produkt;
import com.hsnr.webshop.core.usecases.ProduktVerwaltungService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/produkte")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduktFacade {

    @Inject
    private ProduktVerwaltungService service;

    @POST
    @RolesAllowed({"admin", "mitarbeiter"})
    public void addProdukt(Produkt produkt) {
        service.produktAnlegen(produkt);
    }

    @PUT
    @RolesAllowed({"admin", "mitarbeiter"})
    public void updateProdukt(Produkt produkt) {
        service.produktBearbeiten(produkt);
    }

    @DELETE
    @Path("/{id}")
     @RolesAllowed({"admin", "mitarbeiter"})
    public void deleteProdukt(@PathParam("id") Long id) {
        service.produktLoeschen(id);
    }

    @GET
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public List<Produkt> alleProdukte() {
        return service.alleProdukte();
    }

    @GET
    @Path("/verfuegbar")
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public List<Produkt> verfuegbareProdukte() {
        return service.verfuegbareProdukte();
    }

    @GET
    @Path("/suche/{query}")
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public List<Produkt> suche(@PathParam("query") String query) {
        return service.sucheProdukte(query);
    }
}
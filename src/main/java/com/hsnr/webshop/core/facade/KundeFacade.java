package com.hsnr.webshop.core.facade;


import com.hsnr.webshop.core.entities.Kunde;
import com.hsnr.webshop.core.usecases.KundenVerwaltungService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeFacade {

    @Inject
    private KundenVerwaltungService service;

    @POST
    @RolesAllowed("admin")
    public void addKunde(Kunde kunde) {
        service.kundeAnlegen(kunde);
    }

    @PUT
    @RolesAllowed("kunde")
    public void updateKunde(Kunde kunde) {
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
}
package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.facade.dto.BenutzerDTO;
import com.hsnr.webshop.core.entities.Benutzer;
import com.hsnr.webshop.core.facade.dto.NeuerBenutzerDTO;
import com.hsnr.webshop.core.usecases.BenutzerVerwaltungService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/benutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BenutzerFacade {

    @Inject
    private BenutzerVerwaltungService service;

    @GET
    @Path("/me")
    @RolesAllowed({"admin", "mitarbeiter", "kunde"})
    public BenutzerDTO aktuellerBenutzer(@Context SecurityContext securityContext) {
        String benutzerkennung = securityContext.getUserPrincipal().getName();
        return service.findeAngemeldetenBenutzer(benutzerkennung);
    }

    @GET
    @RolesAllowed("admin")
    public List<BenutzerDTO> alleBenutzer() {
        List<Benutzer> benutzerListe = service.alleBenutzer();
        return benutzerListe.stream()
                .map(b -> new BenutzerDTO(
                        b.getBenutzerkennung(),
                        b.getRolle(),
                        b.getName(),
                        b.getTelefonnummer()))
                .collect(Collectors.toList());
    }
    
    @POST
    @RolesAllowed("admin")
    public Response benutzerAnlegen(NeuerBenutzerDTO neuerBenutzer) {
        service.benutzerAnlegen(neuerBenutzer);
        return Response.status(Response.Status.CREATED).build();
    }
    
    @DELETE
    @Path("/{kennung}")
    @RolesAllowed("admin")
    public void benutzerLoeschen(@PathParam("kennung") String kennung) {
    service.benutzerLoeschen(kennung);
    }
    
    @PUT
    @Path("/{kennung}")
    @RolesAllowed("admin")
    public void aktualisiereBenutzer(@PathParam("kennung") String kennung, NeuerBenutzerDTO dto) {
        Benutzer bestehend = service.findeBenutzer(kennung);
        if (bestehend == null) {
            throw new NotFoundException("Benutzer nicht gefunden");
        }

        bestehend.setName(dto.getName());
        bestehend.setTelefonnummer(dto.getTelefonnummer());
        bestehend.setRolle(dto.getRolle());
        bestehend.setPasswort(dto.getPasswort());

        service.benutzerAktualisieren(bestehend);
    }
}
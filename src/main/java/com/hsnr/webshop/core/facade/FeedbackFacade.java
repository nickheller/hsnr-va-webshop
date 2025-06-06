package com.hsnr.webshop.core.facade;

import com.hsnr.webshop.core.entities.Feedback;
import com.hsnr.webshop.core.facade.dto.FeedbackDTO;
import com.hsnr.webshop.core.facade.dto.FeedbackResponseDTO;
import com.hsnr.webshop.core.usecases.FeedbackService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackFacade {

    @Inject
    private FeedbackService service;

    @POST
    @RolesAllowed("kunde")
    @Path("/abgeben/{bestellnummer}")
    public void feedbackAbgeben(@PathParam("bestellnummer") Long bestellnummer, FeedbackDTO dto) {
        service.feedbackAbgeben(bestellnummer, dto.text);
    }

    @GET
    @RolesAllowed({"admin", "mitarbeiter"})
    @Path("/bestellung/{bestellnummer}")
    public List<FeedbackResponseDTO> feedbackZuBestellung(@PathParam("bestellnummer") Long bestellnummer) {
        List<Feedback> feedbackList = service.feedbackZuBestellung(bestellnummer);
        return mapToDTO(feedbackList);
    }

    @GET
    @RolesAllowed({"admin", "mitarbeiter"})
    public List<FeedbackResponseDTO> alleFeedbacks() {
        List<Feedback> feedbackList = service.alleFeedbacks();
        return mapToDTO(feedbackList);
    }

    private List<FeedbackResponseDTO> mapToDTO(List<Feedback> feedbackList) {
        List<FeedbackResponseDTO> responseList = new ArrayList<>();
        for (Feedback f : feedbackList) {
            FeedbackResponseDTO dto = new FeedbackResponseDTO();
            dto.id = f.getNummer();
            dto.text = f.getText();
            dto.bestellnummer = f.getBestellung().getBestellnummer();
            responseList.add(dto);
        }
        return responseList;
    }
}
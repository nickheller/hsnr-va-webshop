package com.hsnr.webshop.core.facade.dto;

import java.util.List;

public class BestellungsRequestDTO {
    public Long kundennummer;
    public List<BestellpositionDTO> positionen;
    public String lieferadresse;
    public String zahlungsmethode;
}
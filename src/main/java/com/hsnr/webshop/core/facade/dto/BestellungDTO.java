package com.hsnr.webshop.core.facade.dto;

import java.time.LocalDate;
import java.util.List;

public class BestellungDTO {
    public Long bestellnummer;
    public LocalDate bestelldatum;
    public String status;
    public String lieferadresse;
    public String zahlungsmethode;
    public double gesamtpreis;
    public List<BestellpositionDTO> positionen;
}
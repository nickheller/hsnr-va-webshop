BEGIN TRANSACTION;

-- 1) Standard-Admin anlegen (wenn noch nicht vorhanden)
INSERT INTO Benutzer (benutzerkennung, passwort, name, telefonnummer, rolle)
SELECT 'admin', 'geheim', 'Admin User', '123456789', 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM Benutzer WHERE benutzerkennung = 'admin'
);

-- 2) Standard-Mitarbeiter anlegen (wenn noch nicht vorhanden)
INSERT INTO Benutzer (benutzerkennung, passwort, name, telefonnummer, rolle)
SELECT 'mitarbeiter', 'passwort123', 'Mitarbeiter Mustermann', '987654321', 'mitarbeiter'
WHERE NOT EXISTS (
    SELECT 1 FROM Benutzer WHERE benutzerkennung = 'mitarbeiter'
);

-- 3) Standard-Kunde-Benutzer anlegen (wenn noch nicht vorhanden)
INSERT INTO Benutzer (benutzerkennung, passwort, name, telefonnummer, rolle)
SELECT 'kunde', 'kunde123', 'Kunde Beispiel', '555123456', 'kunde'
WHERE NOT EXISTS (
    SELECT 1 FROM Benutzer WHERE benutzerkennung = 'kunde'
);

-- 4) Kundendaten für den Standard-Kunden anlegen
--    Wir verknüpfen über die soeben angelegte Benutzerkennung 'kunde'
INSERT INTO Kunde (adresse, email, geburtsdatum, telefonnummer, zahlungsmethode, benutzer_id)
SELECT
    'Musterstraße 1',
    'kunde@example.com',
    DATE '1990-01-01',
    '555123456',
    'Rechnung',
    b.id
FROM Benutzer b
WHERE b.benutzerkennung = 'kunde'
  AND NOT EXISTS (
    SELECT 1 FROM Kunde k WHERE k.benutzer_id = b.id
  );

-- 5) Produkt anlegen
INSERT INTO PRODUKT (PRODUKTNUMMER, NAME, PREIS, BESTAND, BESCHREIBUNG, KATEGORIE, LIEFERZEIT)
VALUES (1, 'MacBook Pro', 2499.99, 2, 'Apple Laptop', 'Laptops', '2-3 Tage');

COMMIT;
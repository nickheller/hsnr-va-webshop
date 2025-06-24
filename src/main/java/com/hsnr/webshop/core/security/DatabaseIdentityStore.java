package com.hsnr.webshop.core.security;

import com.hsnr.webshop.core.dataaccess.BenutzerRepository;
import com.hsnr.webshop.core.entities.Benutzer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {

    @Inject
    private BenutzerRepository benutzerRepo;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;

            Benutzer benutzer = benutzerRepo.findByBenutzerkennung(upc.getCaller());

            if (benutzer != null && benutzer.getPasswort().equals(upc.getPasswordAsString())) {
                Set<String> rollen = new HashSet<>();
                rollen.add(benutzer.getRolle());
                return new CredentialValidationResult(benutzer.getBenutzerkennung(), rollen);
            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
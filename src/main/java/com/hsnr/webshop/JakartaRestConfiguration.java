package com.hsnr.webshop;

import jakarta.annotation.security.DeclareRoles;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@BasicAuthenticationMechanismDefinition(
    realmName = "webshop-realm"
)
@DeclareRoles({"admin", "mitarbeiter", "kunde"})
@ApplicationPath("api")
public class JakartaRestConfiguration extends Application {
}
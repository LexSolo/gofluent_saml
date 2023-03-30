package com.testtask.gofluent.config;

import java.io.File;
import java.security.cert.X509Certificate;
import org.opensaml.security.x509.X509Support;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

@Configuration
public class RepositoryConfig {

  @Bean
  protected RelyingPartyRegistrationRepository relyingPartyRegistrations() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    File verificationKey = new File(classLoader.getResource("cert/okta.crt").getFile());
    X509Certificate certificate = X509Support.decodeCertificate(verificationKey);
    Saml2X509Credential credential = Saml2X509Credential.verification(certificate);
    RelyingPartyRegistration registration = RelyingPartyRegistration
        .withRegistrationId("okta-saml")
        .assertingPartyDetails(party -> party
            .entityId("http://www.okta.com/exk8wl8as0GrX74bB5d7")
            .singleSignOnServiceLocation("https://dev-39875043.okta.com/app/dev-39875043_samlgofluentexample_1/exk8wl8as0GrX74bB5d7/sso/saml")
            .wantAuthnRequestsSigned(false)
            .verificationX509Credentials(c -> c.add(credential))
        ).build();
    return new InMemoryRelyingPartyRegistrationRepository(registration);
  }
}

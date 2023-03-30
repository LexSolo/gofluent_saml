package com.testtask.gofluent.config;

import java.io.File;
import java.security.cert.X509Certificate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.opensaml.security.x509.X509Support;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class SighOnLoginConfig {

  private final SamlLoginProperties samlLoginProperties;

  @Bean
  protected RelyingPartyRegistrationRepository relyingPartyRegistrations() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    File verificationKey = new File(
        Objects.requireNonNull(classLoader.getResource("cert/okta.crt")).getFile());
    X509Certificate certificate = X509Support.decodeCertificate(verificationKey);
    assert certificate != null;
    Saml2X509Credential credential = Saml2X509Credential.verification(certificate);
    RelyingPartyRegistration registration = RelyingPartyRegistration
        .withRegistrationId("okta-saml")
        .assertingPartyDetails(party -> party
            .entityId(samlLoginProperties.getEntityId())
            .singleSignOnServiceLocation(samlLoginProperties.getSighOnUrl())
            .wantAuthnRequestsSigned(false)
            .verificationX509Credentials(c -> c.add(credential)))
        .build();
    return new InMemoryRelyingPartyRegistrationRepository(registration);
  }
}

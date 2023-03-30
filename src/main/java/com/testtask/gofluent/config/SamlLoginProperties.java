package com.testtask.gofluent.config;

import lombok.Getter;
import lombok.Setter;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Setter
@Validated
public class SamlLoginProperties {

  @NotEmpty
  @Value("${spring.security.saml2.relyingparty.registration.okta-saml.identityprovider.entity-id}")
  private String entityId;

  @NotEmpty
  @Value(
      "${spring.security.saml2.relyingparty.registration.okta-saml.identityprovider.singlesignon.url}")
  private String sighOnUrl;
}

package com.testtask.gofluent.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SamlController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/secured/hello")
  public String secured(
      @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
    model.addAttribute("name", principal.getName());
    return "secured";
  }
}

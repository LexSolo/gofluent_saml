package com.testtask.gofluent.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SamlController {

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/secured/hello")
  public String hello(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
    model.addAttribute("name", principal.getName());
    return "secured";
  }
}

package io.github.maciejlagowski.airfield.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class AbcSpring extends GlobalMethodSecurityConfiguration {
}

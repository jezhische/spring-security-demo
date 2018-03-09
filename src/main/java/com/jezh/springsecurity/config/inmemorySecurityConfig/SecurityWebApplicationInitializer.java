package com.jezh.springsecurity.config.inmemorySecurityConfig;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// This class also allows to register a ContextLoaderListener with the specified classes, but it could be simply inherited
// to register "the DelegatingFilterProxy to use the springSecurityFilterChain before any other registered Filter".
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}

package com.alexb.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        CamelHttpTransportServlet camelHttpTransportServlet = new CamelHttpTransportServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean<>(camelHttpTransportServlet);

        registration.setName("CamelServlet");
        return registration;
    }
}

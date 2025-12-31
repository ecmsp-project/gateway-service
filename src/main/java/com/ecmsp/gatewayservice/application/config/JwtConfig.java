package com.ecmsp.gatewayservice.application.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ecmsp.gatewayservice.application.security.CmsJwtFilter;
import com.ecmsp.gatewayservice.application.security.JwtAuthenticationFilter;
import com.ecmsp.gatewayservice.application.security.JwtTokenReader;

@Configuration
class JwtConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtTokenReader jwtTokenReader) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtTokenReader));
        registrationBean.addUrlPatterns("/api/orders/*", "/api/returns/*", "/api/cart/*", "/api/users/*", "/api/roles/*", "/api/deliveries/*", "/api/statistics/*", "/api/products/*", "/api/categories/*", "/api/variants/*", "/api/properties/*");
                registrationBean.setOrder(100);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CmsJwtFilter> cmsJwtFilter(JwtTokenReader jwtTokenReader) {
        FilterRegistrationBean<CmsJwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CmsJwtFilter(jwtTokenReader));
        registrationBean.addUrlPatterns("/api/settings/*");
        registrationBean.setOrder(100);
        return registrationBean;
    }
}
package com.demo.demo.config;
//import org.springframework.security.config.oauth2.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.test.context.junit4.SpringRunner;



@Configuration
@EnableAuthorizationServer
public class oauth2config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userdetail")
    public UserDetailsService userdetail;

    @Autowired
    public PasswordEncoder passWordEncoder;



    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("wxy").secret(passWordEncoder.encode("123456"))
                .authorizedGrantTypes("refresh_token","password","client_credentials")
                .scopes("webclient");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .userDetailsService(this.userdetail)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}

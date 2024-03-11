package com.smartSpd.smartSpding.Infraestructure.Seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated()

                        .requestMatchers(HttpMethod.POST, "/contaInterna/registrarConta").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/contaInterna/editarConta").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/contaInterna/deletarConta/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/contaInterna/buscarConta").authenticated()
                        .requestMatchers(HttpMethod.GET, "/contaInterna/buscarContaInvidual/{id}").authenticated()

                        .requestMatchers(HttpMethod.POST, "/receita/registrarReceita").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/receita/editarReceita").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/receita/deletarRceita").authenticated()
                        .requestMatchers(HttpMethod.GET, "/receita/buscarCategoriaReceita").authenticated()
                        .requestMatchers(HttpMethod.GET, "/receita/buscarReceitasPorId/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/receita/buscarTituloContabilReceita/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/receita/buscarReceitas").authenticated()

                        .requestMatchers(HttpMethod.GET, "/origem/buscarOrigem").authenticated()

                        .requestMatchers(HttpMethod.POST, "/contaBancaria/registrarContaBancaria").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/contaBancaria/deletarContaBancaria/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/contaBancaria/buscarContaBancariaPorNome/{nome}").authenticated()
                                .anyRequest().authenticated()
                        //.anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

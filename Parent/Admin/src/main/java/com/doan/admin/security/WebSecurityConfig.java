package com.doan.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/home").permitAll()
                .antMatchers("/").hasAnyAuthority("Admin")// Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
                .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and().logout().permitAll()

        ;
        http.headers().frameOptions().sameOrigin();

//        http.authorizeRequests()
//                .antMatchers("/states/list_by_country/**").hasAnyAuthority("Admin", "Salesperson")
//                .antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
//                .antMatchers("/categories/**", "/brands/**", "/shops/**", "/articles/**", "/sections/**").hasAnyAuthority("Admin", "Editor")
//
//                .antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
//
//                .antMatchers("/products/edit/**", "/products/save", "/products/check_unique")
//                .hasAnyAuthority("Admin", "Editor", "Salesperson")
//
//                .antMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
//                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
//
//                .antMatchers("/products/**", "/menus/**", "/articles/**").hasAnyAuthority("Admin", "Editor")
//
//                .antMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
//
//                .antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Assistant")
//
//                .antMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin", "Salesperson")
//
//                .antMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
//
//                .antMatchers("/reviews/**", "/questions/**").hasAnyAuthority("Admin", "Assistant")
//
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .permitAll()
//                .and().logout().permitAll()
//                .and()
//                .rememberMe()
//                .key("AbcDefgHijKlmnOpqrs_1234567890")
//                .tokenValiditySeconds(7 * 24 * 60 * 60);
//        ;
//        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/error");
    }
}

package com.springBackendTest.blog.config

import com.springBackendTest.blog.account.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig (@Autowired private val accountService: AccountService,
                      @Autowired private val passwordEncoder: PasswordEncoder): WebSecurityConfigurerAdapter() {
    companion object {
        const val LOGIN_SUCCESS_URL: String = "/view/success"
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(accountService)
            .passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http
                .anonymous()
                    .and()
                .authorizeRequests()
                    .antMatchers("/css/**", "/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/user/**").hasAnyRole("USER")
//                    .antMatchers(HttpMethod.GET,"/**").permitAll()
//                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .successForwardUrl(LOGIN_SUCCESS_URL)
                    .and()
//                .csrf().disable()
    }
}
package lima.wallyson.WebSmartOffice.infraestructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetails: UserDetailsService
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(
                        "/swagger-ui/index.html#/api-controller/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**"
                    ).permitAll()
                requests.requestMatchers("/admin/**").hasRole("ADMIN")
                requests.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                requests.anyRequest().authenticated()
            }.userDetailsService(userDetails)
            .formLogin { it.defaultSuccessUrl("/home", true) }
            .logout { it.logoutUrl("/logout").logoutSuccessUrl("/") }

        return http.build()
    }
}


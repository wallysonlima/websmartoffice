package lima.wallyson.WebSmartOffice.infraestructure.configuration

import lima.wallyson.WebSmartOffice.application.usecase.PersonDetailsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val personDetails: PersonDetailsUseCase,
    private val corsConfigurationSource: CorsConfigurationSource
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(personDetails)
        authProvider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(authProvider)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource) }
            .csrf { it.disable() }
            .headers { headers -> headers.frameOptions { it.disable() } }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(
                    "/auth/**",
                    "/auth/logout",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-resources/**"
                ).permitAll()
                requests.requestMatchers("/admin/**").permitAll()
                requests.requestMatchers("/user/**").permitAll()
                requests.requestMatchers("/auth/**").permitAll()
                requests.anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) } // ✅ Garante que a sessão é criada
            .formLogin { it.disable() } // 🔹 Desativa o login automático do Spring Security

        return http.build()
    }
}


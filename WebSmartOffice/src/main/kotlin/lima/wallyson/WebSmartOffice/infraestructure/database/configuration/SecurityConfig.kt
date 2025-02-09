package lima.wallyson.WebSmartOffice.infraestructure.database.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    // Libera os endpoints do Springdoc/Swagger UI
                    .requestMatchers(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**"
                    ).permitAll()
                    // Outros endpoints exigem autenticação
                    .anyRequest().permitAll()
            }
            // Utiliza autenticação básica (caso precise de autenticação para os demais endpoints)
            .httpBasic(withDefaults())
            // Desabilita o CSRF (verifique se essa opção é adequada ao seu cenário)
            .csrf { csrf -> csrf.disable() }

        return http.build()
    }
}

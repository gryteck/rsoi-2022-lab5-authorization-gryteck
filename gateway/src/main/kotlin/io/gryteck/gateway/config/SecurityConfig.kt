package io.gryteck.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange()
            .pathMatchers(
                "/actuator/**",
                "/",
                "/api/v1/callback",
                "/api/v1/authorize",
                "/login"
            )
            .permitAll()
            .and()
            .authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
            .oauth2ResourceServer { it.jwt() }
//            .exceptionHandling {
//                it.authenticationEntryPoint(entryPoint()) // disables redirect and return 401
//            }
//            .oauth2Login { loginSpec ->
//                loginSpec.authenticationMatcher(PathPatternParserServerWebExchangeMatcher("/api/v1/callback"))
//            }
            .csrf().disable()

        return http.build()
    }

//    private fun entryPoint(): ServerAuthenticationEntryPoint {
//        val loginEntry = DelegatingServerAuthenticationEntryPoint.DelegateEntry(
//            ServerWebExchangeMatchers.pathMatchers("/api/v1/authorize"),
//            RedirectServerAuthenticationEntryPoint("/login")
//        )
//
//        return DelegatingServerAuthenticationEntryPoint(loginEntry).apply {
//            setDefaultEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
//        }
//    }
}

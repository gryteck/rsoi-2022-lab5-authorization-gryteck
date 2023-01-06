package io.gryteck.userservice

import io.gryteck.common.exception.CommonExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import reactivefeign.spring.config.EnableReactiveFeignClients

@SpringBootApplication
@EnableReactiveFeignClients
@Import(value = [CommonExceptionHandler::class, HttpMessageConvertersAutoConfiguration::class])
class UserServiceApplication

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}

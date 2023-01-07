package com.bory.tutorial.cleanarchitecture

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CleanArchitectureWithNewJpaApplication

fun main(args: Array<String>) {
    runApplication<CleanArchitectureWithNewJpaApplication>(*args)
}

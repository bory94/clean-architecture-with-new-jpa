package com.bory.tutorial.cleanarchitecture

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.docs.Documenter
import org.springframework.modulith.model.ApplicationModules

@SpringBootTest
class CleanArchitectureWithNewJpaApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun verifyModuleDependencies() {
        val modules =
            ApplicationModules.of(CleanArchitectureWithNewJpaApplication::class.java).verify()

        Documenter(modules)
            .writeDocumentation()
            .writeModulesAsPlantUml(
                Documenter.DiagramOptions.defaults().withTargetFileName("all-components.puml")
            )
    }

}

package com.bory.tutorial.cleanarchitecture.user.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInQueryUsecases
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/admin/v1/users")
class UserQueryController(
    private val genericUserInQueries: GenericUserInQueryUsecases
) {
    @GetMapping
    fun findAllUsers() = genericUserInQueries.findAll()

    @GetMapping("/{uuid}")
    fun findOneUser(@PathVariable("uuid") uuid: UUID) = genericUserInQueries.findOne(uuid)
}
package com.bory.tutorial.cleanarchitecture.user.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInCommands
import com.bory.tutorial.cleanarchitecture.user.domain.User
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping
class UserCommandController(
    private val genericUserInCommands: GenericUserInCommands
) {
    @PostMapping(("/public/v1/users"))
    fun createUser(@RequestBody @Valid user: User) =
        genericUserInCommands.create(user).let {
            ResponseEntity.created(URI("/v1/users/${it.uuid}")).build<Unit>()
        }

    @PutMapping("/v1/users/{uuid}")
    fun modifyUser(@PathVariable("uuid") uuid: UUID, @RequestBody @Valid user: User) =
        genericUserInCommands.modify(uuid, user).let {
            ResponseEntity.ok().build<Unit>()
        }

    @DeleteMapping(("/v1/users/{uuid}"))
    fun deleteUser(@PathVariable("uuid") uuid: UUID) =
        genericUserInCommands.delete(uuid).let {
            ResponseEntity.accepted().build<Unit>()
        }
}
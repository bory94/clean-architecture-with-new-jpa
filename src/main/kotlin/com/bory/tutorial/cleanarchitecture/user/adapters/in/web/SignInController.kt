package com.bory.tutorial.cleanarchitecture.user.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignInCommandUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.SignInVo
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/v1/sign-in")
class SignInController(
    private val signInCommand: SignInCommandUsecase
) {
    @PostMapping
    fun signIn(@RequestBody @Valid signInVo: SignInVo) =
        signInCommand.signIn(signInVo).let { token ->
            ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build<Unit>()
        }
}
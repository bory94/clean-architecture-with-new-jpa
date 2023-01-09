package com.bory.tutorial.cleanarchitecture.user.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignUpCommand
import com.bory.tutorial.cleanarchitecture.user.domain.SignUpVo
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/v1/sign-up")
class SignUpController(
    private val signUpCommand: SignUpCommand
) {
    @PostMapping
    fun signUp(@RequestBody @Valid signUpVo: SignUpVo) = signUpCommand.signUp(signUpVo).let {
        ResponseEntity.ok().build<Unit>()
    }
}
package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toEntity(user: User) = with(user) {
        UserEntity(uuid, name, email, password)
    }

    fun toDomain(userEntity: UserEntity) = with(userEntity) {
        User(uuid, name, email, password, createdBy, createdTime, modifiedBy, modifiedTime)
    }

    fun copyToEntity(user: User, userEntity: UserEntity) = userEntity.apply {
        name = user.name
        email = user.email
        password = user.password
    }
}
package me.ppvan.meplace.repository

import me.ppvan.meplace.data.User

interface AuthService {
    fun register(user: User): Boolean
    fun login(username: String, password: String): User
    fun currentUser(): User

    fun update(user: User)

    fun logout(): Boolean
}
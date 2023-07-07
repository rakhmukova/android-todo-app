package com.example.todoapp.data.remote.model

/**
 * Sealed class representing the user authorization state.
 */
sealed class UserAuthorization {
    data class Authorized(
        val accessToken: String
    ) : UserAuthorization()
//    class NotAuthorized() : UserAuthorization()
}

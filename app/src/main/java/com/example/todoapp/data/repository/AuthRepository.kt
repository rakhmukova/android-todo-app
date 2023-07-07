package com.example.todoapp.data.repository

import com.example.todoapp.data.remote.model.UserAuthorization
import com.example.todoapp.di.component.AppScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Repository class responsible for managing user authorization.
 */
@AppScope
class AuthRepository @Inject constructor() {
    // todo: add authorization logic
    private val _userAuth: MutableStateFlow<UserAuthorization> = MutableStateFlow(
        UserAuthorization.Authorized("your_token")
    )
    val userAuth = _userAuth.asStateFlow()
}

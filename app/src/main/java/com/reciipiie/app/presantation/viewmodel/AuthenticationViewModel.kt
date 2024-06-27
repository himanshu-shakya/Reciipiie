package com.reciipiie.app.presantation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reciipiie.app.MainActivity
import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.common.utils.UiState
import com.reciipiie.app.common.utils.UsernamePreferences
import com.reciipiie.app.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authRepo: AuthenticationRepository,
    private val userNamePreferences: UsernamePreferences
) : ViewModel() {
    private val _continueWithGoogle = MutableStateFlow<UiState<String>>(UiState.Idle)
    val continueWithGoogle = _continueWithGoogle
    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    init {
        viewModelScope.launch {
            userNamePreferences.getUserName.collect { name ->
                _userName.value = name ?: ""
            }
        }
    }

    fun continueWithGoogle(mainActivity: MainActivity) {
        _continueWithGoogle.update { UiState.Loading }
        viewModelScope.launch {
            when (val result = authRepo.continueWithGoogle(mainActivity)) {
                is Result.Error -> {
                    _continueWithGoogle.update { UiState.Error(result.error) }
                }

                is Result.Success -> {
                    _continueWithGoogle.update {
                        saveUserName(result.data)
                        UiState.Success(result.data)
                    }
                }
            }
        }

    }

    private fun saveUserName(userName: String) {
        viewModelScope.launch {
            userNamePreferences.saveUserName(userName)
        }
    }


}
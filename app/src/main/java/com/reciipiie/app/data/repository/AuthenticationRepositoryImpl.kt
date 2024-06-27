package com.reciipiie.app.data.repository

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.reciipiie.app.MainActivity
import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.domain.repository.AuthenticationRepository
import io.appwrite.enums.OAuthProvider
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account

class AuthenticationRepositoryImpl(private val account: Account) : AuthenticationRepository {
    override suspend fun continueWithGoogle(activity: MainActivity): Result<String> {
        return try {
            var userName by mutableStateOf("")
            account.createOAuth2Session(
                activity = activity,
                provider = OAuthProvider.GOOGLE,
            )
            userName = when(val result = getUserName()){
                is Result.Error -> {
                    ""
                }

                is Result.Success -> {
                    result.data
                }
            }
            Result.Success(userName)
        } catch (e: AppwriteException) {
            Log.i("TAG", "continueWithGoogle: ${e.message}")
            Result.Error(e.message ?: "Unknown Error")
        } catch (e: Exception) {
            Log.i("TAG", "continueWithGoogle: ${e.message}")
            Result.Error(e.message ?: "Unknown Error")
        }
    }

    private suspend fun getUserName(): Result<String> {
        return try {
            val user = account.get()
            Log.i("TAG", "continueWithGoogle: ${user.name}")
            Result.Success(user.name)

        } catch (e: AppwriteException) {
            Log.i("TAG", "continueWithGoogle: ${e.message}")
            Result.Error(e.message ?: "Unknown Error")
        } catch (e: Exception) {
            Log.i("TAG", "continueWithGoogle: ${e.message}")
            Result.Error(e.message ?: "Unknown Error")
        }
    }
}
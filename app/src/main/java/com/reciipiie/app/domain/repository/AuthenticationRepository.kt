package com.reciipiie.app.domain.repository

import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.MainActivity

interface AuthenticationRepository {
    suspend fun continueWithGoogle(activity: MainActivity): Result<String>
}
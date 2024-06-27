package com.reciipiie.app.common.utils


sealed class Result<out T>{
    data class Success<out T:Any>(val data:T): Result<T>()
    data class Error(val error:String): Result<Nothing>()
}

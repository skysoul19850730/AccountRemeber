package com.jscoolstar.accountremeber.model

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
sealed class SSResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : SSResult<T>()
    data class Error(val exception: Exception) : SSResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
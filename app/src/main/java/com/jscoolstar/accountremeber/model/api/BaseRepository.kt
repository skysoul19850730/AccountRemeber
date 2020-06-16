package com.jscoolstar.accountremeber.model.api

import java.io.IOException
import com.jscoolstar.accountremeber.model.SSResult

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class BaseRepository {


    protected suspend fun <T : Any> safeApiCall(call: suspend () -> SSResult<T>?, errorMessage: String): SSResult<T> {
        return try {
            call()?:SSResult.Error(Exception("result is null"))
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            SSResult.Error(IOException("$errorMessage : ${e.message}", e))
        }
    }



}
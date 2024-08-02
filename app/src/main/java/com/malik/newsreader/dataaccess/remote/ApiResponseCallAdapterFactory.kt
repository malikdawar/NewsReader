package com.malik.newsreader.dataaccess.remote

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * The [ApiResponseCallAdapterFactory].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    val responseType = getParameterUpperBound(0, callType as ParameterizedType)
                    ApiResponseCallBackAdapter(responseType)
                }

                else -> null
            }
        }

        else -> null
    }
}
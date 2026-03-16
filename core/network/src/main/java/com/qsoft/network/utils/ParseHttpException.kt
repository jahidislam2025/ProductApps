package com.qsoft.network.utils

import com.google.gson.Gson
import com.qsoft.network.model.CommonErrorModel
import retrofit2.HttpException

fun parseHttpException(e: HttpException): CommonErrorModel {
    return try {
        val gson = Gson()
        val errorBody = e.response()?.errorBody()?.string()
        val obj = try {
            gson.fromJson(errorBody, CommonErrorModel::class.java)
        } catch (_: Exception) {
            null
        }

        CommonErrorModel(
            message =  obj?.message ?: ExceptionalMessage.SOMETHING_WENT_WRONG,
            statusCode = obj?.statusCode ?: e.code(),
        )
    } catch (exception: Exception) {
        CommonErrorModel(
            message = exception.message.toString(),
        )
    }
}
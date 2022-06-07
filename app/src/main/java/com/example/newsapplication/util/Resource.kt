package com.example.newsapplication.util
// this class is used to wrap api response to differentiate between success response and error response
// sealed class is like abstract but with defined classes that are allowed to inherit from this sealed class
sealed class Resource<T>(
    val data: T? = null,  //body of our response
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}
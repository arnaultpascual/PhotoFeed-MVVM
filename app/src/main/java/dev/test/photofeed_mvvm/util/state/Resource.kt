package dev.test.photofeed_mvvm.util.state

import android.util.Log

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val errorMessage: ErrorMessage?) {
    companion object {

        private const val TAG = "Resource"

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(msg: String, error: ErrorMessage?): Resource<T> {
            Log.e(TAG, "error: $msg")
            return Resource(Status.ERROR, null, msg, error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null, null)
        }

        fun <T> wasSuccessful(data: T?): Resource<T> {
            return Resource(Status.WAS_SUCCESSFUL, data, null, null)
        }
    }
}

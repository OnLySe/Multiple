package com.zzq.saf.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.*

suspend fun createRandomString(length: Int): String {
    if (length < 1) {
        return ""
    }
    val result = withContext(Dispatchers.Default) {
        val random = Random()
        val stringBuilder = StringBuilder(length)
        repeat(length) {
            stringBuilder.append(random.nextInt(10000))
        }

        async {
            val stringBuilder = StringBuilder(length)
            repeat(length) {
                stringBuilder.append(random.nextInt(10000))
            }
            stringBuilder.toString()
        }
    }
    return result.await()
}

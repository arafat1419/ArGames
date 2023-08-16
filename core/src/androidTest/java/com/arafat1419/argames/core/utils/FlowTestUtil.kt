package com.arafat1419.argames.core.utils

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object FlowTestUtil {
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    suspend fun <T> Flow<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: suspend () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)

        val job = kotlinx.coroutines.GlobalScope.launch {
            this@getOrAwaitValue.collect {
                data = it
                latch.countDown()
            }
        }

        try {
            afterObserve.invoke()

            // Don't wait indefinitely if the Flow is not emitting.
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("Flow value was never emitted.")
            }
        } finally {
            job.cancel()
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}

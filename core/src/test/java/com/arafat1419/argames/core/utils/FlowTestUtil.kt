package com.arafat1419.argames.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object FlowTestUtil {
    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(flow: Flow<T>): T {
        val latch = CountDownLatch(1)
        var result: T? = null

        runBlocking {
            launch {
                flow.collect { value ->
                    result = value
                    latch.countDown()
                }
            }
        }

        try {
            latch.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return result as T
    }
}
package hulkdx.com.carsearch.executor

import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
class CustomThreadExecutor @Inject constructor(): ThreadExecutor {

    companion object {
        private const val CORE_SIZE = 3
        private const val MAX_CORE_SIZE = 10
    }

    private val mThreadPoolExecutor: ThreadPoolExecutor
    private val mThreadCounter = AtomicInteger()

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(
            CORE_SIZE, MAX_CORE_SIZE, 1, TimeUnit.SECONDS,
            LinkedBlockingQueue()
        ) { runnable -> Thread(runnable, "custom_thread_" + mThreadCounter.getAndIncrement()) }
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun submit(runnable: Runnable): Future<*> {
        return mThreadPoolExecutor.submit(runnable)
    }

    override fun shutdown() {
        mThreadPoolExecutor.shutdown()
    }
}
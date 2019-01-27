package hulkdx.com.carsearch.executor


import java.util.concurrent.Future
import javax.inject.Inject
import javax.inject.Singleton
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Mohammad Jafarzadeh Rezvan on 10/11/2018.
 */

interface ThreadExecutor {
    fun execute(runnable: Runnable)
    fun submit(runnable: Runnable): Future<*>
    fun shutdown()
}

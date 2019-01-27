package hulkdx.com.carsearch.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.RunnableFuture
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
class MainThreadExecutor @Inject
constructor(): ThreadExecutor {

    private val mMainHandler: Handler

    init {
        val looper: Looper = Looper.getMainLooper()
        mMainHandler = Handler(looper)
    }

    override fun execute(runnable: Runnable) {
        mMainHandler.post(runnable)
    }

    override fun submit(runnable: Runnable): Future<*> {
        val future: RunnableFuture<*> = FutureTask(runnable, null)
        // There is no need to cancel or shutdown MainThreadExecutor
        mMainHandler.post(future)
        return future
    }

    override fun shutdown() {
    }
}
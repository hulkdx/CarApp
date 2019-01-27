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
class TrampolineThreadExecutor @Inject constructor(): ThreadExecutor {


    init {
    }

    override fun execute(runnable: Runnable) {
        runnable.run()
    }

    override fun submit(runnable: Runnable): Future<*> {
        val future: RunnableFuture<*> = FutureTask(runnable, null)
        runnable.run()
        return future
    }

    override fun shutdown() {
    }
}
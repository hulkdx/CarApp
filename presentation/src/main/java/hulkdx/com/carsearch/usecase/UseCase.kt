package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.executor.ThreadExecutor
import hulkdx.com.carsearch.utils.Result
import java.util.concurrent.Future

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 *
 * Note: there can be a RxUseCase that follow the same pattern in Rx way.
 */
abstract class UseCase<PARAMS, RESULT>(private val mThreadExecutor: ThreadExecutor,
                                       private val mPostThreadExecutor: ThreadExecutor)
    :IUseCase<PARAMS, RESULT>
{

    private var mFuture: Future<*>? = null

    /**
     * Execute a task by doing it on background and post the result to MainThread.
     *
     * Note that this can be changed with RxJava or Coroutines in Kotlin, I haven't used them because it was mentioned
     * to use a few libs.
     *
     * @param params: parameters to run on background.
     * @param callback: callback in the form of Result.class)
     */
    override fun execute(params: PARAMS, callback: Result<RESULT>) {
        try {
            mFuture = mThreadExecutor.submit(Runnable {
                try {
                    val result = doOnBackground(params)
                    mPostThreadExecutor.execute(Runnable {
                        callback.onSuccess(result)
                    })
                } catch (t: Throwable) {
                    mPostThreadExecutor.execute(Runnable {
                        callback.onError(t)
                    })
                }
            })
        } catch (t: Throwable) {
            mPostThreadExecutor.execute(Runnable {
                callback.onError(t)
            })
        }
    }

    /**
     * The task that needs to run on the background Thread.
     */
    protected abstract fun doOnBackground(params: PARAMS): RESULT

    override fun dispose() {
        mFuture?.let {
            //
            // Sometimes it needs to Interrupt the Thread couple of times to dispose it.
            // But, do it for less than 200ms.
            //
            val startedTime: Long = System.currentTimeMillis()
            while (!it.isCancelled && (System.currentTimeMillis() - startedTime) < 200) {
                it.cancel(true)
            }
        }
    }
}

interface IUseCase<PARAMS, RESULT> {
    fun execute(params: PARAMS, callback: Result<RESULT>)
    fun dispose()
}
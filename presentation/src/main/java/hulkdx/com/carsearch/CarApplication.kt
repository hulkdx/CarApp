package hulkdx.com.carsearch

import android.app.Application
import hulkdx.com.carsearch.di.components.ApplicationComponent
import hulkdx.com.carsearch.di.components.createAppComponent
import timber.log.Timber

/**
 * Created by Mohammad Jafarzadeh Rezvan on 25/01/2019.
 */
class CarApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        applicationComponent = createAppComponent()
    }
}
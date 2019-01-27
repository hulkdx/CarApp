package hulkdx.com.carsearch.di.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import hulkdx.com.carsearch.di.annotations.ActivityContext

/**
 * Created by Mohammad Jafarzadeh Rezvan on 09/11/2018.
 */

@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return mActivity
    }
}

package hulkdx.com.carsearch.di.components

import dagger.Subcomponent
import hulkdx.com.carsearch.di.annotations.PerActivity
import hulkdx.com.carsearch.di.modules.ActivityModule
import hulkdx.com.carsearch.ui.main.MainActivity

/**
 * Created by Mohammad Jafarzadeh Rezvan on 09/11/2018.
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}

/**
 * Created by Mohammad Jafarzadeh Rezvan on 7/6/2017.
 */

package hulkdx.com.carsearch.di.components

import dagger.Component
import hulkdx.com.carsearch.di.annotations.ConfigPersistent
import hulkdx.com.carsearch.di.modules.ActivityModule

/**
 * Created by Mohammad Jafarzadeh Rezvan on 09/11/2018.
 */

@ConfigPersistent
@Component(dependencies = [ApplicationComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

}
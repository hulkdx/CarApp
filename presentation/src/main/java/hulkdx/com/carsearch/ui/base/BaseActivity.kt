package hulkdx.com.carsearch.ui.base

import android.os.Bundle
import android.util.LongSparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import hulkdx.com.carsearch.CarApplication
import hulkdx.com.carsearch.di.components.ActivityComponent
import hulkdx.com.carsearch.di.components.ConfigPersistentComponent
import hulkdx.com.carsearch.di.components.DaggerConfigPersistentComponent
import hulkdx.com.carsearch.di.modules.ActivityModule
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val ACTIVITY_ID = "ACTIVITY_ID"

        private var sActivityId = 0
        private val sComponentsMap = LongSparseArray<ConfigPersistentComponent>()
    }

    var activityComponent: ActivityComponent? = null
        private set



    //---------------------------------------------------------------
    // Lifecycle
    //---------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createConfigPersistentActivityComponent(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            onDestroyWithoutConfigurationChange()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(ACTIVITY_ID, sActivityId)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            onDestroyWithoutConfigurationChange()
        }
    }

    /**
     * Note: this is called when the activity is destroyed without configuration changes this happens in 2 ways:
     * 1. in onPause of Activity
     * 2. onDestroy if its !isChangingConfigurations
     */
    open fun onDestroyWithoutConfigurationChange() {
        removeConfigPersistentComponent()
    }

    //---------------------------------------------------------------
    // Config Persistent Component
    //---------------------------------------------------------------

    /**
     * Create ActivityComponent that can survive a configuration using a static HashMap. (In memory)
     */
    private fun createConfigPersistentActivityComponent(savedInstanceState: Bundle?) {
        sActivityId = savedInstanceState?.getInt(ACTIVITY_ID) ?: sActivityId + 1
        var configPersistentComponent: ConfigPersistentComponent? = sComponentsMap.get(sActivityId.toLong(), null)
        if (configPersistentComponent == null) {
            Timber.i("Creating new component");
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                .applicationComponent((application as CarApplication).applicationComponent)
                .build()
            sComponentsMap.put(sActivityId.toLong(), configPersistentComponent)
        } else {
            Timber.i("re-using component, activityId: " + sActivityId);
        }
        activityComponent = configPersistentComponent!!.activityComponent(ActivityModule(this))
    }

    private fun removeConfigPersistentComponent() {
        // Logger.i("Clearing Component id=%d", sActivityId);
        sComponentsMap.remove(sActivityId.toLong())
    }

    //---------------------------------------------------------------
    // Fragments
    //---------------------------------------------------------------

    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(containerViewId, fragment).commit()
    }

    protected fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(containerViewId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

}

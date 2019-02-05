package hulkdx.com.carsearch.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.ui.base.BaseActivity
import hulkdx.com.carsearch.ui.main.builtdates.CarBuiltDateFragment
import hulkdx.com.carsearch.ui.main.cartype.CarTypeFragment
import hulkdx.com.carsearch.ui.main.manufacturer.ManufacturersFragment
import hulkdx.com.carsearch.utils.FragmentActionType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    //---------------------------------------------------------------
    // Lifecycle
    //---------------------------------------------------------------

    @Inject lateinit var mMainPresenter: MainPresenter

    // these are here to update the backstack, e.g. the StatusBar and UI, check onBackPressed code for more info.
    private var mLastFragmentActionType: FragmentActionType? = null
    private var mCurrentFragmentActionType: FragmentActionType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDrawer(savedInstanceState == null)

        activityComponent?.inject(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
            mLastFragmentActionType?.let {
                updateStatusBar(it)
            }
        }
    }

    override fun onDestroyWithoutConfigurationChange() {
        super.onDestroyWithoutConfigurationChange()
        mMainPresenter.detach()
    }

    //---------------------------------------------------------------
    // Setup Views
    //---------------------------------------------------------------

    private fun setupDrawer(isFirstTimeLoaded: Boolean) {
        setSupportActionBar(toolbar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout,  toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_manufactures -> {
                    switchFragments(FragmentActionType.Manufacturer)
                }
                R.id.nav_car_types -> {
                    switchFragments(FragmentActionType.CarType)
                }
                R.id.nav_built_date -> {
                    switchFragments(FragmentActionType.CarBuiltDate)
                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        if (isFirstTimeLoaded) {
            // Set the default one:
            switchFragments(FragmentActionType.Manufacturer, isFirstTimeLoaded = true)
        }
    }

    fun switchFragments(fragmentActionType: FragmentActionType,
                        ids: List<String>? = null,
                        isFirstTimeLoaded: Boolean = false) {

        mLastFragmentActionType = mCurrentFragmentActionType
        mCurrentFragmentActionType = fragmentActionType

        updateStatusBar(fragmentActionType)

        val fragment: Fragment
        when (fragmentActionType) {
            FragmentActionType.Manufacturer -> {
                fragment = ManufacturersFragment()
            }
            FragmentActionType.CarType -> {
                fragment = CarTypeFragment.newInstance(ids?.get(0))
            }
            FragmentActionType.CarBuiltDate -> {
                fragment = CarBuiltDateFragment.newInstance(manufactureId = ids?.get(0), carType = ids?.get(0))
            }
        }
        if (isFirstTimeLoaded) addFragment(R.id.fragment_content, fragment)
        else replaceFragment(R.id.fragment_content, fragment)

    }

    private fun updateStatusBar(fragmentActionType: FragmentActionType) {
        val statusBarTitle: String
        when (fragmentActionType) {
            FragmentActionType.Manufacturer -> {
                statusBarTitle = getString(R.string.manufacturers)
                nav_view.setCheckedItem(R.id.nav_manufactures)
            }
            FragmentActionType.CarType -> {
                statusBarTitle = getString(R.string.car_types)
                nav_view.setCheckedItem(R.id.nav_car_types)
            }
            FragmentActionType.CarBuiltDate -> {
                statusBarTitle = getString(R.string.car_built_date)
                nav_view.setCheckedItem(R.id.nav_built_date)
            }
        }
        supportActionBar?.title = statusBarTitle
    }
}
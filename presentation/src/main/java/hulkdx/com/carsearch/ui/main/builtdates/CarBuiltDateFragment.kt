package hulkdx.com.carsearch.ui.main.builtdates


import android.os.Bundle
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.ui.main.BaseMainFragment
import hulkdx.com.carsearch.ui.main.cartype.CarTypeFragment


open class CarBuiltDateFragment : BaseMainFragment() {

    companion object {

        private const val ARG_MANUFACTURE_ID = "ARG_MANUFACTURE_ID"
        private const val ARG_CAR_TYPE = "ARG_CAR_TYPE"

        private const val DEFAULT_MANUFACTURE_ID = "020"
        private const val DEFAULT_CAR_TYPE = "500"

        fun newInstance(manufactureId: String? = null,
                        carType: String? = null): CarBuiltDateFragment {
            val fragment = CarBuiltDateFragment()

            manufactureId?.let {
                val args = Bundle()
                args.putString(ARG_MANUFACTURE_ID, manufactureId)
                args.putString(ARG_CAR_TYPE, carType)
                fragment.arguments = args
            }

            return fragment
        }
    }

    private lateinit var mManufactureId: String
    private lateinit var mCarType: String

    private lateinit var mAdapter: CarBuiltDateAdapter

    //---------------------------------------------------------------
    // Lifecycle:
    //---------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mManufactureId = arguments?.getString(ARG_MANUFACTURE_ID, DEFAULT_MANUFACTURE_ID) ?: DEFAULT_MANUFACTURE_ID
        mCarType       = arguments?.getString(ARG_CAR_TYPE,       DEFAULT_CAR_TYPE)       ?: DEFAULT_CAR_TYPE
    }

    //---------------------------------------------------------------
    // BaseMainFragment Abstracts:
    //---------------------------------------------------------------

    override fun recyclerId() = R.id.recycler_m
    override fun rootLayoutId() = R.layout.fragment_built_dates

    override fun loadData() {
        getMainPresenter().loadCarBuiltDates(mManufactureId, mCarType)
    }

    override fun createAdapter() {
        mAdapter = CarBuiltDateAdapter()
        mRecyclerView.adapter = mAdapter
    }

    override fun clearAdapter() {
        mAdapter.setData(listOf())
    }


    //---------------------------------------------------------------
    // Mvp View.Contract
    //---------------------------------------------------------------

    override fun onCarBuiltDateLoaded(value: List<CarBuiltDate>) {
        mAdapter.setData(value)
        mRecyclerView.scheduleLayoutAnimation()
    }
}

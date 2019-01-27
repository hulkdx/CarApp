package hulkdx.com.carsearch.ui.main.cartype

import android.os.Bundle
import android.view.View
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.ui.main.BaseMainFragment
import hulkdx.com.carsearch.utils.FragmentActionType

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
class CarTypeFragment: BaseMainFragment() {

    companion object {

        private const val ARG_MANUFACTURE_ID = "ARG_MANUFACTURE_ID"
        private const val DEFAULT_MANUFACTURE_ID = "020"

        fun newInstance(manufactureId: String?): CarTypeFragment {
            val fragment = CarTypeFragment()

            manufactureId?.let {
                val args = Bundle()
                args.putString(ARG_MANUFACTURE_ID, manufactureId)
                fragment.arguments = args
            }

            return fragment
        }
    }

    private lateinit var mManufactureId: String
    private lateinit var mAdapter: CarTypeAdapter

    //---------------------------------------------------------------
    // Lifecycle:
    //---------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mManufactureId = arguments?.getString(ARG_MANUFACTURE_ID, DEFAULT_MANUFACTURE_ID) ?: DEFAULT_MANUFACTURE_ID
    }

    //---------------------------------------------------------------
    // BaseMainFragment Abstracts:
    //---------------------------------------------------------------

    override fun rootLayoutId() = R.layout.fragment_cartypes

    override fun recyclerId() = R.id.recycler_m

    override fun loadData() {
        getMainPresenter().loadCarTypes(mManufactureId, mPaginationNumber)
    }

    override fun createAdapter() {
        mAdapter = CarTypeAdapter(View.OnClickListener { viewClicked ->
            val position = mRecyclerView.getChildLayoutPosition(viewClicked)
            if (position >= 0) {
                mAdapter.getData(position)?.let { carType ->
                    onItemClick(carType, viewClicked)
                }
            }
        })
        mRecyclerView.adapter = mAdapter
    }

    override fun clearAdapter() {
        mAdapter.setData(listOf())
    }

    //---------------------------------------------------------------
    // OnClick Adapter Items:
    //---------------------------------------------------------------

    private fun onItemClick(carType: CarType, view: View) {
        getMainActivity().switchFragments(FragmentActionType.CarBuiltDate, listOf(mManufactureId, carType.name))
    }

    //---------------------------------------------------------------
    // Mvp View.Contract
    //---------------------------------------------------------------

    override fun onCarTypeLoaded(carTypes: List<CarType>) {
        mAdapter.setData(carTypes)
        mRecyclerView.scheduleLayoutAnimation()
    }

    override fun onCarTypeLoadedEmpty(pageNumber: Int) {
        mMaxPaginationNumber = pageNumber
    }
}
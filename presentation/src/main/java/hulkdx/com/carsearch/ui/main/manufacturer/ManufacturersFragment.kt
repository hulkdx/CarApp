package hulkdx.com.carsearch.ui.main.manufacturer


import android.os.Bundle
import android.view.View
import android.widget.SearchView
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.ui.main.BaseMainFragment
import hulkdx.com.carsearch.utils.FragmentActionType


open class ManufacturersFragment : BaseMainFragment(), SearchView.OnQueryTextListener {

    private lateinit var mManufacturerAdapter: ManufacturerAdapter

    private lateinit var mSearchView: SearchView

    //---------------------------------------------------------------
    // Lifecycle
    //---------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSearchView = view.findViewById(R.id.search_view)
        mSearchView.setOnQueryTextListener(this)
    }

    //---------------------------------------------------------------
    // BaseMainFragment Abstracts:
    //---------------------------------------------------------------

    override fun recyclerId() = R.id.recycler_m
    override fun rootLayoutId() = R.layout.fragment_manufactures
    override fun loadData() = getMainPresenter().loadManufacturers(mPaginationNumber)


    override fun createAdapter() {
        mManufacturerAdapter = ManufacturerAdapter(View.OnClickListener { viewClicked ->
            val position = mRecyclerView.getChildLayoutPosition(viewClicked)
            if (position >= 0) {
                mManufacturerAdapter.getData(position)?.let { manufacture ->
                    onItemClick(manufacture, viewClicked)
                }
            }
        })
        mRecyclerView.adapter = mManufacturerAdapter
    }

    override fun clearAdapter() = mManufacturerAdapter.setData(listOf())

    //---------------------------------------------------------------
    // OnClick Adapter Items:
    //---------------------------------------------------------------

    private fun onItemClick(manufacture: Manufacturer, view: View) {
        val manufactureId = manufacture.id
        getMainActivity().switchFragments(FragmentActionType.CarType, listOf(manufactureId))
    }

    //---------------------------------------------------------------
    // SearchView:
    //---------------------------------------------------------------

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText ?: ""

        if (searchText == "") getMainPresenter().loadManufacturers(mPaginationNumber)
        else getMainPresenter().searchManufacturers(searchText)

        return false
    }

    override fun onQueryTextSubmit(query: String?) = false

    //---------------------------------------------------------------
    // Mvp View.Contract
    //---------------------------------------------------------------

    override fun onManufacturersLoaded(value: List<Manufacturer>) {
        mManufacturerAdapter.setData(value)
        mRecyclerView.scheduleLayoutAnimation()
    }

    override fun onManufacturersLoadedEmpty(pageNumber: Int) {
        mMaxPaginationNumber = pageNumber
    }
}

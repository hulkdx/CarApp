package hulkdx.com.carsearch.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 *
 * Because the fragments are very close to each other I created this 'BaseFragment' to handle
 */
abstract class BaseMainFragment: Fragment(), MainContract.View {
    
    internal var mPaginationNumber: Int = 0
    internal var mMaxPaginationNumber: Int = -1

    private  lateinit var mPaginationTextView: TextView
    internal lateinit var mRecyclerView: RecyclerView

    //---------------------------------------------------------------
    // Lifecycle
    //---------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(rootLayoutId(), container, false)
        setupRecyclerView(view)
        setupPagination(view)
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMainPresenter().attach(this)
        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMainPresenter().detach()
    }

    //---------------------------------------------------------------
    // Setup Views
    //---------------------------------------------------------------

    private fun setupRecyclerView(view: View) {

        mRecyclerView = view.findViewById(recyclerId())
        val resId = R.anim.layout_animation_fall_down
        val controller = AnimationUtils.loadLayoutAnimation(context, resId)
        mRecyclerView.layoutAnimation = controller
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        createAdapter()
    }

    private fun setupPagination(view: View) {
        val paginationBtnLeft: ImageButton = view.findViewById(R.id.pagination_btn_left)
        val paginationBtnRight: ImageButton = view.findViewById(R.id.pagination_btn_right)
        mPaginationTextView = view.findViewById(R.id.pagination_txt)

        updatePaginationTextView()
        paginationBtnRight.setOnClickListener {
            if (mMaxPaginationNumber != -1 && mPaginationNumber == mMaxPaginationNumber) return@setOnClickListener
            mPaginationNumber++
            paginationUpdated()
        }
        paginationBtnLeft.setOnClickListener {
            if (mPaginationNumber == 0) return@setOnClickListener
            mPaginationNumber--
            paginationUpdated()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePaginationTextView() {
        mPaginationTextView.text = getString(R.string.page_number) + "\n${mPaginationNumber+1}"
    }

    private fun paginationUpdated() {
        updatePaginationTextView()
        clearAdapter()
        loadData()
    }

    //---------------------------------------------------------------
    // Getter/Setter
    //---------------------------------------------------------------

    internal fun getMainActivity() = activity as MainActivity

    internal fun getMainPresenter() = getMainActivity().mMainPresenter

    //---------------------------------------------------------------
    // Mvp View.Contract
    //---------------------------------------------------------------
    override fun loading() {}
    override fun onError(throwable: Throwable, from: String) {}
    override fun onManufacturersLoaded(value: List<Manufacturer>) {}
    override fun onManufacturersLoadedEmpty(pageNumber: Int) {}
    override fun onCarTypeLoaded(carTypes: List<CarType>) {}
    override fun onCarTypeLoadedEmpty(pageNumber: Int) {}
    override fun onCarBuiltDateLoaded(value: List<CarBuiltDate>) {}
    //---------------------------------------------------------------
    // Abstracts: 
    //---------------------------------------------------------------
    
    abstract fun rootLayoutId(): Int
    abstract fun recyclerId(): Int
    abstract fun loadData()
    abstract fun createAdapter()
    abstract fun clearAdapter()
}

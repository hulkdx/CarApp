package hulkdx.com.carsearch.ui.base

import hulkdx.com.carsearch.ui.main.MainContract

/**
 * Created by Mohammad Jafarzadeh Rezvan on 25/01/2019.
 */
open class BasePresenter<T: MainContract.View>: BaseContract.Presenter<T> {

    var view: T? = null
        private set

    fun isViewAttached(): Boolean = view != null

    override fun attach(view: T) {
        this.view = view
    }

    override fun detach() {
        view = null
    }
}
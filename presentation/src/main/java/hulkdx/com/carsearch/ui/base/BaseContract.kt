package hulkdx.com.carsearch.ui.base

import hulkdx.com.carsearch.ui.main.MainContract

/**
 * Created by Mohammad Jafarzadeh Rezvan on 25/01/2019.
 */
interface BaseContract {
    interface Presenter<T: MainContract.View> {
        fun attach(view: T)
        fun detach()
    }
}
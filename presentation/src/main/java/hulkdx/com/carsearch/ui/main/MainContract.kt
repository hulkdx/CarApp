package hulkdx.com.carsearch.ui.main

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.ui.base.BaseContract

/**
 * Created by Mohammad Jafarzadeh Rezvan on 25/01/2019.
 */
interface MainContract {
    interface View {
        fun loading()
        fun onError(throwable: Throwable, from: String)
        fun onManufacturersLoaded(value: List<Manufacturer>)
        fun onManufacturersLoadedEmpty(pageNumber: Int)
        fun onCarTypeLoadedEmpty(pageNumber: Int)
        fun onCarTypeLoaded(carTypes: List<CarType>)
        fun onCarBuiltDateLoaded(value: List<CarBuiltDate>)
    }

    interface Presenter<T : View>: BaseContract.Presenter<T> {
        fun loadManufacturers(pageNumber: Int)
        fun loadCarTypes(manufactureId: String, pageNumber: Int)
        fun loadCarBuiltDates(manufactureId: String, carType: String)
        fun searchManufacturers(searchText: String)
    }
}
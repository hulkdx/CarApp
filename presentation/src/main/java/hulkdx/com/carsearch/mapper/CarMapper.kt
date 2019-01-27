package hulkdx.com.carsearch.mapper

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.data.models.remoteresponse.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
open class CarMapper @Inject constructor() {

    open fun convertToManufacturers(apiResponse: ApiResponse): List<Manufacturer> {
        val list = mutableListOf<Manufacturer>()
        for ((key, value) in apiResponse.map) {
            list.add(Manufacturer(name = value, id = key))
        }

        return list
    }

    open fun convertToCarTypes(apiResponse: ApiResponse): List<CarType> {
        val list = mutableListOf<CarType>()
        for ((key, value) in apiResponse.map) {
            list.add(CarType(name = value))
        }

        return list
    }

    fun convertToCarBuiltDate(apiResponse: ApiResponse): List<CarBuiltDate> {
        val list = mutableListOf<CarBuiltDate>()
        for ((key, value) in apiResponse.map) {
            list.add(CarBuiltDate(value))
        }
        return list
    }

}
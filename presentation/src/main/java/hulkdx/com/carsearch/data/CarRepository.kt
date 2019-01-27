package hulkdx.com.carsearch.data

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.data.remote.ApiManager
import hulkdx.com.carsearch.mapper.CarMapper
import hulkdx.com.carsearch.utils.getPagination
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
open class CarRepository @Inject constructor(private val apiManager: ApiManager,
                                             private val mCarMapper: CarMapper)
{
    @Volatile
    private var totalManufacturerCount: Int = -1

    // Note: multiple thread access this object so it needs to be synchronized or define as Volatile
    // Please read CustomThreadExecutor.kt
    @Volatile
    private var memoryCachedManufacturers: List<Manufacturer> = Collections.synchronizedList(ArrayList())
        set(value) {
            field = Collections.synchronizedList(value)
        }

    open fun getManufacturers(size: Int, page: Int): List<Manufacturer> {

        if (memoryCachedManufacturers.isNotEmpty()) {
            return memoryCachedManufacturers.getPagination(page, size)
        }

        val apiResponse = apiManager.getManufacturer(size, page)

        totalManufacturerCount = apiResponse.totalPageCount * size

        return mCarMapper.convertToManufacturers(apiResponse)
    }

    open fun getAllManufacturers(): List<Manufacturer> {

        if (memoryCachedManufacturers.isNotEmpty()) {
            return memoryCachedManufacturers
        }

        if (totalManufacturerCount == -1) {
            getManufacturers(1, 0)
        }

        val manufacturers = getManufacturers(totalManufacturerCount, 0)

        memoryCachedManufacturers = manufacturers

        return manufacturers
    }

    open fun getCarTypes(pageSize: Int, pageNumber: Int, manufactureId: String): List<CarType> {
        return apiManager.getCarTypes(pageSize, pageNumber, manufactureId)
    }

    open fun getCarBuiltDates(manufactureId: String, carType: String): List<CarBuiltDate> {
        return apiManager.getBuiltDates(manufactureId, carType)
    }
}

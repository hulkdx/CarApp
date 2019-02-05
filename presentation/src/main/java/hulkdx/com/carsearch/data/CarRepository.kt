package hulkdx.com.carsearch.data

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.data.remote.ApiManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
open class CarRepository @Inject constructor(private val apiManager: ApiManager,
                                             private val mMemoryCache: MemoryCache)
{

    open fun getManufacturers(size: Int, page: Int): List<Manufacturer> {

        mMemoryCache.getManufacturer(page, size)?.let {
            return it
        }

        val (manufacturers, totalManufacturerCount) = apiManager.getManufacturer(size, page)
        mMemoryCache.totalManufacturerCount = totalManufacturerCount

        return manufacturers
    }

    open fun getAllManufacturers(): List<Manufacturer> {

        mMemoryCache.getAllManufacturers()?.let {
            return it
        }

        var totalManufacturerCount = mMemoryCache.totalManufacturerCount
        if (totalManufacturerCount == -1) {
            totalManufacturerCount = apiManager.getManufacturer(1, 0).secondParam
            mMemoryCache.totalManufacturerCount = totalManufacturerCount
        }

        val manufacturers = getManufacturers(totalManufacturerCount, 0)
        mMemoryCache.manufacturers = manufacturers
        return manufacturers
    }

    open fun getCarTypes(pageSize: Int, pageNumber: Int, manufactureId: String): List<CarType> {
        return apiManager.getCarTypes(pageSize, pageNumber, manufactureId)
    }

    open fun getCarBuiltDates(manufactureId: String, carType: String): List<CarBuiltDate> {
        return apiManager.getBuiltDates(manufactureId, carType)
    }
}

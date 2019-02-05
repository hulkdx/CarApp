package hulkdx.com.carsearch.data

import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.utils.getPagination
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryCache @Inject constructor() {

    @Volatile
    var totalManufacturerCount: Int = -1

    // Note: multiple thread access this object so it needs to be synchronized or define as Volatile
    // Please read CustomThreadExecutor.kt
    @Volatile
    var manufacturers: List<Manufacturer> = Collections.synchronizedList(ArrayList())
        set(value) {
            field = Collections.synchronizedList(value)
        }

    fun getManufacturer(page: Int, size: Int): List<Manufacturer>? {
        if (manufacturers.isEmpty()) {
            return null
        }

        return manufacturers.getPagination(page, size)
    }

    fun getAllManufacturers(): List<Manufacturer>? {
        if (manufacturers.isEmpty()) {
            return null
        }

        return manufacturers
    }
}

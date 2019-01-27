package hulkdx.com.carsearch

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.data.models.remoteresponse.ApiResponse
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Mohammad Jafarzadeh Rezvan on 27/01/2019.
 */

fun createManufacturer(nId: String? = null, nName: String? = null): Manufacturer {

    val id = nId ?: "${Random().nextInt()}"
    val name= nName ?: "${Random().nextInt()}"

    return Manufacturer(id, name)
}

fun createManufacturers(number: Int): List<Manufacturer> {
    val list = mutableListOf<Manufacturer>()

    for (i in 0..number) {
        list.add(createManufacturer(nId = "id=$i", nName =  "name=$i"))
    }

    return list
}

fun createCarType(nName: String? = null): CarType {

    val name= nName ?: "${Random().nextInt()}"

    return CarType(name)
}

fun createCarTypes(number: Int): List<CarType> {
    val list = mutableListOf<CarType>()

    for (i in 0..number) {
        list.add(createCarType(nName =  "name=$i"))
    }

    return list
}

fun createCarBuiltDate(nName: String? = null): CarBuiltDate {

    val name= nName ?: "${Random().nextInt()}"

    return CarBuiltDate(name)
}

fun createCarBuiltDates(number: Int): List<CarBuiltDate> {
    val list = mutableListOf<CarBuiltDate>()

    for (i in 0..number) {
        list.add(createCarBuiltDate(nName =  "name=$i"))
    }

    return list
}

fun createApiResponse(list: List<Manufacturer>): ApiResponse {

    val hashMap = HashMap<String, String>()

    for (m in list) {
        hashMap.put(m.id, m.name)
    }

    return ApiResponse(1, 1, 1, hashMap)
}
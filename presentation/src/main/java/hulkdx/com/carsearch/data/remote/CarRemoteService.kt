package hulkdx.com.carsearch.data.remote

import com.google.gson.GsonBuilder
import hulkdx.com.carsearch.BuildConfig.BASE_URL
import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.data.models.remoteresponse.ApiResponse
import hulkdx.com.carsearch.mapper.CarMapper
import hulkdx.com.carsearch.utils.TwoParam
import hulkdx.com.carsearch.utils.WA_KEY
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
open class ApiManager @Inject constructor(private val mCarRemoteService: CarRemoteService,
                                          private val mCarMapper: CarMapper)
{

    companion object {

        fun newService(): CarRemoteService {
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).build()
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(CarRemoteService::class.java)
        }
    }

    @Throws(Exception::class)
    open fun getManufacturer(pageSize: Int, page: Int): TwoParam<List<Manufacturer>, Int> {
        val apiResponse= mCarRemoteService.getManufacturer(WA_KEY, pageSize, page).execute()

        if (!apiResponse.isSuccessful) {
            throw RuntimeException("getManufacturer API is not successful, HTTP-Code: ${apiResponse.code()}")
        }

        val bodyResponse = apiResponse.body()!!

        return TwoParam(mCarMapper.convertToManufacturers(bodyResponse), bodyResponse.pageSize * bodyResponse.totalPageCount)
    }

    @Throws(Exception::class)
    open fun getCarTypes(pageSize: Int, pageNumber: Int, manufactureId: String): List<CarType> {
        val apiResponse= mCarRemoteService.getCarTypes(WA_KEY, pageSize, pageNumber,
            manufactureId).execute()

        if (!apiResponse.isSuccessful) {
            throw RuntimeException("getManufacturer API is not successful, HTTP-Code: ${apiResponse.code()}")
        }

        val bodyResponse = apiResponse.body()
        return mCarMapper.convertToCarTypes(bodyResponse!!)
    }


    @Throws(Exception::class)
    open fun getBuiltDates(manufactureId: String, carType: String): List<CarBuiltDate> {
        val apiResponse= mCarRemoteService.getBuiltDates(WA_KEY, manufactureId, carType).execute()

        if (!apiResponse.isSuccessful) {
            throw RuntimeException("getManufacturer API is not successful, HTTP-Code: ${apiResponse.code()}")
        }

        val bodyResponse = apiResponse.body()
        return mCarMapper.convertToCarBuiltDate(bodyResponse!!)
    }
}

interface CarRemoteService {
    @GET("v1/car-types/manufacturer")
    fun getManufacturer(@Query("wa_key") wa_key: String,
                        @Query("pageSize") pageSize: Int,
                        @Query("page") page: Int): Call<ApiResponse>


    @GET("v1/car-types/main-types")
    fun getCarTypes(@Query("wa_key") wa_key: String,
                    @Query("pageSize") pageSize: Int,
                    @Query("page") page: Int,
                    @Query("manufacturer") manufacturerId: String): Call<ApiResponse>


    @GET("v1/car-types/built-dates")
    fun getBuiltDates(@Query("wa_key") wa_key: String,
                      @Query("manufacturer") manufacturerId: String,
                      @Query("main-type") carType: String): Call<ApiResponse>
}
package hulkdx.com.carsearch.data

import hulkdx.com.carsearch.*
import hulkdx.com.carsearch.data.models.remoteresponse.ApiResponse
import hulkdx.com.carsearch.data.remote.ApiManager
import hulkdx.com.carsearch.data.remote.CarRemoteService
import hulkdx.com.carsearch.mapper.CarMapper
import hulkdx.com.carsearch.utils.anyz
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@RunWith(JUnit4::class)
class CarRepositoryTest {

    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()!!

    lateinit var sut: CarRepository

    @Mock lateinit var apiManager: ApiManager
    @Mock lateinit var carMapper: CarMapper

    private val TEST_DATA_CARTYPES = createCarTypes(5)
    private val TEST_DATA_MANUFACTURERS = createManufacturers(5)
    private val TEST_DATA_BUILTDATES = createCarBuiltDates(5)
    private val TEST_API_RESPONSE = createApiResponse(TEST_DATA_MANUFACTURERS)

    @Before
    fun setUp() {
        sut = CarRepository(apiManager, carMapper)
    }

    @Test
    fun getManufacturers_should_call_api() {
        val pageSize: Int  = Random().nextInt()
        val pageNumber: Int = Random().nextInt()

        sut.getManufacturers(pageSize, pageNumber)

        verify(apiManager).getManufacturer(pageSize, pageNumber)
    }

    @Test
    fun getManufacturers_should_return_test_data() {
            
        val pageSize: Int  = Random().nextInt()
        val pageNumber: Int = Random().nextInt()

        Mockito.`when`(apiManager.getManufacturer(pageSize, pageNumber))
            .thenReturn(TEST_API_RESPONSE)

        Mockito.`when`(carMapper.convertToManufacturers(TEST_API_RESPONSE))
            .thenReturn(TEST_DATA_MANUFACTURERS)

        val manufacturers = sut.getManufacturers(pageSize, pageNumber)

        assertEquals(TEST_DATA_MANUFACTURERS, manufacturers)
    }

    @Test
    fun getCarTypes_should_call_api() {
        val pageSize: Int  = Random().nextInt()
        val pageNumber: Int = Random().nextInt()
        val manufactureId = "${Random().nextInt()}"

        sut.getCarTypes(pageSize, pageNumber, manufactureId)

        verify(apiManager).getCarTypes(pageSize, pageNumber, manufactureId)
    }

    @Test
    fun getCarTypes_should_return_test_data() {

        val pageSize: Int  = Random().nextInt()
        val pageNumber: Int = Random().nextInt()
        val manufactureId = "${Random().nextInt()}"


        Mockito.`when`(apiManager.getCarTypes(pageSize, pageNumber, manufactureId))
            .thenReturn(TEST_DATA_CARTYPES)

        val result = sut.getCarTypes(pageSize, pageNumber, manufactureId)

        assertEquals(TEST_DATA_CARTYPES, result)
    }

    @Test
    fun getCarBuiltDates_should_call_api() {
        val manufactureId = "${Random().nextInt()}"
        val carType = "${Random().nextInt()}"

        sut.getCarBuiltDates(manufactureId, carType)

        verify(apiManager).getBuiltDates(manufactureId, carType)
    }

    @Test
    fun getCarBuiltDates_should_return_test_data() {

        val manufactureId = "${Random().nextInt()}"
        val carType = "${Random().nextInt()}"

        Mockito.`when`(apiManager.getBuiltDates(manufactureId, carType))
            .thenReturn(TEST_DATA_BUILTDATES)

        val result = sut.getCarBuiltDates(manufactureId, carType)

        assertEquals(TEST_DATA_BUILTDATES, result)
    }
}
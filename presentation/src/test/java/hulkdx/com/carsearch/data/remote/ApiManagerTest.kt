package hulkdx.com.carsearch.data.remote

import hulkdx.com.carsearch.mapper.CarMapper
import hulkdx.com.carsearch.ui.main.MainPresenter.Companion.PAGE_SIZE
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnit

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@RunWith(JUnit4::class)
class ApiManagerTest {

    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()!!

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        val carMapper = CarMapper()
        apiManager = ApiManager(ApiManager.newService(), carMapper)
    }

    @Test
    fun getManufacturer_test() {

        val result = apiManager.getManufacturer(PAGE_SIZE, 0)

        assertTrue(result.map.isNotEmpty())
    }

    @Test
    fun getCarTypes_test() {
        val result = apiManager.getCarTypes(PAGE_SIZE, 0, "020")
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun getBuiltDates_test() {
        val result = apiManager.getBuiltDates("020", "500")
        assertTrue(result.isNotEmpty())
    }
}

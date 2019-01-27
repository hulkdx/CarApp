package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.createManufacturers
import hulkdx.com.carsearch.data.CarRepository
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.executor.TrampolineThreadExecutor
import hulkdx.com.carsearch.utils.Result
import hulkdx.com.carsearch.utils.anyz
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import java.lang.RuntimeException

/**
 * Created by Mohammad Jafarzadeh Rezvan on 27/01/2019.
 */
@RunWith(JUnit4::class)
class GetManufacturersUseCaseTest {

    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()!!
    
    // System under test
    private lateinit var sut: GetManufacturersUseCase
    
    // Mocks
    @Mock lateinit var carRepository: CarRepository
    @Mock lateinit var resultObject: Result<List<Manufacturer>>

    // Tests
    private val TEST_MANUFACTURERS = createManufacturers(5)

    @Before
    fun setUp() {
        val threadExecutor     = TrampolineThreadExecutor()
        val postThreadExecutor = TrampolineThreadExecutor()
        sut = GetManufacturersUseCase(threadExecutor,
                                      postThreadExecutor,
                                      carRepository)
    }
    
    @Test
    fun execute_should_call_carRepository() {
        stub_carRepository()
        sut.execute(0, resultObject)
        verify(carRepository).getManufacturers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())
    }

    @Test
    fun execute_should_call_onSuccess() {
        stub_carRepository()
        sut.execute(0, resultObject)
        verify(resultObject).onSuccess(TEST_MANUFACTURERS)
    }

    @Test
    fun execute_should_call_onError_when_carRepository_throws_error() {

        val throwable: Throwable = RuntimeException("TEST ERROR")
        `when`(carRepository.getManufacturers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenThrow(throwable)

        sut.execute(0, resultObject)

        verify(resultObject).onError(throwable)
    }

    private fun stub_carRepository() {
        `when`(carRepository.getManufacturers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenReturn(TEST_MANUFACTURERS)
    }
}
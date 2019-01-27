package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.createCarTypes
import hulkdx.com.carsearch.data.CarRepository
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.executor.TrampolineThreadExecutor
import hulkdx.com.carsearch.utils.Result
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import java.lang.RuntimeException

/**
 * Created by Mohammad Jafarzadeh Rezvan on 27/01/2019.
 */
@RunWith(JUnit4::class)
class GetCarTypesUseCaseTest {

    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()!!

    // System under test
    private lateinit var sut: GetCarTypesUseCase

    // Mocks
    @Mock
    lateinit var carRepository: CarRepository
    @Mock
    lateinit var resultObject: Result<List<CarType>>

    // Tests
    private val TEST_DATA = createCarTypes(5)

    @Before
    fun setUp() {
        val threadExecutor     = TrampolineThreadExecutor()
        val postThreadExecutor = TrampolineThreadExecutor()
        sut = GetCarTypesUseCase(threadExecutor, postThreadExecutor, carRepository)
    }

    @Test
    fun execute_should_call_carRepository() {
        stub_carRepository()
        sut.execute(GetCarTypesUseCase.Params("020", 0), resultObject)
        Mockito.verify(carRepository).getCarTypes(anyInt(), anyInt(), anyString())
    }

    @Test
    fun execute_should_call_onSuccess() {
        stub_carRepository()
        sut.execute(GetCarTypesUseCase.Params("020", 0), resultObject)
        Mockito.verify(resultObject).onSuccess(TEST_DATA)
    }

    @Test
    fun execute_should_call_onError_when_carRepository_throws_error() {

        val throwable: Throwable = RuntimeException("TEST ERROR")
        Mockito.`when`(carRepository.getCarTypes(anyInt(), anyInt(), anyString()))
            .thenThrow(throwable)

        sut.execute(GetCarTypesUseCase.Params("020", 0), resultObject)

        Mockito.verify(resultObject).onError(throwable)
    }

    private fun stub_carRepository() {
        Mockito.`when`(carRepository.getCarTypes(anyInt(), anyInt(), anyString()))
            .thenReturn(TEST_DATA)
    }
}
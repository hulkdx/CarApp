package hulkdx.com.carsearch

import hulkdx.com.carsearch.ui.main.MainContract
import hulkdx.com.carsearch.ui.main.MainPresenter
import hulkdx.com.carsearch.usecase.IGetCarBuiltDatesUseCase
import hulkdx.com.carsearch.usecase.IGetManufacturersUseCase
import hulkdx.com.carsearch.usecase.IGetCarTypesUseCase
import hulkdx.com.carsearch.usecase.ISearchManufacturersUseCase
import hulkdx.com.carsearch.utils.anyz
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit

@RunWith(JUnit4::class)
class MainPresenterTest {

    @JvmField
    @Rule
    var mockitoRule = MockitoJUnit.rule()!!

    private lateinit var sut: MainPresenter
    @Mock lateinit var view: MainContract.View
    @Mock lateinit var getManufacturersUseCase: IGetManufacturersUseCase
    @Mock lateinit var getCarTypesUseCase: IGetCarTypesUseCase
    @Mock lateinit var getCarBuiltDatesUseCase: IGetCarBuiltDatesUseCase
    @Mock lateinit var searchManufacturersUseCase: ISearchManufacturersUseCase

    @Before
    fun setUp() {
        sut = MainPresenter(getManufacturersUseCase, getCarTypesUseCase, getCarBuiltDatesUseCase, searchManufacturersUseCase)
        sut.attach(view)
    }

    @Test
    fun loadManufacturers_calls_loading() {
        sut.loadManufacturers(0)
        verify(view).loading()
    }

    @Test
    fun loadManufacturers_calls_getManufacturersUseCase() {
        sut.loadManufacturers(0)
        verify(getManufacturersUseCase).execute(anyz(), anyz())
    }

    @Test
    fun loadCarTypes_calls_loading() {
        sut.loadCarTypes("Test", 1)
        verify(view).loading()
    }

    @Test
    fun loadCarTypes_calls_getCarTypesUseCase() {
        sut.loadCarTypes("Test", 1)
        verify(getCarTypesUseCase).execute(anyz(), anyz())
    }

    @Test
    fun getCarBuiltDatesUseCase_calls_getCarBuiltDatesUseCase() {
        sut.loadCarBuiltDates("Test", "")
        verify(getCarBuiltDatesUseCase).execute(anyz(), anyz())
    }

    @Test
    fun searchManufacturers_calls_getCarTypesUseCase() {
        sut.searchManufacturers("Test")
        verify(searchManufacturersUseCase).execute(anyz(), anyz())
    }

}

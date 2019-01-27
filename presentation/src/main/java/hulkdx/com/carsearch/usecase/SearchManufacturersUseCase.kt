package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.data.CarRepository
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.di.annotations.APostThreadExecutor
import hulkdx.com.carsearch.di.annotations.AThreadExecutor
import hulkdx.com.carsearch.executor.ThreadExecutor
import hulkdx.com.carsearch.ui.main.MainPresenter
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Singleton
open class SearchManufacturersUseCase @Inject constructor(@AThreadExecutor threadExecutor: ThreadExecutor,
                                                          @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                                          private val mCarRepository: CarRepository)

    : UseCase<String, List<Manufacturer>>(threadExecutor, postThreadExecutor), ISearchManufacturersUseCase
{

    override fun doOnBackground(params: String): List<Manufacturer> {

        val searchValue = params.toLowerCase()

        val allManufacturers = mCarRepository.getAllManufacturers()
        val searchList = mutableListOf<Manufacturer>()
        for (manufacturer in allManufacturers) {
            if (manufacturer.name.toLowerCase().contains(searchValue)) {
                searchList.add(manufacturer)
            }
        }
        return searchList
    }

}

// For test purposes:
interface ISearchManufacturersUseCase: IUseCase<String, List<Manufacturer>>
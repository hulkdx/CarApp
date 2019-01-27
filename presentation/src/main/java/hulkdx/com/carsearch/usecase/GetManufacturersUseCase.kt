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
open class GetManufacturersUseCase @Inject constructor(@AThreadExecutor threadExecutor: ThreadExecutor,
                                                       @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                                       private val mCarRepository: CarRepository)

    : UseCase<Int, List<Manufacturer>>(threadExecutor, postThreadExecutor), IGetManufacturersUseCase
{

    override fun doOnBackground(params: Int): List<Manufacturer> {
        return mCarRepository.getManufacturers(MainPresenter.PAGE_SIZE, params)
    }

}

// For test purposes:
interface IGetManufacturersUseCase: IUseCase<Int, List<Manufacturer>>
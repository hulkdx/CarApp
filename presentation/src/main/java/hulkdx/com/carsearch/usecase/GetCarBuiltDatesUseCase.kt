package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.data.CarRepository
import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
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
open class GetCarBuiltDatesUseCase @Inject constructor(@AThreadExecutor threadExecutor: ThreadExecutor,
                                                       @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                                       private val mCarRepository: CarRepository)

    : UseCase<GetCarBuiltDatesUseCase.Params, List<CarBuiltDate>>(threadExecutor, postThreadExecutor), IGetCarBuiltDatesUseCase
{

    override fun doOnBackground(params: Params): List<CarBuiltDate> {
        return mCarRepository.getCarBuiltDates(params.manufactureId, params.carType)
    }

    data class Params(val manufactureId: String, val carType: String)
}

// For test purposes, because probably mockito have problem with abstract class?
interface IGetCarBuiltDatesUseCase: IUseCase<GetCarBuiltDatesUseCase.Params, List<CarBuiltDate>>

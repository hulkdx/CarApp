package hulkdx.com.carsearch.usecase

import hulkdx.com.carsearch.data.CarRepository
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
open class GetCarTypesUseCase @Inject constructor(@AThreadExecutor threadExecutor: ThreadExecutor,
                                                   @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                                   private val mCarRepository: CarRepository)

    : UseCase<GetCarTypesUseCase.Params, List<CarType>>(threadExecutor, postThreadExecutor), IGetCarTypesUseCase
{

    override fun doOnBackground(params: GetCarTypesUseCase.Params): List<CarType> {
        return mCarRepository.getCarTypes(MainPresenter.PAGE_SIZE, params.pageNumber, params.manufactureId)
    }

    data class Params(val manufactureId: String, val pageNumber: Int)
}

// For test purposes, because probably mockito have problem with abstract class?
interface IGetCarTypesUseCase: IUseCase<GetCarTypesUseCase.Params, List<CarType>>

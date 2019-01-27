package hulkdx.com.carsearch.di.modules

import dagger.Module
import dagger.Provides
import hulkdx.com.carsearch.data.CarRepository
import hulkdx.com.carsearch.data.remote.ApiManager
import hulkdx.com.carsearch.data.remote.CarRemoteService
import hulkdx.com.carsearch.di.annotations.APostThreadExecutor
import hulkdx.com.carsearch.di.annotations.AThreadExecutor
import hulkdx.com.carsearch.executor.CustomThreadExecutor
import hulkdx.com.carsearch.executor.MainThreadExecutor
import hulkdx.com.carsearch.executor.ThreadExecutor
import hulkdx.com.carsearch.usecase.*
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 09/11/2018.
 */
@Module
class ApplicationModule() {

    @Provides
    @AThreadExecutor
    @Singleton
    fun providesThreadExecutor(customThreadExecutor: CustomThreadExecutor): ThreadExecutor {
        return customThreadExecutor
    }

    @Provides
    @APostThreadExecutor
    @Singleton
    fun providesPostThreadExecutor(mainThreadExecutor: MainThreadExecutor): ThreadExecutor {
        return mainThreadExecutor
    }

    @Provides
    @Singleton
    fun providesApiManager(): CarRemoteService {
        return ApiManager.newService()
    }

    @Provides
    @Singleton
    fun providesGetManufacturersUseCase(@AThreadExecutor threadExecutor: ThreadExecutor,
                                        @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                        carRepository: CarRepository
    ): IGetManufacturersUseCase {
        return GetManufacturersUseCase(threadExecutor, postThreadExecutor, carRepository)
    }

    @Provides
    @Singleton
    fun providesIGetCarTypesUseCase(@AThreadExecutor threadExecutor: ThreadExecutor,
                                    @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                    carRepository: CarRepository
    ): IGetCarTypesUseCase {
        return GetCarTypesUseCase(threadExecutor, postThreadExecutor, carRepository)
    }

    @Provides
    @Singleton
    fun providesIGetCarBuiltDatesUseCase(@AThreadExecutor threadExecutor: ThreadExecutor,
                                         @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                         carRepository: CarRepository
    ): IGetCarBuiltDatesUseCase {
        return GetCarBuiltDatesUseCase(threadExecutor, postThreadExecutor, carRepository)
    }

    @Provides
    @Singleton
    fun providesISearchManufacturersUseCase(@AThreadExecutor threadExecutor: ThreadExecutor,
                                         @APostThreadExecutor postThreadExecutor: ThreadExecutor,
                                         carRepository: CarRepository
    ): ISearchManufacturersUseCase {
        return SearchManufacturersUseCase(threadExecutor, postThreadExecutor, carRepository)
    }
}

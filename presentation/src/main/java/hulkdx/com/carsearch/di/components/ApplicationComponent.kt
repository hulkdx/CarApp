package hulkdx.com.carsearch.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import hulkdx.com.carsearch.CarApplication
import hulkdx.com.carsearch.data.remote.CarRemoteService
import hulkdx.com.carsearch.di.modules.ApplicationModule
import hulkdx.com.carsearch.usecase.*
import javax.inject.Singleton

/**
 * Created by Mohammad Jafarzadeh Rezvan on 09/11/2018.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: CarApplication)

    fun GetManufacturersUseCase(): IGetManufacturersUseCase
    fun IGetCarTypesUseCase(): IGetCarTypesUseCase
    fun IGetCarBuiltDatesUseCase(): IGetCarBuiltDatesUseCase
    fun ISearchManufacturersUseCase(): ISearchManufacturersUseCase
    fun ApiManager(): CarRemoteService
}

fun Application.createAppComponent() = DaggerApplicationComponent.builder()
    .application(this)
    .build()
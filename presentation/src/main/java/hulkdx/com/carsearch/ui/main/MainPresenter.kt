package hulkdx.com.carsearch.ui.main

import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.di.annotations.PerActivity
import hulkdx.com.carsearch.ui.base.BasePresenter
import hulkdx.com.carsearch.usecase.*
import hulkdx.com.carsearch.utils.Result

import javax.inject.Inject

/**
 * Created by Mohammad Jafarzadeh Rezvan on 25/01/2019.
 */
@PerActivity
class MainPresenter @Inject constructor(private val mGetManufacturersUseCase: IGetManufacturersUseCase,
                                        private val mGetCarTypesUseCase: IGetCarTypesUseCase,
                                        private val mGetCarBuiltDatesUseCase: IGetCarBuiltDatesUseCase,
                                        private val mSearchManufacturersUseCase: ISearchManufacturersUseCase
)
    : BasePresenter<MainContract.View>(), MainContract.Presenter<MainContract.View>
{
    companion object {
        const val PAGE_SIZE = 15
    }

    override fun detach() {
        super.detach()
        mGetManufacturersUseCase.dispose()
    }

    override fun loadManufacturers(pageNumber: Int) {
        view?.loading()
        mGetManufacturersUseCase.execute(pageNumber, object : Result<List<Manufacturer>> {
            override fun onSuccess(value: List<Manufacturer>) {
                if (value.isEmpty())
                    view?.onManufacturersLoadedEmpty(pageNumber)
                else view?.onManufacturersLoaded(value)
            }

            override fun onError(throwable: Throwable) {
                view?.onError(throwable, "loadManufacturers")
            }
        })
    }

    override fun loadCarTypes(manufactureId: String, pageNumber: Int) {
        view?.loading()
        mGetCarTypesUseCase.execute(GetCarTypesUseCase.Params(manufactureId, pageNumber),
            object: Result<List<CarType>> {
                override fun onSuccess(value: List<CarType>) {
                    if (value.isEmpty())
                        view?.onCarTypeLoadedEmpty(pageNumber)
                    else view?.onCarTypeLoaded(value)
                }

                override fun onError(throwable: Throwable) {
                    view?.onError(throwable, "loadCarTypes")
                }
            })
    }


    override fun loadCarBuiltDates(manufactureId: String, carType: String) {
        view?.loading()
        mGetCarBuiltDatesUseCase.execute(GetCarBuiltDatesUseCase.Params(manufactureId, carType),
            object: Result<List<CarBuiltDate>> {
                override fun onSuccess(value: List<CarBuiltDate>) {
                    view?.onCarBuiltDateLoaded(value)
                }

                override fun onError(throwable: Throwable) {
                    view?.onError(throwable, "loadCarTypes")
                }
            })
    }


    override fun searchManufacturers(searchText: String) {
        view?.loading()

        mSearchManufacturersUseCase.execute(searchText, object: Result<List<Manufacturer>> {
            override fun onSuccess(value: List<Manufacturer>) {
                view?.onManufacturersLoaded(value)
            }

            override fun onError(throwable: Throwable) {
                view?.onError(throwable, "loadCarTypes")
            }
        })
    }

}
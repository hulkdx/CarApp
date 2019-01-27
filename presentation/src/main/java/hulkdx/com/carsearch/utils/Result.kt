package hulkdx.com.carsearch.utils

/**
 * Created by Mohammad Jafarzadeh Rezvan on 27/01/2019.
 */
public interface Result<T> {
    fun onSuccess(value: T)
    fun onError(throwable: Throwable)
}
package hulkdx.com.carsearch.utils

import org.mockito.Mockito

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 * https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791
 */
fun <T> anyz(): T {
    Mockito.any<T>()
    return uninitialized()
}
private fun <T> uninitialized(): T = null as T
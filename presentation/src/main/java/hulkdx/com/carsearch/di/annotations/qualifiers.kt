package hulkdx.com.carsearch.di.annotations

import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AThreadExecutor


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class APostThreadExecutor

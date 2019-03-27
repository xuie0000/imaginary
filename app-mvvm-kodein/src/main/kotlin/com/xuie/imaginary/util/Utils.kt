package com.xuie.imaginary.util

/**
 * @author Jie Xu
 * @date 17-7-5
 */
object Utils {
  fun <T> checkNotNull(reference: T?): T {
    return if (reference == null) {
      throw NullPointerException()
    } else {
      reference
    }
  }

  fun <T> checkNotNull(reference: T?, errorMessage: Any?): T {
    if (reference == null) {
      throw NullPointerException(errorMessage.toString())
    }
    return reference
  }

}

/**
 * Used to allow Singleton with arguments in Kotlin while keeping the code efficient and safe.
 *
 * See https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
open class SingletonHolderSingleArg<out T, in A>(private val creator: (A) -> T) {

  @Volatile
  private var instance: T? = null

  fun getInstance(arg: A): T =
      instance ?: synchronized(this) {
        instance ?: creator(arg).apply {
          instance = this
        }
      }
}

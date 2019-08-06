package xuk.imaginary.util

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

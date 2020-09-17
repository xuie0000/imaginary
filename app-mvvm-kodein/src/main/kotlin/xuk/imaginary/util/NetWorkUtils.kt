package xuk.imaginary.util

import android.content.Context
import android.net.ConnectivityManager
import java.util.regex.Pattern

/**
 * @author Jie Xu
 * @date 17-8-17
 */
object NetWorkUtils {

  /**
   * 检查网络是否可用
   *
   * @param paramContext
   * @return
   */
  fun isNetConnected(paramContext: Context): Boolean {
    val i = false
    val localNetworkInfo = (paramContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    return if (localNetworkInfo != null && localNetworkInfo.isAvailable) true else false
  }

  /**
   * 检测wifi是否连接
   */
  fun isWifiConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null) {
      val networkInfo = cm.activeNetworkInfo
      if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
        return true
      }
    }
    return false
  }

  /**
   * 检测3G是否连接
   */
  fun is3gConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null) {
      val networkInfo = cm.activeNetworkInfo
      if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
        return true
      }
    }
    return false
  }

  /**
   * 判断网址是否有效
   */
  fun isLinkAvailable(link: String): Boolean {
    val pattern = Pattern.compile("^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$", Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(link)
    return if (matcher.matches()) {
      true
    } else false
  }
}

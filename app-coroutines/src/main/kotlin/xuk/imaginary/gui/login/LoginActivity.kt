package xuk.imaginary.gui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import xuk.imaginary.R
import xuk.imaginary.common.Constants
import xuk.imaginary.common.getSpValue
import xuk.imaginary.gui.gank.MainActivity

/**
 * 获取权限
 */
private const val REQUEST_CODE_PERMISSIONS = 101

private const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"
private const val MAX_NUMBER_REQUEST_PERMISSIONS = 2

class LoginActivity : AppCompatActivity() {

  private val permissions = listOf(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  )

  private var permissionRequestCount: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login_activity)

    savedInstanceState?.let {
      permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
    }

    val isFromMain: Boolean =
        if (intent == null) false else intent.getBooleanExtra("fromMain", false)

    if (!isFromMain &&
        (!getSpValue(Constants.SP_LOGIN_TIP, Constants.SP_LOGIN_TIP_DEFAULT))
    ) {
      startActivity(Intent(this, MainActivity::class.java))

      finish()
    }

    // 获取权限
    requestPermissionsIfNecessary()

  }

  /**
   * Request permissions twice - if the user denies twice then show a toast about how to update
   * the permission for storage. Also disable the button if we don't have access to pictures on
   * the device.
   */
  private fun requestPermissionsIfNecessary() {
    if (!checkAllPermissions()) {
      if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
        permissionRequestCount += 1
        ActivityCompat.requestPermissions(
            this,
            permissions.toTypedArray(),
            REQUEST_CODE_PERMISSIONS
        )
      } else {
        Toast.makeText(
            this,
            R.string.set_permissions_in_settings,
            Toast.LENGTH_LONG
        ).show()
        // TODO 解决权限问题
      }
    }
  }

  /** Permission Checking  */
  private fun checkAllPermissions(): Boolean {
    var hasPermissions = true
    for (permission in permissions) {
      hasPermissions = hasPermissions and isPermissionGranted(permission)
    }
    return hasPermissions
  }

  private fun isPermissionGranted(permission: String) =
      ContextCompat.checkSelfPermission(this, permission) ==
          PackageManager.PERMISSION_GRANTED

  override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<String>,
      grantResults: IntArray
  ) {

    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      requestPermissionsIfNecessary() // no-op if permissions are granted already.
    }
  }


}

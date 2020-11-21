package xuk.imaginary.gui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import xuk.imaginary.R
import xuk.imaginary.common.Constants
import xuk.imaginary.common.getSpValue
import xuk.imaginary.gui.gank.MainActivity

class LoginActivity : AppCompatActivity() {

  private val permissions = listOf(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login_activity)

    val isFromMain: Boolean =
        if (intent == null) false else intent.getBooleanExtra("fromMain", false)

    if (!isFromMain &&
        (!getSpValue(Constants.SP_LOGIN_TIP, Constants.SP_LOGIN_TIP_DEFAULT))
    ) {
      startActivity(Intent(this, MainActivity::class.java))

      finish()
    }

    if (checkSelfPermission()) {
      // do something
    }

  }

  private fun checkSelfPermission(): Boolean {
    val permissionList: MutableList<String> = ArrayList()
    for (permission in permissions) {
      if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
        permissionList.add(permission)
      }
    }
    if (permissionList.isNotEmpty()) {
      val permissionsArr = permissionList.toTypedArray()
      ActivityCompat.requestPermissions(this, permissionsArr, 101)
      return false
    }
    return true
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      grantResults.forEach {
        if (it != PackageManager.PERMISSION_GRANTED) {
          // Permission Denied
          finish()
        }
      }
    }
  }

  companion object {
    private const val REQUEST_CODE_PERMISSIONS = 101
  }


}

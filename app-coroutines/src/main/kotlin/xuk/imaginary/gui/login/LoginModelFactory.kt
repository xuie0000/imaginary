package xuk.imaginary.gui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xuk.imaginary.data.UserRepository

class LoginModelFactory(
    private val repository: UserRepository
    , private val context: Context
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return LoginModel(repository) as T
  }
}
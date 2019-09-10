package xuk.imaginary.gui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xuk.imaginary.data.UserRepository

class RegisterModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return RegisterModel(repository) as T
  }
}
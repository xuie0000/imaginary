package xuk.imaginary.gui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xuk.imaginary.data.UserRepository

class RegisterModel constructor(
    private val repository: UserRepository
) : ViewModel() {

  val n = MutableLiveData("")
  val p = MutableLiveData("")
  val mail = MutableLiveData("")

  /**
   * 用户名改变回调的函数
   */
  fun onNameChanged(s: CharSequence) {
    //n.set(s.toString())
    n.value = s.toString()
  }

  /**
   * 邮箱改变的时候
   */
  fun onEmailChanged(s: CharSequence) {
    //n.set(s.toString())
    mail.value = s.toString()
  }

  /**
   * 密码改变的回调函数
   */
  fun onPwdChanged(s: CharSequence) {
    //p.set(s.toString())
    p.value = s.toString()
  }

  fun register() {
    viewModelScope.launch {
      repository.register(mail.value!!, n.value!!, p.value!!)
    }
  }
}

class RegisterModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return RegisterModel(repository) as T
  }
}
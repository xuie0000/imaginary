package xuk.imaginary.gui.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xuk.imaginary.common.SimpleWatcher
import xuk.imaginary.data.UserRepository
import xuk.imaginary.data.db.User

class LoginModel constructor(
    private val repository: UserRepository
) : ViewModel() {

  val n = MutableLiveData<String>("")
  val p = MutableLiveData<String>("")

  /**
   * 用户名改变回调的函数
   */
  fun onNameChanged(s: CharSequence) {
    //n.set(s.toString())
    n.value = s.toString()
  }

  /**
   * 密码改变的回调函数
   */
  fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    //p.set(s.toString())
    p.value = s.toString()
  }

  fun login(): LiveData<User?>? {
    val pwd = p.value!!
    val account = n.value!!
    return repository.login(account, pwd)
  }

  // SimpleWatcher 是简化了的TextWatcher
  val nameWatcher = object : SimpleWatcher() {
    override fun afterTextChanged(s: Editable) {
      super.afterTextChanged(s)

      n.value = s.toString()
    }
  }

  val pwdWatcher = object : SimpleWatcher() {
    override fun afterTextChanged(s: Editable) {
      super.afterTextChanged(s)

      //p.set(s.toString())
      p.value = s.toString()
    }
  }
}
package xuk.imaginary.gui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import xuk.imaginary.common.Constants
import xuk.imaginary.common.InjectorUtils
import xuk.imaginary.common.putSpValue
import xuk.imaginary.databinding.LoginFragmentBinding
import xuk.imaginary.gui.gank.MainActivity

/**
 * 登录的Fragment
 *
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

  private val loginModel: LoginModel by viewModels {
    InjectorUtils.providerLoginModelFactory(requireContext())
  }
  private var isEnable: Boolean = false

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    // TODO 研究DataBindComponent
    /*val binding: LoginFragmentBinding = DataBindingUtil.inflate(
        inflater
        , R.layout.login_fragment
        , container
        , false
    )*/
    // 生成Binding的另外一种方式
    val binding = LoginFragmentBinding.inflate(
        inflater
        , container
        , false
    )
    onSubscribeUi(binding)
    return binding.root
  }

  private fun onSubscribeUi(binding: LoginFragmentBinding) {
    binding.model = loginModel
    binding.isEnable = isEnable
    binding.activity = activity

    binding.btnLogin.setOnClickListener {
      loginModel.login()?.observe(this, Observer { user ->
        if (user == null) {
          Toast.makeText(context, "输入账号或密码错误，请重新输入！", Toast.LENGTH_SHORT).show()
          return@Observer
        }

        activity!!.putSpValue(Constants.SP_USER_ID, user.id)
        activity!!.putSpValue(Constants.SP_USER_NAME, user.account)
        val intent = Intent(context, MainActivity::class.java)
        context!!.startActivity(intent)
        activity!!.finish()
        Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
      })
    }

    loginModel.p.observe(viewLifecycleOwner, Observer {
      // 保证账号和密码不为空的时候才可以登录
      binding.isEnable = it.isNotEmpty() && loginModel.n.value!!.isNotEmpty()
    })

    val name = arguments?.getString(Constants.ARGS_NAME)
    if (!TextUtils.isEmpty(name))
      loginModel.n.value = name!!
  }
}

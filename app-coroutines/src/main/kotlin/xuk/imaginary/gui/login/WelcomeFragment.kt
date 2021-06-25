package xuk.imaginary.gui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.welcome_fragment.*
import xuk.imaginary.R
import xuk.imaginary.common.Constants
import xuk.imaginary.common.getSpValue
import xuk.imaginary.common.putSpValue
import xuk.imaginary.gui.gank.MainActivity

/**
 * 欢迎界面
 */
class WelcomeFragment : Fragment(R.layout.welcome_fragment) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btn_login.setOnClickListener {
      // 设置动画参数
      val navOption = navOptions {
        anim {
          enter = R.anim.common_slide_in_right
          exit = R.anim.common_slide_out_left
          popEnter = R.anim.common_slide_in_left
          popExit = R.anim.common_slide_out_right
        }
      }

      val name = activity!!.getSpValue(Constants.SP_USER_NAME, Constants.USER_NAME)
      // Navigation 传递参数
      val bundle = Bundle()
      bundle.putString(Constants.ARGS_NAME, name)
      findNavController().navigate(R.id.login, bundle, navOption)
    }

    btn_register.setOnClickListener {
      // 利用SafeArgs传递参数
      val action = WelcomeFragmentDirections
          .actionWelcomeToRegister()
          .setEMAIL("xuie0000@163.com")
      findNavController().navigate(action)
    }

    btnSkip.setOnClickListener {
      with(activity!!) {
        if (getSpValue(Constants.SP_LOGIN_TIP, Constants.SP_LOGIN_TIP_DEFAULT)) {
          MaterialAlertDialogBuilder(this)
              .setTitle("跳过？")
              .setMessage("下次是否不再显示登录界面？")
              .setPositiveButton("YES") { _, _ ->
                putSpValue(Constants.SP_LOGIN_TIP, false)
                jumpToMain()
              }
              .setNegativeButton("CANCEL") { _, _ ->
                jumpToMain()
              }
              .show()
        } else {
          jumpToMain()
        }
      }

    }
  }

  private fun jumpToMain() {
    startActivity(Intent(context, MainActivity::class.java))
    activity?.finish()
  }


}

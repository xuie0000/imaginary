package xuk.imaginary.gui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import xuk.imaginary.R
import xuk.imaginary.common.Constants
import xuk.imaginary.common.InjectorUtils
import xuk.imaginary.databinding.RegisterFragmentBinding

/**
 * 注册的Fragment
 *
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

  private var isEnable: Boolean = false
  private val registerModel: RegisterModel by viewModels {
    InjectorUtils.providerRegisterModelFactory(requireContext())
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val binding: RegisterFragmentBinding = DataBindingUtil.inflate(
        inflater
        , R.layout.register_fragment
        , container
        , false
    )

    initData(binding)
    onSubscribeUi(binding)
    return binding.root
  }

  private fun initData(binding: RegisterFragmentBinding) {
    // SafeArgs的使用
    val safeArgs: RegisterFragmentArgs by navArgs()
    val email = safeArgs.email
    binding.model?.mail?.value = email

    binding.model = registerModel
    binding.isEnable = isEnable
    binding.activity = activity
  }

  private fun onSubscribeUi(binding: RegisterFragmentBinding) {
    binding.btnRegister.setOnClickListener {
      registerModel.register()
      val bundle = Bundle()
      bundle.putString(Constants.ARGS_NAME, registerModel.n.value)
      findNavController().navigate(R.id.login, bundle, null)
    }

    registerModel.p.observe(viewLifecycleOwner, Observer {
      binding.isEnable = it.isNotEmpty()
          && registerModel.n.value!!.isNotEmpty()
          && registerModel.mail.value!!.isNotEmpty()
    })

    registerModel.n.observe(viewLifecycleOwner, Observer {
      binding.isEnable = it.isNotEmpty()
          && registerModel.p.value!!.isNotEmpty()
          && registerModel.mail.value!!.isNotEmpty()
    })

    registerModel.mail.observe(viewLifecycleOwner, Observer {
      binding.isEnable = it.isNotEmpty()
          && registerModel.n.value!!.isNotEmpty()
          && registerModel.p.value!!.isNotEmpty()
    })
  }
}

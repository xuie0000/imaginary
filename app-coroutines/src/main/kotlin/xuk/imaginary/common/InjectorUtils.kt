package xuk.imaginary.common

import android.content.Context
import xuk.imaginary.data.AppDataBase
import xuk.imaginary.data.UserRepository
import xuk.imaginary.gui.login.LoginModelFactory
import xuk.imaginary.gui.login.RegisterModelFactory

/**
 * @author Jie Xu
 * @date 2019/9/10
 */

object InjectorUtils {
  /**
   * 得到用户仓库
   */
  private fun providerUserRepository(context: Context): UserRepository {
    return UserRepository.getInstance(AppDataBase.getInstance(context).userDao())
  }

  fun providerRegisterModel(context: Context): RegisterModelFactory {
    return RegisterModelFactory(providerUserRepository(context))
  }

  fun providerLoginModel(context: Context): LoginModelFactory {
    return LoginModelFactory(providerUserRepository(context), context)
  }
}
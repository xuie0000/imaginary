package xuk.imaginary.data

/**
 * @author Jie Xu
 * @date 2019/8/9
 */
sealed class Action

data class SelectPage(val page: Int, val refresh: Boolean) : Action()

data class SelectDate(val date: String) : Action()

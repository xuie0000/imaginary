package xuk.imaginary.gui.gank.show

import android.content.Intent
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Jie Xu
 * @date 2019/8/23
 */
@RunWith(AndroidJUnit4::class)
//@LargeTest
class GkActivityTest {

    @Rule
    @JvmField
    var activityTestRule = object : ActivityTestRule<GkActivity>(GkActivity::class.java) {
        override fun getActivityIntent(): Intent {
            return Intent().apply {
                putExtra("date", "2019/04/10")
                putExtra("image", "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg")
            }
        }
    }

    @Before
    fun jumpToGkActivity() {
    }

    @Test
    fun activeTask_DisplayedInUi() {
        Log.d("Test2", "activeTask_DisplayedInUi")
//        activityTestRule.launchActivity(Intent().putExtra("date", "2019/04/10")
//                .putExtra("image", "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg"))
    }

}
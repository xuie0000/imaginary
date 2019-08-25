package xuk.imaginary

import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xuk.imaginary.gui.gank.show.GkActivity

/**
 * @author Jie Xu
 * @date 2019/8/23
 */
class GkActivityTest {

  @Rule
  @JvmField
  var activityTestRule = ActivityTestRule(GkActivity::class.java)
//  var activityTestRule = IntentsTestRule(GkActivity::class.java)


  @Before
  fun jumpToGkActivity() {
    val startIntent = Intent().apply {
      putExtra("date", "2019/04/10")
      putExtra("image", "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg")
    }
    activityTestRule.launchActivity(startIntent)
  }

  @Test
  fun activeTask_DisplayedInUi() {
    val startIntent = Intent().apply {
      putExtra("date", "2019/04/10")
      putExtra("image", "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg")
    }
    activityTestRule.launchActivity(startIntent)
  }

}
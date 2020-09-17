/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package xuk.imaginary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * @author Jie Xu
 */
class ScrollAwareFabBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                   directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
    // Ensure we react to vertical scrolling
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
  }


  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                              target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                              dyUnconsumed: Int) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
      child.hide()
    } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
      child.show()
    }
  }
}

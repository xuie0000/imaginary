package com.xuie.imaginaryandroid.gui.gank.show

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.github.chrisbanes.photoview.PhotoView
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.BaseBean
import com.xuie.imaginaryandroid.data.GankBean
import com.xuie.imaginaryandroid.data.source.GankRepository
import com.xuie.imaginaryandroid.glide.GlideApp
import java.util.*

class GankActivity : AppCompatActivity(), GankContract.View {
    override lateinit var presenter: GankContract.Presenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var gankDaily: PhotoView

    private var date: String? = null
    private val adapter = ExpandableItemAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gank)

        recyclerView = findViewById(R.id.recycler_view)
        gankDaily = findViewById(R.id.gank_daily)

        date = intent.getStringExtra("date")
        val imageUrl = intent.getStringExtra("image")
        GlideApp.with(this).load(imageUrl).into(gankDaily)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        GankPresenter(GankRepository.instance, this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getGank(date!!)
    }


    override fun onResume() {
        super.onResume()
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun refresh(gb: GankBean) {
        Log.d(TAG, gb.toString())
        val entities = generateData(gb)
        adapter.replaceData(ArrayList())
        adapter.addData(entities)
        adapter.expandAll()
    }

    private fun generateData(gb: GankBean): List<MultiItemEntity> {
        val entities = ArrayList<MultiItemEntity>()
        Log.d(TAG, "category: " + gb.category.toString())
        for (s in gb.category!!) {
            val lv0 = Level0Item()
            lv0.type = s
            var bbs: List<BaseBean>?
            if (s == "Android") {
                bbs = gb.results!!.Android
            } else if (s == "瞎推荐") {
                bbs = gb.results!!.瞎推荐
            } else if (s == "iOS") {
                bbs = gb.results!!.iOS
            } else if (s == "休息视频") {
                bbs = gb.results!!.休息视频
            } else if (s == "福利") {
//                bbs = gb.results!!.福利
                continue
            } else if (s == "前端") {
                bbs = gb.results!!.前端
            } else if (s == "拓展资源") {
                bbs = gb.results!!.拓展资源
            } else if (s == "App") {
                bbs = gb.results!!.App
            } else {
                Log.e(TAG, "$s!!!!!")
                continue
            }
            for (ab in bbs!!) {
                val lv1 = Level1Item()
                lv1.articleName = ab.desc
                lv1.articleUrl = ab.url
                lv1.author = ab.who

                if (ab.images != null && ab.images!!.isNotEmpty())
                    lv1.imageUrl = ab.images!![0]
                lv0.addSubItem(lv1)
            }
            entities.add(lv0)
        }
        return entities
    }

    companion object {
        private var TAG = "GankActivity"
    }
}

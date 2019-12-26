package com.jscoolstar.accountremeber.activities.settings.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.jscoolstar.accountremeber.R
import kotlinx.android.synthetic.main.act_mine.*

class MineAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS )
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_mine)

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> {
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                var judgeMax = 0.6f
                if (Math.abs(p1) >= p0!!.totalScrollRange * judgeMax) {//折叠状态
                    llt_toolbar2.visibility = View.VISIBLE
                    toolbar1.visibility = View.GONE
                    var total4Count = p0!!.totalScrollRange * (1 - judgeMax)
                    var dt = p0!!.totalScrollRange - Math.abs(p1)
                    var result = dt * 1.0f / total4Count
                    llt_toolbar2.alpha = 1 - result
                } else {
                    llt_toolbar2.visibility = View.GONE
                    toolbar1.visibility = View.VISIBLE
                    toolbar2.title = ""
                }
            }
        })
    }
}
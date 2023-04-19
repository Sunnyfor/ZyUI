package com.sunny.zyui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zyui.fun1.SlideSwitchButtonActivity
import java.util.*

/**
 * Desc 功能区
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021/05/26 17:35
 */
class MainActivity : AppCompatActivity() {

    private var actionList = ArrayList<ActionBean>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val recyclerView = RecyclerView(this)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        actionList.clear()
//        actionList.add(fun1SlideActivity())



    }


    private fun fun1SlideActivity(): ActionBean {
        val action = ActionBean()
        action.title = "F1-自定义滑动解锁View"
        action.iconRes = R.mipmap.ic_launcher
        action.onClickListener = View.OnClickListener {
            startActivity(Intent(this, SlideSwitchButtonActivity::class.java))
        }
        return action
    }

}
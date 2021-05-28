package com.sunny.zyui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zyui.fun1.SlideSwitchButtonActivity
import java.util.ArrayList

/**
 * Desc 功能区
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021/05/26 17:35
 */
class MainActivity : AppCompatActivity() {

    private var actionList = ArrayList<ActionBean>()

    private val adapter: ActionAdapter by lazy {
        ActionAdapter(this, actionList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = RecyclerView(this)
        setContentView(recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        actionList.clear()
        actionList.add(loadCameraAction())
        actionList.add(loadPhotoAction())
        actionList.add(loadFileAction())

        adapter.notifyDataSetChanged()

    }


    private fun loadCameraAction(): ActionBean {
        val cameraAction = ActionBean()
        cameraAction.iconRes = R.mipmap.ic_launcher
        cameraAction.title = "相机"
        cameraAction.onClickListener = View.OnClickListener {
            Toast.makeText(this, "相机", Toast.LENGTH_SHORT).show()
        }
        return cameraAction
    }


    private fun loadPhotoAction(): ActionBean {
        val photoAction = ActionBean()
        photoAction.iconRes = R.mipmap.ic_launcher
        photoAction.title = "相册"
        photoAction.onClickListener = View.OnClickListener {

        }
        return photoAction
    }


    private fun loadFileAction(): ActionBean {
        val fileAction = ActionBean()
        fileAction.iconRes = R.mipmap.ic_launcher
        fileAction.title = "文件"
        fileAction.onClickListener = View.OnClickListener {

        }
        return fileAction
    }
}
package com.sunny.zyui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Desc 功能选项子条目
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2019/12/20 11:39
 */
class ActionAdapter(var context: Context, var list: ArrayList<ActionBean>) :
    RecyclerView.Adapter<ActionAdapter.ActionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        return ActionHolder(
            LayoutInflater.from(context).inflate(R.layout.item_action, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.title.text = list[position].title
        holder.image.setImageResource(list[position].iconRes)
        holder.itemView.setOnClickListener {
            list[position].onClickListener?.onClick(it)
        }
    }


    class ActionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textView)
        val image: ImageView = view.findViewById(R.id.imageView)
    }

}
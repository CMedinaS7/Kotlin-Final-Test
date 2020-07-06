package com.example.kotlinfinaltest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.model.UserModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_template.view.*

class FriendAdapter (private var data: List<UserModel>, private val listener: UserHolder.OnAdapterListener) :
    RecyclerView.Adapter<FriendAdapter.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflatedView = parent.inflate(R.layout.activity_user_template, false)
        return UserHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(userList: List<UserModel>) {
        this.data = userList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user: UserModel = this.data[position]
        holder.itemView.user_name.text = user.name + ' ' + user.lastname

        if (!user.image.isBlank()) {
            Picasso.get()
                .load(user.image)
                .into(holder.itemView.user_photo)
        }
        holder.itemView.container_user.setOnClickListener { listener.onItemClickListener(user) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class UserHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: UserModel )
        }

    }
}

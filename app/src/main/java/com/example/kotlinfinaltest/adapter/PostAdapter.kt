package com.example.kotlinfinaltest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.model.CommentModel
import com.example.kotlinfinaltest.model.PostModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_template.view.*

class PostAdapter(private var data: List<PostModel>, private val listener: PostHolder.OnAdapterListener) :
    RecyclerView.Adapter<PostAdapter.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflatedView = parent.inflate(R.layout.activity_post_template, false)
        return PostHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(postList: List<PostModel>) {
        this.data = postList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post: PostModel = this.data[position]
        holder.itemView.post_username.text = post.username
        holder.itemView.post_text.text = post.body
        holder.itemView.tv_feed_item_likes.text = post.likes.toString()

        if(!post.user_image.isBlank()) {
            Picasso.get()
                .load(post.user_image)
                .into(holder.itemView.post_photo_user)
        }
        if(!post.image.isBlank()) {
            Picasso.get()
                .load(post.image)
                .into(holder.itemView.post_photo)
        }
        holder.itemView.btn_feed_item_comment.setOnClickListener { listener.onItemClickListener(post) }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class PostHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: PostModel)
        }

    }
}
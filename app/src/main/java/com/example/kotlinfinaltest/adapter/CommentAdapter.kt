package com.example.kotlinfinaltest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.model.CommentModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comment_template.view.*

class CommentAdapter  (private var data: List<CommentModel>, private val listener: CommentHolder.OnAdapterListener) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflatedView = parent.inflate(R.layout.activity_comment_template, false)
        return CommentHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(commentList: List<CommentModel>) {
        this.data = commentList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val comment: CommentModel = this.data[position]
        holder.itemView.comment_text.text = comment.comment

        if (!comment.user_image.isBlank()) {
            Picasso.get()
                .load(comment.user_image)
                .into(holder.itemView.comment_userphoto)
        }
        holder.itemView.setOnClickListener { listener.onItemClickListener(comment) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CommentHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: CommentModel)
        }

    }
}

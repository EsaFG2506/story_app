package com.kikilidyaaaa.storyapp.view

import android.app.Activity
import android.content.Intent
import android.view.*
import android.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kikilidyaaaa.storyapp.R
import com.kikilidyaaaa.storyapp.data.response.ListStoryItem
import com.kikilidyaaaa.storyapp.view.detail.DetailActivity
import com.squareup.picasso.Picasso

class StoryAdapter: ListAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        private val imgPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(story: ListStoryItem) {
            tvName.text = story.name
            Picasso.get().load(story.photoUrl).into(imgPhoto)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("id", story.id)
                intent.putExtra("name", story.name)
                intent.putExtra("photoUrl", story.photoUrl)
                intent.putExtra("desc", story.description)

                Toast.makeText(itemView.context,story.name + " dipilih", Toast.LENGTH_SHORT).show()

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgPhoto, "profile"),
                        Pair(tvName, "name"),
                        Pair(tvDesc, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
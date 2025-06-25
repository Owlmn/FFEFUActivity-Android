package ru.app.fefuactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R
import ru.app.fefuactivity.model.UserActivityDisplay

sealed class ActivityListItem {
    data class Section(val title: String) : ActivityListItem()
    data class ActivityItem(val activity: UserActivityDisplay) : ActivityListItem()
}

class SectionedActivityAdapter(
    private var items: List<ActivityListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_SECTION = 0
        private const val TYPE_ACTIVITY = 1
    }
    private var onItemClickListener: ((UserActivityDisplay) -> Unit)? = null
    
    fun setOnItemClickListener(listener: (UserActivityDisplay) -> Unit) {
        onItemClickListener = listener
    }
    
    fun updateItems(newItems: List<ActivityListItem>) {
        items = newItems
        notifyDataSetChanged()
    }
    
    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is ActivityListItem.Section -> TYPE_SECTION
        is ActivityListItem.ActivityItem -> TYPE_ACTIVITY
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SECTION -> {
                val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
                SectionViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
                UserActivityViewHolder(view)
            }
        }
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityListItem.Section -> (holder as SectionViewHolder).bind(item.title)
            is ActivityListItem.ActivityItem -> {
                (holder as UserActivityViewHolder).bind(item.activity)
                holder.itemView.setOnClickListener { onItemClickListener?.invoke(item.activity) }
            }
        }
    }
    
    override fun getItemCount(): Int = items.size
}

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(title: String) {
        (itemView as? android.widget.TextView)?.apply {
            text = title
            textSize = 24f
            setTextColor(itemView.context.getColor(R.color.dark_grey))
        }
    }
}

class UserActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: UserActivityDisplay) {
        itemView.findViewById<android.widget.TextView>(R.id.distance).text = item.distance
        itemView.findViewById<android.widget.TextView>(R.id.username).text = item.username
        itemView.findViewById<android.widget.TextView>(R.id.time).text = item.time
        itemView.findViewById<android.widget.TextView>(R.id.type).text = item.type
        itemView.findViewById<android.widget.TextView>(R.id.date).text = item.date
    }
} 
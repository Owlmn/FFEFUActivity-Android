package ru.app.fefuactivity.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R
import java.time.format.TextStyle

sealed class ActivityListItem {
    data class Section(val title: String) : ActivityListItem()
    data class ActivityItem(val activity: UserActivity) : ActivityListItem()
}

data class UserActivity(
    val distance: String,
    val username: String,
    val time: String,
    val type: String,
    val date: String,
    val duration: String? = null,
    val start: String? = null,
    val finish: String? = null,
    val comment: String? = null
)

class SectionedActivityAdapter(
    private val items: List<ActivityListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_SECTION = 0
        private const val TYPE_ACTIVITY = 1
    }
    private var onItemClickListener: ((UserActivity) -> Unit)? = null
    fun setOnItemClickListener(listener: (UserActivity) -> Unit) {
        onItemClickListener = listener
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
    fun bind(item: UserActivity) {
        itemView.findViewById<android.widget.TextView>(R.id.distance).text = item.distance
        itemView.findViewById<android.widget.TextView>(R.id.username).text = item.username
        itemView.findViewById<android.widget.TextView>(R.id.time).text = item.time
        itemView.findViewById<android.widget.TextView>(R.id.type).text = item.type
        itemView.findViewById<android.widget.TextView>(R.id.date).text = item.date
    }
}

class UsersActivityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val items = listOf(
            ActivityListItem.Section("Вчера"),
            ActivityListItem.ActivityItem(
                UserActivity(
                    distance = "14.32 км",
                    username = "@van_darkholme",
                    time = "2 часа 46 минут",
                    type = "Серфинг",
                    date = "14 часов назад",
                    duration = "2 часа 46 минут",
                    start = "14:49",
                    finish = "17:35",
                    comment = "Поймал отличную волну!"
                )
            ),
            ActivityListItem.Section("Май 2022 года"),
            ActivityListItem.ActivityItem(
                UserActivity(
                    distance = "1 000 м",
                    username = "@techniquepasha",
                    time = "60 минут",
                    type = "Велосипед",
                    date = "29.05.2022",
                    duration = "60 минут",
                    start = "15:00",
                    finish = "16:00",
                    comment = "Катался с друзьями во дворе."
                )
            )
        )
        val adapter = SectionedActivityAdapter(items)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { item ->
            val fragment = UsersDetailsFragment.newInstance(
                type = item.type,
                username = item.username,
                distance = item.distance,
                date = item.date,
                duration = item.duration ?: "",
                start = item.start ?: "",
                finish = item.finish ?: "",
                comment = item.comment ?: ""
            )
            (requireActivity() as? ru.app.fefuactivity.activity.MainScreenActivity)?.showDetailsFragment(fragment)
        }
    }
} 
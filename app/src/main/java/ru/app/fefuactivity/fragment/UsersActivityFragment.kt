package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R
import ru.app.fefuactivity.adapter.SectionedActivityAdapter
import ru.app.fefuactivity.adapter.ActivityListItem
import ru.app.fefuactivity.model.UserActivityDisplay

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
                UserActivityDisplay(
                    distance = "14.32 км",
                    username = "@van_darkholme",
                    time = "2 часа 46 минут",
                    type = "Серфинг",
                    date = "14 часов назад",
                    comment = "Поймал отличную волну!"
                )
            ),
            ActivityListItem.ActivityItem(
                UserActivityDisplay(
                    distance = "228 м",
                    username = "@techniquepasha",
                    time = "14 часов 48 минут",
                    type = "Качели",
                    date = "14 часов назад",
                    comment = "Накачал себе настроение!"
                )
            ),
            ActivityListItem.ActivityItem(
                UserActivityDisplay(
                    distance = "10 км",
                    username = "@morgen_shtern",
                    time = "1 час 10 минут",
                    type = "Езда на кадилак",
                    date = "14 часов назад",
                    comment = "Катался с ветерком по району."
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
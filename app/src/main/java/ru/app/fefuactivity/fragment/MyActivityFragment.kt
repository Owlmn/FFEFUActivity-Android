package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R

class MyActivityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.myActivityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val items = listOf(
            ActivityListItem.Section("Вчера"),
            ActivityListItem.ActivityItem(
                UserActivity(
                    distance = "14.32 км",
                    username = "",
                    time = "2 часа 46 минут",
                    type = "Серфинг",
                    date = "14 часов назад",
                    duration = "2 часа 46 минут",
                    start = "14:49",
                    finish = "17:35"
                )
            ),
            ActivityListItem.Section("Май 2022 года"),
            ActivityListItem.ActivityItem(
                UserActivity(
                    distance = "1 000 м",
                    username = "",
                    time = "60 минут",
                    type = "Велосипед",
                    date = "29.05.2022",
                    duration = "60 минут",
                    start = "15:00",
                    finish = "16:00"
                )
            )
        )
        val adapter = SectionedActivityAdapter(items)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { item ->
            val fragment = MyDetailsFragment.newInstance(
                type = item.type,
                distance = item.distance,
                date = item.date,
                duration = item.duration ?: "",
                start = item.start ?: "",
                finish = item.finish ?: ""
            )
            (requireActivity() as? ru.app.fefuactivity.activity.MainScreenActivity)?.showDetailsFragment(fragment)
        }
    }
} 
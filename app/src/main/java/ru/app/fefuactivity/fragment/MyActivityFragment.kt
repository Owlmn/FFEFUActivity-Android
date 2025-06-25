package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R
import androidx.lifecycle.ViewModelProvider
import ru.app.fefuactivity.viewmodel.UserActivityViewModel
import ru.app.fefuactivity.data.UserActivity as DbUserActivity
import ru.app.fefuactivity.data.Coordinate
import ru.app.fefuactivity.adapter.SectionedActivityAdapter
import ru.app.fefuactivity.adapter.ActivityListItem
import ru.app.fefuactivity.model.UserActivityDisplay
import java.text.SimpleDateFormat
import java.util.*

class MyActivityFragment : Fragment() {
    private lateinit var viewModel: UserActivityViewModel
    private lateinit var adapter: SectionedActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
        
        val recyclerView = view.findViewById<RecyclerView>(R.id.myActivityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        adapter = SectionedActivityAdapter(emptyList())
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
        
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            val items = if (activities.isEmpty()) {
                getDefaultItems()
            } else {
                convertToDisplayItems(activities)
            }
            adapter.updateItems(items)
        }
    }
    
    private fun getDefaultItems(): List<ActivityListItem> {
        return listOf(
            ActivityListItem.Section("Вчера"),
            ActivityListItem.ActivityItem(
                UserActivityDisplay(
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
                UserActivityDisplay(
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
    }
    
    private fun convertToDisplayItems(activities: List<DbUserActivity>): List<ActivityListItem> {
        val items = mutableListOf<ActivityListItem>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        
        val groupedActivities = activities.groupBy { 
            dateFormat.format(it.startTime) 
        }.toSortedMap(compareByDescending { it })
        
        groupedActivities.forEach { (date, dayActivities) ->
            items.add(ActivityListItem.Section(date))
            
            dayActivities.forEach { activity ->
                val duration = (activity.endTime.time - activity.startTime.time) / 60000 // в минутах
                val distance = calculateDistance(activity.coordinates)
                
                items.add(ActivityListItem.ActivityItem(
                    UserActivityDisplay(
                        distance = "${String.format("%.2f", distance)} км",
                        username = "",
                        time = formatDuration(duration),
                        type = when (activity.type) {
                            ru.app.fefuactivity.data.ActivityType.BICYCLE -> "Велосипед"
                            ru.app.fefuactivity.data.ActivityType.RUNNING -> "Бег"
                            ru.app.fefuactivity.data.ActivityType.WALKING -> "Шаг"
                        },
                        date = date,
                        duration = formatDuration(duration),
                        start = timeFormat.format(activity.startTime),
                        finish = timeFormat.format(activity.endTime)
                    )
                ))
            }
        }
        
        return items
    }
    
    private fun calculateDistance(coordinates: List<Coordinate>): Double {
        if (coordinates.size < 2) return 0.0
        
        var totalDistance = 0.0
        for (i in 0 until coordinates.size - 1) {
            val coord1 = coordinates[i]
            val coord2 = coordinates[i + 1]
            totalDistance += calculateDistanceBetweenPoints(coord1, coord2)
        }
        
        return totalDistance
    }
    
    private fun calculateDistanceBetweenPoints(coord1: Coordinate, coord2: Coordinate): Double {
        val r = 6371
        val lat1 = Math.toRadians(coord1.latitude)
        val lat2 = Math.toRadians(coord2.latitude)
        val deltaLat = Math.toRadians(coord2.latitude - coord1.latitude)
        val deltaLng = Math.toRadians(coord2.longitude - coord1.longitude)
        
        val a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        
        return r * c
    }
    
    private fun formatDuration(minutes: Long): String {
        val hours = minutes / 60
        val mins = minutes % 60
        
        return when {
            hours > 0 -> "${hours} ч ${mins} мин"
            else -> "${mins} мин"
        }
    }
} 
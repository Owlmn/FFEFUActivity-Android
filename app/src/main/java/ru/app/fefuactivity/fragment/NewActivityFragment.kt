package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.app.fefuactivity.R
import android.widget.ImageView
import android.widget.TextView
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import ru.app.fefuactivity.viewmodel.UserActivityViewModel
import ru.app.fefuactivity.data.*
import java.util.Date
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewActivityFragment : Fragment() {
    private val activityTypes = listOf(
        ActivityTypeDisplay("Велосипед"),
        ActivityTypeDisplay("Бег"),
        ActivityTypeDisplay("Шаг")
    )
    private var selectedType: ActivityTypeDisplay? = null
    private lateinit var viewModel: UserActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
        
        val container = view.findViewById<FrameLayout>(R.id.bottom_block_container)
        val inflater = LayoutInflater.from(requireContext())
        
        val startView = inflater.inflate(R.layout.bottom_block_start, container, false)
        container.addView(startView)
        
        val recycler = startView.findViewById<RecyclerView>(R.id.activity_type_recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ActivityTypeAdapter(activityTypes) { type ->
            selectedType = type
        }
        recycler.adapter = adapter
        selectedType = activityTypes.firstOrNull()
        adapter.setSelectedType(selectedType)
        val spacing = (8 * resources.displayMetrics.density).toInt()
        recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: android.graphics.Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0) outRect.left = spacing
            }
        })
        
        val startButton = startView.findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            val activityType = when (selectedType?.name) {
                "Велосипед" -> ru.app.fefuactivity.data.ActivityType.BICYCLE
                "Бег" -> ru.app.fefuactivity.data.ActivityType.RUNNING
                "Шаг" -> ru.app.fefuactivity.data.ActivityType.WALKING
                else -> ru.app.fefuactivity.data.ActivityType.BICYCLE
            }
            
            val startTime = Date()
            val endTime = Date(startTime.time + Random.nextLong(300000, 7200000)) // 5-120 минут
            
            val coordinates = generateRandomCoordinates()
            
            val userActivity = UserActivity(
                type = activityType,
                startTime = startTime,
                endTime = endTime,
                coordinates = coordinates
            )
            
            viewModel.insertActivity(userActivity)
            
            container.removeAllViews()
            val activeView = inflater.inflate(R.layout.bottom_block_active, container, false)
            container.addView(activeView)
            
            val activityTypeText = activeView.findViewById<TextView>(R.id.activity_type_text)
            activityTypeText.text = selectedType?.name ?: "Велосипед"
            
            val finishButton = activeView.findViewById<ImageView>(R.id.finish_button)
            finishButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        
        view.findViewById<ImageView>(R.id.back_button)?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    
    private fun generateRandomCoordinates(): List<Coordinate> {
        val coordinates = mutableListOf<Coordinate>()
        val baseLat = 43.585472
        val baseLng = 39.723098
        
        for (i in 0..Random.nextInt(10, 50)) {
            val lat = baseLat + Random.nextDouble(-0.01, 0.01)
            val lng = baseLng + Random.nextDouble(-0.01, 0.01)
            coordinates.add(Coordinate(lat, lng))
        }
        
        return coordinates
    }
}

data class ActivityTypeDisplay(val name: String)

class ActivityTypeAdapter(
    private val items: List<ActivityTypeDisplay>,
    private val onSelect: (ActivityTypeDisplay) -> Unit
) : RecyclerView.Adapter<ActivityTypeViewHolder>() {
    private var selected: ActivityTypeDisplay? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_type, parent, false)
        return ActivityTypeViewHolder(view)
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ActivityTypeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, item == selected)
        holder.itemView.setOnClickListener {
            selected = item
            notifyDataSetChanged()
            onSelect(item)
        }
    }
    fun setSelectedType(type: ActivityTypeDisplay?) {
        selected = type
        notifyDataSetChanged()
    }
}

class ActivityTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val icon: ImageView = view.findViewById(R.id.type_icon)
    private val name: TextView = view.findViewById(R.id.type_name)
    fun bind(type: ActivityTypeDisplay, selected: Boolean) {
        icon.setImageResource(R.drawable.pic)
        name.text = type.name
        val bgRes = if (selected) R.drawable.bg_activity_type_item_selected else R.drawable.bg_activity_type_item
        itemView.setBackgroundResource(bgRes)
    }
} 
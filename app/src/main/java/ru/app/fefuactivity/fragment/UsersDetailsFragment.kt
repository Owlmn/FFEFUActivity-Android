package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.app.fefuactivity.R
import android.widget.TextView
import android.widget.ImageView

class UsersDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_users_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        view.findViewById<TextView>(R.id.type).text = args?.getString("type")
        view.findViewById<TextView>(R.id.username).text = args?.getString("username")
        view.findViewById<TextView>(R.id.distance).text = args?.getString("distance")
        view.findViewById<TextView>(R.id.date).text = args?.getString("date")
        view.findViewById<TextView>(R.id.duration).text = args?.getString("duration")
        view.findViewById<TextView>(R.id.start).text = "Старт ${args?.getString("start")}"
        view.findViewById<TextView>(R.id.finish).text = "Финиш ${args?.getString("finish")}"
        view.findViewById<TextView>(R.id.comment).text = args?.getString("comment")
        view.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    companion object {
        fun newInstance(type: String, username: String, distance: String, date: String, duration: String, start: String, finish: String, comment: String): UsersDetailsFragment {
            val fragment = UsersDetailsFragment()
            val args = Bundle()
            args.putString("type", type)
            args.putString("username", username)
            args.putString("distance", distance)
            args.putString("date", date)
            args.putString("duration", duration)
            args.putString("start", start)
            args.putString("finish", finish)
            args.putString("comment", comment)
            fragment.arguments = args
            return fragment
        }
    }
} 
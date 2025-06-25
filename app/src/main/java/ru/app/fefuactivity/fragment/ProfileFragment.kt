package ru.app.fefuactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ru.app.fefuactivity.R
import android.content.Intent
import ru.app.fefuactivity.activity.WelcomeActivity

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changePassword = view.findViewById<TextView>(R.id.change_password)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        changePassword.setOnClickListener {
            (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ChangePasswordFragment())
                addToBackStack(null)
                commit()
            }
            (requireActivity().findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab))?.hide()
        }
        logoutButton.setOnClickListener {
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
} 
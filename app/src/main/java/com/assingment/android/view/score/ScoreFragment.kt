package com.assingment.android.view.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.assingment.android.R
import com.assingment.android.view.NavigationEvent
import com.assingment.android.view.ShareViewModel

class ScoreFragment : Fragment() {

    private val layoutId: Int = R.layout.fragment_score

    private val viewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = view.findViewById<TextView>(R.id.name)
        val scoreText = view.findViewById<TextView>(R.id.score)
        val button = view.findViewById<Button>(R.id.ctaButton)
        name.text = viewModel.name.value

        viewModel.apply {
            navigation.observe(viewLifecycleOwner, ::navigateToDestination)
            score.observe(viewLifecycleOwner, {
                scoreText.text = "$it"
            })
        }
        button.setOnClickListener {
            viewModel.navigateToWelcomeScreen()
        }
    }

    private fun navigateToDestination(navigation: NavigationEvent?) {
        navigation ?: return
        findNavController().navigate(navigation.destination)
    }
}

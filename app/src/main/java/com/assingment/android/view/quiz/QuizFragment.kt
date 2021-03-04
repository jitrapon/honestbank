package com.assingment.android.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.assingment.android.R
import com.assingment.android.view.NavigationEvent
import com.assingment.android.view.ShareViewModel
import com.google.android.material.snackbar.Snackbar

class QuizFragment: Fragment() {

    private val layoutId: Int = R.layout.fragment_quiz
    private val viewModel : QuizViewModel by viewModels()
    private val model: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root = view.findViewById<ConstraintLayout>(R.id.root)
        val button = view.findViewById<Button>(R.id.ctaButton)
        val pager = view.findViewById<ViewPager2>(R.id.pager)
        val next = view.findViewById<Button>(R.id.nextButton)
        val back = view.findViewById<Button>(R.id.backButton)

        button.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_scoreFragment)
        }

        viewModel.apply {
            quizQuestions.observe(viewLifecycleOwner, {
                pager.adapter = QuizAdapter(it, viewModel)
            })
            page.observe(viewLifecycleOwner, {
                pager.setCurrentItem(it, true)
            })
            score.observe(viewLifecycleOwner, {
                model.saveScore(it)
            })
            message.observe(viewLifecycleOwner, {
                it ?: return@observe
                Snackbar.make(root, getString(it.resId), Snackbar.LENGTH_LONG).show()
            })
            navigation.observe(viewLifecycleOwner, ::navigateToDestination)
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setPage(position)
            }
        })

        next.setOnClickListener {
            viewModel.nextPage()
        }

        back.setOnClickListener {
            viewModel.previousPage()
        }

        button.setOnClickListener {
            viewModel.submit()
        }
    }

    private fun navigateToDestination(navigation: NavigationEvent?) {
        navigation ?: return
        findNavController().navigate(navigation.destination)
    }
}

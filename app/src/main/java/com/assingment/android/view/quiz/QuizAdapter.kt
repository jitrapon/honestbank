package com.assingment.android.view.quiz

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assingment.android.R
import com.assingment.android.model.QuizQuestion
import com.assingment.android.view.quiz.QuizViewModel.Companion.NO_CHOICE

class QuizAdapter(
    private val quizQuestions: List<QuizQuestion>,
    private val viewModel: QuizViewModel
) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun getItemCount(): Int = quizQuestions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_quiz, parent, false)

        return ViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, quizQuestions[position])
    }

    class ViewHolder(view: View, private val viewModel: QuizViewModel) :
        RecyclerView.ViewHolder(view) {

        private val question: TextView = view.findViewById(R.id.question)
        private val choices: RadioGroup = view.findViewById(R.id.choices)

        fun bind(questionIndex: Int, quizQuestion: QuizQuestion) {
            question.text = quizQuestion.question
            choices.setOnCheckedChangeListener(null)
            val selectedChoiceIndex = viewModel.getSelectedChoice(questionIndex)
            if (selectedChoiceIndex != NO_CHOICE) {
                (choices.getChildAt(selectedChoiceIndex) as RadioButton).isChecked = true
            } else {
                choices.clearCheck()
            }
            quizQuestion.choices.forEachIndexed { i, q ->
                (choices.getChildAt(i) as RadioButton).text = q
            }
            choices.setOnCheckedChangeListener { radioGroup, i ->
                val checked = radioGroup.findViewById<RadioButton>(i)
                val index = radioGroup.indexOfChild(checked)
                viewModel.selectChoice(questionIndex, index)
            }
        }
    }
}

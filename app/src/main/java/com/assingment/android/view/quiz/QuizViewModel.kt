package com.assingment.android.view.quiz

import android.app.Application
import android.util.Log
import android.util.SparseIntArray
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assingment.android.model.Data
import com.assingment.android.model.QuizQuestion

class QuizViewModel(application: Application): AndroidViewModel(application) {

    val quizQuestions = MutableLiveData<List<QuizQuestion>>()
    var page = 0

    private val choices = SparseIntArray(Data.quizQuestions.size)
    private var score: Int = 0

    companion object {

        const val NO_CHOICE = -1
    }

    init {
        quizQuestions.value = Data.quizQuestions
        for (i in Data.quizQuestions.indices) {
            choices[i] = NO_CHOICE
        }
    }

    fun selectChoice(questionIndex: Int, choiceIndex: Int) {
        choices[questionIndex] = choiceIndex
        val expected = Data.quizQuestions[questionIndex].answer
        val actual = Data.quizQuestions[questionIndex].choices[choiceIndex]
        score = if (expected == actual) score + 1 else score
    }

    fun getSelectedChoice(questionIndex: Int): Int {
        Log.d("quiz", "Question at $questionIndex with answer ${choices[questionIndex]}")
        return choices[questionIndex]
    }
}

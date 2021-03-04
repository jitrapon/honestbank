package com.assingment.android.view.quiz

import android.app.Application
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assingment.android.LiveEvent
import com.assingment.android.R
import com.assingment.android.model.Data
import com.assingment.android.model.QuizQuestion
import com.assingment.android.view.MessageEvent
import com.assingment.android.view.NavigationEvent
import kotlin.math.max
import kotlin.math.min

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    val quizQuestions = MutableLiveData<List<QuizQuestion>>()

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    @VisibleForTesting
    val choices: ArrayList<Int>

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _navigation = LiveEvent<NavigationEvent>()
    val navigation: LiveData<NavigationEvent>
        get() = _navigation

    private val _message = LiveEvent<MessageEvent>()
    val message: LiveData<MessageEvent>
        get() = _message

    companion object {

        const val NO_CHOICE = -1
    }

    init {
        quizQuestions.value = Data.quizQuestions.shuffled()
        choices = ArrayList()
        for (i in Data.quizQuestions.indices) {
            choices.add(NO_CHOICE)
        }
        _score.value = 0
        _page.value = 0
    }

    fun setPage(page: Int) {
        _page.value = page
    }

    fun nextPage() {
        _page.value = min(page.value!! + 1, Data.quizQuestions.size - 1)
    }

    fun previousPage() {
        _page.value = max(page.value!! - 1, 0)
    }

    fun selectChoice(questionIndex: Int, choiceIndex: Int) {
        val lastSelectedIndex = choices[questionIndex]
        choices[questionIndex] = choiceIndex
        val expected = quizQuestions.value!![questionIndex].answer
        val actual = quizQuestions.value!![questionIndex].choices[choiceIndex]
        _score.value = if (expected == actual) {
            score.value!! + 1
        } else {
            if (lastSelectedIndex == quizQuestions.value!![questionIndex].choices.indexOf(expected)) score.value!! - 1
            else score.value!!
        }
    }

    fun getSelectedChoice(questionIndex: Int): Int {
        return choices[questionIndex]
    }

    fun submit() {
        if (choices.any { it == NO_CHOICE }) {
            _message.value = MessageEvent(R.string.error_answer)
        } else {
            _message.value = null
            _navigation.value = NavigationEvent(R.id.action_quizFragment_to_scoreFragment)
        }
    }
}

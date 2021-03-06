package com.assingment.android.view

import android.app.Application
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assingment.android.LiveEvent
import com.assingment.android.R
import com.assingment.android.model.ScoreRepository

class ShareViewModel(application: Application) : AndroidViewModel(application) {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _navigation = LiveEvent<NavigationEvent>()
    val navigation: LiveData<NavigationEvent>
        get() = _navigation

    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?>
        get() = _nameError

    private val scoreRepository: ScoreRepository = ScoreRepository()

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _scoreBoard = MutableLiveData<List<ScoreRank>>()
    val scoreBoard: LiveData<List<ScoreRank>>
        get() = _scoreBoard

    fun onNameInputTextChanged(nameTextInput: CharSequence?) {
        if (!nameTextInput.isNullOrEmpty()) {
            _nameError.value = null
        }
    }

    fun proceedToQuizScreen(nameTextInput: CharSequence) {
        if (nameTextInput.isBlank()) {
            _nameError.value = getApplication<Application>().getString(R.string.error_input_name)
        } else {
            _score.value = 0
            _name.value = nameTextInput.toString()
            _nameError.value = null
            _navigation.value = NavigationEvent(R.id.action_homeFragment_to_quizFragment)
        }
    }

    fun navigateToWelcomeScreen() {
        _navigation.value = NavigationEvent(R.id.action_scoreFragment_to_homeFragment)
    }

    fun saveScore(score: Int) {
        _score.value = score
        val name = _name.value ?: return
        scoreRepository.saveScore(name, score)
    }

    fun getScoreBoard() {
        val scoreBoard = scoreRepository.getScores().map { (name, score) ->
            ScoreRank(0, name, score)
        }.sortedByDescending { it.score }
        scoreBoard.forEachIndexed { index, value ->
            value.rank = index + 1
        }
        _scoreBoard.value = scoreBoard
    }
}

/**
 * A data class that represents an action of navigation
 */
data class NavigationEvent(@IdRes val destination: Int)

/**
 * A data class that represents an action of showing a message
 */
data class MessageEvent(@StringRes val resId: Int)

/**
 * List of scores and ranks
 */
data class ScoreRank(var rank: Int, val name: String, val score: Int)
package com.assingment.android.view

import android.app.Application
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assingment.android.LiveEvent
import com.assingment.android.R

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

    var score = 0

    fun onNameInputTextChanged(nameTextInput: CharSequence?) {
        if (!nameTextInput.isNullOrEmpty()) {
            _nameError.value = null
        }
    }

    fun proceedToQuizScreen(nameTextInput: CharSequence) {
        if (nameTextInput.isBlank()) {
            _nameError.value = getApplication<Application>().getString(R.string.error_input_name)
        } else {
            _name.value = nameTextInput.toString()
            _nameError.value = null
            _navigation.value = NavigationEvent(R.id.action_homeFragment_to_quizFragment)
        }
    }

    fun navigateToWelcomeScreen() {
        _navigation.value = NavigationEvent(R.id.action_scoreFragment_to_homeFragment)
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
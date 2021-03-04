package com.assingment.android.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assingment.android.R
import com.assingment.android.view.NavigationEvent
import com.assingment.android.view.ShareViewModel
import com.google.android.material.textfield.TextInputLayout

class HomeFragment: Fragment() {

    private val layoutId: Int = R.layout.fragment_home

    private val viewModel: ShareViewModel by activityViewModels()

    private var nameInputLayout: TextInputLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.ctaButton)
        val editText = view.findViewById<EditText>(R.id.nameInput)
        nameInputLayout = view.findViewById(R.id.nameInputLayout)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home_title)

        button.setOnClickListener {
            viewModel.proceedToQuizScreen(editText.text)
        }
        editText.doAfterTextChanged {
            viewModel.onNameInputTextChanged(it)
        }

        viewModel.apply {
            nameError.observe(viewLifecycleOwner, ::showNameInputError)
            navigation.observe(viewLifecycleOwner, ::navigateToDestination)
        }
    }

    private fun showNameInputError(message: String?) {
        nameInputLayout?.apply {
            error = message
            isErrorEnabled = !message.isNullOrEmpty()
        }
    }

    private fun navigateToDestination(navigation: NavigationEvent?) {
        navigation ?: return
        findNavController().navigate(navigation.destination)
    }
}

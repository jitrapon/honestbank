package com.assingment.android

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.assingment.android.view.NavigationEvent
import com.assingment.android.view.ShareViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, sdk = [Build.VERSION_CODES.O_MR1])
class ShareViewModelTest {

    private lateinit var application: Application
    private lateinit var viewModel: ShareViewModel

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()
        viewModel = ShareViewModel(application)
    }

    @Test
    fun `don't navigate to quiz screen and show error when input name is empty`() {
        viewModel.proceedToQuizScreen("")

        assertEquals(null, viewModel.navigation.value)
        assertEquals(null, viewModel.name.value)
        assertEquals(application.getString(R.string.error_input_name), viewModel.nameError.value)
    }

    @Test
    fun `navigate to quiz screen when input name is not empty`() {
        val name = "John Doe"
        viewModel.proceedToQuizScreen(name)

        assertEquals(
            NavigationEvent(R.id.action_homeFragment_to_quizFragment),
            viewModel.navigation.value
        )
        assertEquals(name, viewModel.name.value)
        assertEquals(null, viewModel.nameError.value)
    }
}

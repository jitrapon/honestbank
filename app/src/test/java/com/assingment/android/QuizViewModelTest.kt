package com.assingment.android

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.assingment.android.model.Data
import com.assingment.android.view.NavigationEvent
import com.assingment.android.view.quiz.QuizViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, sdk = [Build.VERSION_CODES.O_MR1])
class QuizViewModelTest {

    private lateinit var application: Application
    private lateinit var viewModel: QuizViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()
        viewModel = QuizViewModel(application)
    }

    @Test
    fun `set page should set page correctly`() {
        viewModel.setPage(3)
        assertEquals(3, viewModel.page.value)
    }

    @Test
    fun `next page should increment page`() {
        viewModel.setPage(0)
        viewModel.nextPage()
        assertEquals(1, viewModel.page.value)
    }

    @Test
    fun `next page should not increment page after maximum reached`() {
        viewModel.setPage(Data.quizQuestions.size - 1)
        viewModel.nextPage()
        assertEquals(Data.quizQuestions.size - 1, viewModel.page.value)
    }

    @Test
    fun `previous page should decrement page`() {
        viewModel.setPage(Data.quizQuestions.size - 1)
        viewModel.previousPage()
        assertEquals(Data.quizQuestions.size - 2, viewModel.page.value)
    }

    @Test
    fun `previous page should not decrement page below zero`() {
        viewModel.setPage(0)
        viewModel.previousPage()
        assertEquals(0, viewModel.page.value)
    }

    @Test
    fun `all choices should be unselected on initialization`() {
        assertTrue(viewModel.choices.count { it == QuizViewModel.NO_CHOICE } == Data.quizQuestions.size)
    }

    @Test
    fun `select a correct choice should increment the score`() {
        val questionIndex = 0
        val question = Data.quizQuestions[questionIndex]
        assertEquals(0, viewModel.score.value)
        viewModel.selectChoice(
            questionIndex,
            question.choices.indexOfFirst { it == question.answer })
        assertEquals(1, viewModel.score.value)
    }

    @Test
    fun `select an incorrect choice should not change the score`() {
        val questionIndex = 0
        val question = Data.quizQuestions[questionIndex]
        assertEquals(0, viewModel.score.value)
        viewModel.selectChoice(
            questionIndex,
            question.choices.indexOfFirst { it != question.answer })
        assertEquals(0, viewModel.score.value)
    }

    @Test
    fun `retrieving selected choice should work correctly`() {
        viewModel.selectChoice(3, 0)
        assertEquals(0, viewModel.getSelectedChoice(3))
    }

    @Test
    fun `submit without answering all questions should not navigate to score`() {
        for (i in 0 until 5) {
            viewModel.selectChoice(i, 0)
        }
        viewModel.submit()
        assertEquals(null, viewModel.navigation.value)
    }

    @Test
    fun `submit with all questions answered should navigate to score`() {
        for (i in Data.quizQuestions.indices) {
            viewModel.selectChoice(i, 0)
        }
        viewModel.submit()
        assertEquals(
            NavigationEvent(R.id.action_quizFragment_to_scoreFragment),
            viewModel.navigation.value
        )
    }
}

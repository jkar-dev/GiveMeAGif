package com.jkapps.givemeagif.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jkapps.givemeagif.domain.GifRepository
import com.jkapps.givemeagif.domain.entity.Gif
import com.jkapps.givemeagif.ui.main.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var repository: GifRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Request a gif straight after viewmodel is created`() {
        MainViewModel(repository)
        coVerify { repository.getRandomGif() }
    }

    @Test
    fun `Request a gif only once if gifUrl is not null`()  {
        coEvery { repository.getRandomGif() } returns Gif(1, "test", "test")

        MainViewModel(repository)
        coVerify(exactly = 1) { repository.getRandomGif() }
    }

    @Test
    fun `Request another gif if gifUrl is null`() {
        coEvery { repository.getRandomGif() } returnsMany listOf(
            Gif(1, "1", null),
            Gif(2, "2", null),
            Gif(3, "3", "wow, not null")
        )

        MainViewModel(repository)
        coVerify(exactly = 3) { repository.getRandomGif() }
    }

    @Test
    fun `Successful gif request`() {
        val gif = Gif(1, "test", "test")
        coEvery { repository.getRandomGif() } returns gif

        val viewModel = MainViewModel(repository)
        assertEquals(gif, viewModel.gif.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(false, viewModel.isErrorDisplaying.value)
    }

    @Test
    fun `Set errorDisplaying as true after getting an error`() {
        coEvery { repository.getRandomGif() } answers { throw RuntimeException() }

        val viewModel = MainViewModel(repository)
        assertEquals(true, viewModel.isErrorDisplaying.value)
    }

    @Test
    fun `Previous button is enabled after loading the second gif`() {
        coEvery { repository.getRandomGif() } returns Gif(1, "test", "test")
        val viewModel = MainViewModel(repository)
        assertEquals(false, viewModel.isButtonEnable.value)
        viewModel.getNewGif()
        assertEquals(true, viewModel.isButtonEnable.value)
    }

    @Test
    fun `Previous button is not enabled after getting the last gif from the stack`() {
        coEvery { repository.getRandomGif() } returns Gif(1, "test", "test")
        val viewModel = MainViewModel(repository)
        viewModel.getNewGif()
        assertEquals(true, viewModel.isButtonEnable.value)
        viewModel.getPrevious()
        assertEquals(false, viewModel.isButtonEnable.value)
    }
}

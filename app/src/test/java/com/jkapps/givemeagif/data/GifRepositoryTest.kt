package com.jkapps.givemeagif.data

import com.jkapps.givemeagif.data.api.GifService
import com.jkapps.givemeagif.domain.entity.Gif
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

class GifRepositoryTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `Gif from the repository is the same as from api`() = runBlockingTest {
        val gif = Gif(1, "test", "test")
        val service = mockk<GifService>()
        coEvery { service.getRandomGif() } returns gif

        val repository = GifRepositoryImpl(service)
        assertEquals(gif, repository.getRandomGif())
    }
}
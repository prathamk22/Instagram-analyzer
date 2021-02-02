package com.pratham.project.fileio.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratham.project.fileio.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private lateinit var viewModel: HomeViewModel

    @Mock lateinit var homeRepositoryImpl: IHomeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(homeRepositoryImpl, null)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `allow user details should be called only one time`() {
        testCoroutineRule.runBlockingTest {
            homeRepositoryImpl.allowUserDetails()
            Mockito.verify(homeRepositoryImpl, times(1)).allowUserDetails()
        }

    }

}
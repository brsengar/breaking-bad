package com.gtsl.breakingbad.presentation.characterlist

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gtsl.breakingbad.net.repository.BreakingBadRepo
import com.gtsl.breakingbad.net.repository.model.CharacterInfo
import com.gtsl.breakingbad.presentation.characterlist.uimodel.UiCharacterInfo
import com.gtsl.breakingbad.presentation.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterListViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val repository: BreakingBadRepo = mockk()

    private lateinit var subject: CharacterListViewModel

    @Before
    fun setup() {
        subject = CharacterListViewModel(repository = repository)
    }

    @Test
    fun `onItemClick updates characterClick LiveData`() {
        val mockView = mockk<View>()
        val mockCharacterInfo = mockk<UiCharacterInfo>()

        subject.onItemClick(mockView, mockCharacterInfo)

        val event = subject.characterClickLiveData.getOrAwaitValue()
        val result = event.getContentIfNotHandled()

        assertEquals(mockView, result?.first)
        assertEquals(mockCharacterInfo, result?.second)
    }

    @Test
    fun `updateContents filters list as per search Text`() {
        subject.originalCharacterList.addAll(configureUiCharacterInfoList())

        subject.updateContents("He")

        subject.characterListLiveData.getOrAwaitValue()

        assertEquals(2, subject.characterListLiveData.value?.size)
        assertEquals("Henry", subject.characterListLiveData.value?.get(0)?.name)
        assertEquals("Hector", subject.characterListLiveData.value?.get(1)?.name)
    }

    @Test
    fun `updateAppearances filters list as per selected seasons`() {
        subject.originalCharacterList.addAll(configureUiCharacterInfoList())

        subject.updateAppearances(arrayListOf("Season 1"))

        subject.characterListLiveData.getOrAwaitValue()
        assertEquals(4, subject.characterListLiveData.value?.size)
        assertEquals("Walter white", subject.characterListLiveData.value?.get(0)?.name)
        assertEquals("Henry", subject.characterListLiveData.value?.get(1)?.name)
        assertEquals("Hector", subject.characterListLiveData.value?.get(2)?.name)
        assertEquals("Marco", subject.characterListLiveData.value?.get(3)?.name)

        subject.updateAppearances(arrayListOf("Season 1", "Season 3"))

        subject.characterListLiveData.getOrAwaitValue()
        assertEquals(2, subject.characterListLiveData.value?.size)
        assertEquals("Walter white", subject.characterListLiveData.value?.get(0)?.name)
        assertEquals("Henry", subject.characterListLiveData.value?.get(1)?.name)

        subject.updateAppearances(
            arrayListOf(
                "Season 1",
                "Season 2",
                "Season 3",
                "Season 4",
                "Season 5"
            )
        )

        subject.characterListLiveData.getOrAwaitValue()
        assertEquals(0, subject.characterListLiveData.value?.size)
    }

    @Test
    fun `loadCharacters uses repository to get characters information`() {
        val captureCallback = slot<(List<CharacterInfo>) -> Unit>()
        val errorCallback = slot<(Unit) -> Unit>()
        every {
            repository.getCharacters(
                capture(captureCallback),
                capture(errorCallback)
            )
        } answers {
            val list = configureCharacterInfoList()
            captureCallback.captured.invoke(list)
        }

        subject.loadCharacters()

        verify { repository.getCharacters(captureCallback.captured, errorCallback.captured) }
        assertEquals(3, subject.characterListLiveData.value?.size)
        assertFalse(subject.characterListErrorLiveData.value!!)
    }

    @Test
    fun `loadCharacters returns error if fails to load`() {
        val captureCallback = slot<(List<CharacterInfo>) -> Unit>()
        val errorCallback = slot<(Unit) -> Unit>()
        every {
            repository.getCharacters(
                capture(captureCallback),
                capture(errorCallback)
            )
        } answers {
            errorCallback.captured.invoke(Unit)
        }

        subject.loadCharacters()

        verify { repository.getCharacters(captureCallback.captured, errorCallback.captured) }
        assertEquals(null, subject.characterListLiveData.value)
        assertTrue(subject.characterListErrorLiveData.value!!)
    }

    private fun configureUiCharacterInfoList(): List<UiCharacterInfo> {
        return arrayListOf(
            toUiCharacterInfo("Walter white", listOf("Season 1", "Season 2", "Season 3")),
            toUiCharacterInfo("Henry", listOf("Season 4", "Season 1", "Season 3")),
            toUiCharacterInfo("Gustavo Fring", listOf("Season 3", "Season 4", "Season 5")),
            toUiCharacterInfo("Hector", listOf("Season 1", "Season 4", "Season 5")),
            toUiCharacterInfo("Marco", listOf("Season 1", "Season 2", "Season 5"))
        )
    }

    private fun toUiCharacterInfo(name: String, appearance: List<String>): UiCharacterInfo {
        return UiCharacterInfo(
            name = name,
            appearance = appearance,
            id = 1,
            nickName = "",
            imageUrl = "",
            status = "",
            occupation = emptyList()
        )

    }

    private fun configureCharacterInfoList(): List<CharacterInfo> {
        return arrayListOf(
            toCharacterInfo("Walter white", listOf(1, 2, 3)),
            toCharacterInfo("Henry", listOf(4, 1, 3)),
            toCharacterInfo("Gustavo Fring", listOf(3, 4, 5))
        )
    }

    private fun toCharacterInfo(name: String, appearance: List<Int>): CharacterInfo {
        return CharacterInfo(
            name = name,
            appearance = appearance,
            id = 1,
            nickname = "",
            img = "",
            status = "",
            occupation = emptyList(),
            birthday = "",
            portrayed = "",
            category = "",
            betterCallSaulAppearance = emptyList()
        )
    }
}
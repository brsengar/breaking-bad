package com.gtsl.breakingbad.mapper

import com.gtsl.breakingbad.net.repository.model.CharacterInfo
import junit.framework.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `toUiModel converts server info to UiModel`() {
        val serverModel = CharacterInfo(
            name = "name",
            appearance = listOf(1, 3, 5),
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

        val uiModel = serverModel.toUiModel()

        assertEquals("Season 1", uiModel.appearance[0])
        assertEquals("Season 3", uiModel.appearance[1])
        assertEquals("Season 5", uiModel.appearance[2])
    }
}
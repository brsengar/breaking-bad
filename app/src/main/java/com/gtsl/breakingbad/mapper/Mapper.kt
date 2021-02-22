package com.gtsl.breakingbad.mapper

import com.gtsl.breakingbad.net.repository.model.CharacterInfo
import com.gtsl.breakingbad.presentation.characterlist.uimodel.UiCharacterInfo

fun CharacterInfo.toUiModel(): UiCharacterInfo {
    return UiCharacterInfo(
        id = this.id,
        name = this.name,
        nickName = this.nickname,
        dob = this.birthday,
        imageUrl = this.img,
        appearance = this.appearance.map { "Season $it" },
        occupation = this.occupation,
        status = this.status,
        portrayed = this.portrayed,
        category = this.category
    )
}
package com.gtsl.breakingbad.presentation.characterlist.uimodel

data class UiCharacterInfo(
    val id: Int,
    val name: String,
    val nickName: String,
    val dob: String,
    val imageUrl: String,
    val appearance: List<String>,
    val occupation: List<String>,
    val status: String,
    val portrayed: String,
    val category: String
)
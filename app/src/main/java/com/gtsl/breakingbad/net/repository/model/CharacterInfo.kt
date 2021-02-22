package com.gtsl.breakingbad.net.repository.model

import com.google.gson.annotations.SerializedName

data class CharacterInfo(
    @SerializedName("char_id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("occupation")
    val occupation: List<String>,
    @SerializedName("img")
    val img: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("appearance")
    val appearance: List<Int>,
    @SerializedName("portrayed")
    val portrayed: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int>
)
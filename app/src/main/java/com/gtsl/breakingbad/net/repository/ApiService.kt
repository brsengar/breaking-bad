package com.gtsl.breakingbad.net.repository

import com.gtsl.breakingbad.net.repository.model.CharacterInfo
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("characters")
    fun getCharacters(): Observable<List<CharacterInfo>>
}
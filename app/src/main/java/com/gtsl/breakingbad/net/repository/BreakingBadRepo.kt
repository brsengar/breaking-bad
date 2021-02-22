package com.gtsl.breakingbad.net.repository

import com.gtsl.breakingbad.net.repository.model.CharacterInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BreakingBadRepo constructor(
    private val apiService: ApiService
) {

    fun getCharacters(
        onSuccess: (List<CharacterInfo>) -> Unit,
        onError: (Unit) -> Unit
    ) {
        apiService.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onSuccess(it)
                },
                {
                    onError
                })

    }
}
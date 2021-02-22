package com.gtsl.breakingbad.presentation.characterlist

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtsl.breakingbad.extensions.Event
import com.gtsl.breakingbad.mapper.toUiModel
import com.gtsl.breakingbad.net.repository.BreakingBadRepo
import com.gtsl.breakingbad.presentation.characterlist.uimodel.UiCharacterInfo

class CharacterListViewModel constructor(
    val repository: BreakingBadRepo
) : ViewModel() {

    internal val originalCharacterList = ArrayList<UiCharacterInfo>()
    private var searchFilterText: String = ""
    var selectedAppearances = ArrayList<String>()

    private val _characterListLiveData = MutableLiveData<List<UiCharacterInfo>>()
    val characterListLiveData: LiveData<List<UiCharacterInfo>>
        get() = _characterListLiveData

    private val _characterListErrorLiveData = MutableLiveData<Boolean>()
    val characterListErrorLiveData: LiveData<Boolean>
        get() = _characterListErrorLiveData

    private val _characterInfoLiveData = MutableLiveData<UiCharacterInfo>()
    val characterInfoLiveData: LiveData<UiCharacterInfo>
        get() = _characterInfoLiveData

    private val _characterClickLiveData = MutableLiveData<Event<View>>()
    val characterClickLiveData: LiveData<Event<View>>
        get() = _characterClickLiveData

    private val _isRefreshing = MutableLiveData<Boolean>(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    fun loadCharacters() {
        _isRefreshing.value = true
        repository.getCharacters(
            onSuccess = {
                originalCharacterList.clear()
                originalCharacterList.addAll(it.map { response ->
                    response.toUiModel()
                })
                updateFilteredList()
                _characterListErrorLiveData.value = false
                _isRefreshing.value = false
            },
            onError = {
                // show error to Ui
                _characterListErrorLiveData.value = true
                _isRefreshing.value = false
            }
        )
    }

    fun onItemClick(view: View, characterInfo: UiCharacterInfo) {
        _characterInfoLiveData.value = characterInfo
        _characterClickLiveData.value = Event(view)
    }

    fun updateContents(searchText: String) {
        searchFilterText = searchText
        updateFilteredList()
    }

    fun updateAppearances(selectedAppearances: List<CharSequence>) {
        this.selectedAppearances.clear()
        this.selectedAppearances.addAll(selectedAppearances.map { it.toString() })
        updateFilteredList()
    }

    private fun updateFilteredList() {
        _characterListLiveData.value = originalCharacterList
            .filter {
                if (selectedAppearances.isNotEmpty()) {
                    it.appearance.containsAll(selectedAppearances)
                } else {
                    true
                }
            }
            .filter {
                if (searchFilterText.isNotEmpty()) {
                    it.name.startsWith(searchFilterText, true)
                } else {
                    true
                }
            }
    }
}
package com.gtsl.breakingbad.di

import com.gtsl.breakingbad.presentation.characterlist.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: Module = module {

    viewModel {
        CharacterListViewModel(
            repository = get()
        )
    }
}

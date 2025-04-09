package orc.zdertis420.simplenotes.di

import orc.zdertis420.simplenotes.data.repository.ThemeRepositoryImplementation
import orc.zdertis420.simplenotes.domain.implementation.ThemeInteractorImplementation
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.domain.repository.ThemeRepository
import orc.zdertis420.simplenotes.ui.viewmodel.HomeViewModel
import orc.zdertis420.simplenotes.ui.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //Main VM
    viewModel<MainViewModel> { MainViewModel() }

    //Home VM
    viewModel<HomeViewModel> { HomeViewModel() }

    //Theme
    factory<ThemeInteractor> { ThemeInteractorImplementation(get<ThemeRepository>()) }
    single<ThemeRepository> { ThemeRepositoryImplementation(androidContext()) }
}
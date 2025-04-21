package orc.zdertis420.simplenotes.di

import orc.zdertis420.simplenotes.data.repository.TaskRepositoryImplementation
import orc.zdertis420.simplenotes.data.repository.ThemeRepositoryImplementation
import orc.zdertis420.simplenotes.domain.implementation.TaskInteractorImplementation
import orc.zdertis420.simplenotes.domain.implementation.ThemeInteractorImplementation
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.domain.repository.TaskRepository
import orc.zdertis420.simplenotes.domain.repository.ThemeRepository
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel
import orc.zdertis420.simplenotes.ui.viewmodel.HomeViewModel
import orc.zdertis420.simplenotes.ui.viewmodel.MainViewModel
import orc.zdertis420.simplenotes.ui.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val main = module {
    viewModel<MainViewModel> { MainViewModel() }
}

val home = module {
    viewModel<HomeViewModel> { HomeViewModel() }
}

val themes = module {
    single<ThemeRepository> { ThemeRepositoryImplementation(androidContext()) }
    factory<ThemeInteractor> { ThemeInteractorImplementation(get<ThemeRepository>()) }

    viewModel<SettingsViewModel> { SettingsViewModel(get<ThemeInteractor>()) }
}

val task = module {
    single<TaskRepository> { TaskRepositoryImplementation(androidContext()) }
    factory<TaskInteractor> { TaskInteractorImplementation(get<TaskRepository>()) }

    viewModel<TaskViewModel> { TaskViewModel(get<TaskInteractor>()) }
}


package orc.zdertis420.simplenotes

import android.app.Application
import orc.zdertis420.simplenotes.di.home
import orc.zdertis420.simplenotes.di.main
import orc.zdertis420.simplenotes.di.task
import orc.zdertis420.simplenotes.di.themes
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val themeInteractor by inject<ThemeInteractor>()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                main,
                home,
                themes,
                task,
            )
        }

        themeInteractor.switchTheme(themeInteractor.getTheme())
    }
}
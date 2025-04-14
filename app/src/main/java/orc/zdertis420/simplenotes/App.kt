package orc.zdertis420.simplenotes

import android.app.Application
import android.content.res.Configuration
import orc.zdertis420.simplenotes.di.appModule
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
            modules(appModule)
        }


        if (themeInteractor.isThemeEditedManually()) {
            val theme = themeInteractor.getTheme()
            themeInteractor.switchTheme(theme)
            themeInteractor.saveTheme(theme)
        } else {
            val theme = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            themeInteractor.switchTheme(theme)
            themeInteractor.saveAutoTheme(theme)
        }
    }
}
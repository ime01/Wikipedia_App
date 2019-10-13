package wikipedia.activities.allClasses

import android.app.Application
import wikipedia.activities.allClasses.Managers.WikiManager
import wikipedia.activities.allClasses.providers.ArticleDataProvider
import wikipedia.activities.allClasses.repositories.ArticleDataBaseOpenHelper
import wikipedia.activities.allClasses.repositories.FavoriteRepository
import wikipedia.activities.allClasses.repositories.HistoryRepository

class WikiApplication: Application(){

    private var dbHelper: ArticleDataBaseOpenHelper? = null
    private var favoriteRepository: FavoriteRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDataBaseOpenHelper(applicationContext)
        favoriteRepository = FavoriteRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoriteRepository!!, historyRepository!!)



    }
}
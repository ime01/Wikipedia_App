package wikipedia.activities.allClasses.Managers

import wikipedia.activities.allClasses.models.WikiPage
import wikipedia.activities.allClasses.models.WikiResult
import wikipedia.activities.allClasses.providers.ArticleDataProvider
import wikipedia.activities.allClasses.repositories.FavoriteRepository
import wikipedia.activities.allClasses.repositories.HistoryRepository

class WikiManager (private val provider: ArticleDataProvider,
                   private val favoriteRepository: FavoriteRepository,
                   private val historyRepository: HistoryRepository){

    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search (term: String, skip: Int, take:Int, handler: (result: WikiResult)-> Unit?){
        return provider.search(term,skip,take,handler)
    }
    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.getRandom(take, handler)
    }
    fun getHistory():   ArrayList<WikiPage>?{
        if(historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }
    fun getFavorites(): ArrayList<WikiPage>? {
        if (favoritesCache==null)
            favoritesCache = favoriteRepository.getAllFavorites()
        return favoritesCache
    }
    fun addFavorite(page: WikiPage){
        favoritesCache?.add(page)
        favoriteRepository.addFavorite(page)
    }
    fun removeFavorite(pageId: Int){
        favoriteRepository.removeFavoriteById(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }
    fun getIsFavorite(pageId: Int): Boolean{
        return favoriteRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addFavorite(page)
    }
    fun clearHistory(){
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory?.forEach { historyRepository.removePageById(it.pageid!!) }
    }


}
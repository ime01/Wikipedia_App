package wikipedia.activities.allClasses

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikipediaapp.R
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_article_detail.toolbar
import kotlinx.android.synthetic.main.activity_search.*
import wikipedia.activities.allClasses.Managers.WikiManager
import wikipedia.activities.allClasses.adapters.ArticleListItemRecyclerAdapter
import wikipedia.activities.allClasses.models.WikiResult
import wikipedia.activities.allClasses.providers.ArticleDataProvider

class SearchActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null

    //private val articleProvider : ArticleDataProvider = ArticleDataProvider()
    private var adapter : ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        search_results_recycler.layoutManager = LinearLayoutManager(this)
        search_results_recycler.adapter = adapter


        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu!!.findItem(R.id.action_search)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                wikiManager?.search(query, 0, 20, { WikiResult->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(WikiResult.query!!.pages)
                    runOnUiThread{adapter.notifyDataSetChanged()}
                } )

                println("updated search")

                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}

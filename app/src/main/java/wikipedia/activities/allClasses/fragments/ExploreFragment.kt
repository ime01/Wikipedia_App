package wikipedia.activities.allClasses.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.wikipediaapp.R
import kotlinx.android.synthetic.main.fragment_explore.*
import wikipedia.activities.allClasses.Managers.WikiManager
import wikipedia.activities.allClasses.SearchActivity
import wikipedia.activities.allClasses.WikiApplication
import wikipedia.activities.allClasses.adapters.ArticleCardRecyclerAdapter
import wikipedia.activities.allClasses.models.WikiResult
import wikipedia.activities.allClasses.providers.ArticleDataProvider

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    private var wikiManager: WikiManager? = null

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var refresher: SwipeRefreshLayout? = null

    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)


        searchCardView!!.setOnClickListener {
           val searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {

            getRandomArticles()
        }

        getRandomArticles()

        return view;
    }

    private fun getRandomArticles(){
        refresher?.isRefreshing = true

        try {

       wikiManager?.getRandom(15, { WikiResult ->
           //do something when we get articles
           adapter.currentResults.clear()
           adapter.currentResults.addAll(WikiResult.query!!.pages)
           activity?.runOnUiThread{
               adapter.notifyDataSetChanged()
               refresher?.isRefreshing = false
           }

       })
    }
        catch (ex : Exception){
            //show alert
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message).setTitle("oops!")
            val dialog = builder.create()
            dialog.show()
        }
    }


}

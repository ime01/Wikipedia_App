package wikipedia.activities.allClasses.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wikipediaapp.R
import wikipedia.activities.allClasses.holders.CardHolder
import wikipedia.activities.allClasses.models.WikiPage
import java.lang.reflect.Array

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardHolder>(){

    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        var page = currentResults[position]
        //this is where we update our view

        holder?.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent,false)
        return CardHolder(cardItem)
    }
}
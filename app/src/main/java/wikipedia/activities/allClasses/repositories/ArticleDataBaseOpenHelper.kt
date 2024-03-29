package wikipedia.activities.allClasses.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ArticleDataBaseOpenHelper (context : Context): ManagedSQLiteOpenHelper(context,"ArticleDatabase.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        //define the tables in this database

        db?.createTable("Favorites", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to  TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)

        db?.createTable("History", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to  TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //use to upgrade the schemas of the table if needed
    }

}
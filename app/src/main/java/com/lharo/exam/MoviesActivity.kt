package com.lharo.exam

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates


class MoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val movies = ArrayList<MovieModel>()
        val titles: Array<String> = resources.getStringArray(R.array.title)
        val years: Array<String> = resources.getStringArray(R.array.year)
        val directors: Array<String> = resources.getStringArray(R.array.director)
        val genres: Array<String> = resources.getStringArray(R.array.genre)
        val languages: Array<String> = resources.getStringArray(R.array.language)
        val metascores: Array<String> = resources.getStringArray(R.array.metascore)
        val imdbRatings: Array<String> = resources.getStringArray(R.array.imdbRating)
        val imdbVotes: Array<String> = resources.getStringArray(R.array.imdbVotes)

        for((i, element) in titles.withIndex()){
            val movie = MovieModel()
            movie.title = element
            movie.year = years[i]
            movie.director = directors[i]
            movie.genre = genres[i]
            movie.language = languages[i]
            movie.metascore = metascores[i]
            movie.imdbRating = imdbRatings[i]
            movie.imdbVotes = imdbVotes[i]
            movies.add(movie)
        }
    }
    companion object {
        private class DataDownload(context: Context, recyclerView: RecyclerView) : AsyncTask<String, Void, String>(){
            private val TAG = "Download"

            var localContext: Context by Delegates.notNull()
            var localRecyclerView: RecyclerView by Delegates.notNull()

            init{
                localContext = context
                localRecyclerView = recyclerView
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground")//.d significa debbug
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()){
                    Log.e(TAG, "doInBackground: failed")//Estos logs si los puede visualizar el usuario
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String{
                try{
                    return URL(urlPath).readText()
                }catch (e: Exception){
                    val errorMesage: String = when(e){
                        is MalformedURLException -> "downloadXML: Invalid url: ${e.message}"
                        is IOException -> "downloadXML: Error reading data: ${e.message}"
                        else -> "downloadXML: Unknown error ${e.message}"
                    }
                    Log.e(TAG, errorMesage)
                }
                return ""
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute")
                val parsedApplication = ParseApp()
                parsedApplication.parse(result)

                val adapter: AppAd = AppAd(localContext, parsedApplication.apps)
                localRecyclerView.adapter = adapter
                localRecyclerView.layoutManager = LinearLayoutManager(localContext)
            }


        }
    }
}
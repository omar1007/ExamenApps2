package com.lharo.exam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAd (context: Context, MovieModel: ArrayList<MovieModel>): RecyclerView.Adapter<AppAd.ViewHolder>(){
    private var localContext: Context? = null
    private var localEntry: ArrayList<MovieModel>? = null

    init {
        localContext = context
        localEntry = MovieModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAd.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(localContext)
        val view: View = layoutInflater.inflate(R.layout.feed_row, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFeedEntry: MovieModel = localEntry!![position]
        holder.textTitle.text = currentFeedEntry.title
        holder.textRating.text = currentFeedEntry.imdbRating
        holder.textMeta.text = currentFeedEntry.metascore
        holder.textVotes.text = currentFeedEntry.imdbVotes
    }

    override fun getItemCount(): Int {
        return localEntry?.size?:0
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val textTitle: TextView = v.findViewById(R.id.txtName)
        val textRating: TextView = v.findViewById(R.id.txtRating)
        val textMeta: TextView = v.findViewById(R.id.txtMeta)
        val textVotes: TextView = v.findViewById(R.id.txtVotes)
    }

}
package com.example.finalproj03ag01


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NowPlayingRecyclerViewAdapter(
    // list of films
    private val film:  List<film>,
    //handles click events
    private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<NowPlayingRecyclerViewAdapter.filmVH>() {

    inner class filmVH(val mView: View) : RecyclerView.ViewHolder(mView) {

        // bounds movies to view holder
        var film_item: film? = null
        // UI components
        val film_Title: TextView = mView.findViewById<View>(R.id.tV) as TextView
        val film_Desc: TextView = mView.findViewById<View>(R.id.desV) as TextView
        val film_poster: ImageView = mView.findViewById<View>(R.id.pV) as ImageView


        // return movie title as string
        override fun toString(): String {
            return film_Title.toString()
        }
    }

    // inflates layout for a single movie
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): filmVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        // return new ViewHolder
        return filmVH(view)
    }

    // bind data to view holder
    override fun onBindViewHolder(holder: filmVH, position: Int) {
        // get movie based on position
        val films = film[position]

        // set film title, dec, and  assign viewfinder

        holder.film_item = films
        holder.film_Title.text = films.film_t
        holder.film_Desc.text = films.film_des

        // load poset image asynchronously L
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + films.film_poster)
            .placeholder(R.drawable.ic_loading)
            .centerInside()
            .into(holder.film_poster)

        // handle click for the item view
        holder.mView.setOnClickListener {
            holder.film_item?.let { book ->
                mListener?.onItemClick(book)
            }
        }
    }

    // return number of movies
    override fun getItemCount(): Int {
        return film.size
    }
}

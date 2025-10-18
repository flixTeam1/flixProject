package com.example.finalproj03ag01


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

// API key from database
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
// fragment display a list of current movies
class NowPlayingFragment : Fragment() {
    //listener handles click evens from recycler view

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        // inflate layout
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout of recycler view
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        // link bar and recycler view from layout
        val bar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val reView = view.findViewById<View>(R.id.movie_list) as RecyclerView
        // set up recycler view vertically
        val context = view.context
        reView.layoutManager = LinearLayoutManager(context)
        // update and return movie
        updateAdapter(bar, reView)
        return view
    }


    // Get data from API and update recycler view
    private fun updateAdapter(prgBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        prgBar.show()

        // Create a AsyncHTTPClient()
        val cli = AsyncHttpClient()
        val req = RequestParams()
        req["api_key"] = API_KEY

        // get request for movies
        cli[
            "https://api.themoviedb.org/3/movie/now_playing",
            req,
            object : JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    // hide bar once we get data
                    prgBar.hide()

                    // extract results from JSON
                    val resultJ: String = json.jsonObject.get("results").toString()
                    // convert json into a list of movies
                    val gson = Gson()
                    val arrayTType = object : TypeToken<List<film>>() {}.type
                    val mod: List<film> = gson.fromJson(resultJ, arrayTType)
                    recyclerView.adapter = NowPlayingRecyclerViewAdapter(mod, listener)


                }
                // check for errors
                override fun onFailure(
                    statCode: Int,
                    headers: Headers?,
                    erResponse: String,
                    t: Throwable?
                ) {
                    // hide bar on error
                    prgBar.hide()

                    // Log debugging
                    t?.message?.let {
                        Log.e(
                            "NowPlayingFragment", "HTML Error Code: "
                                    + statCode.toString() + ", Headers: " + headers.toString()
                        )
                        Log.e("NowPlayingFragment", erResponse)
                    }
                }
            }]
    }


}
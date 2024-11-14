package com.example.project4

com.sarcb.flixsterplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class PopularPeopleFragment : Fragment() {

    private lateinit var peopleAdapter: PeopleAdapter
    private val peopleList = ArrayList<Person>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular_people, container, false)

        // Set up RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        peopleAdapter = PeopleAdapter(peopleList)
        recyclerView.adapter = peopleAdapter

        // Fetch data from API
        fetchPopularPeople()

        return view
    }

    private fun fetchPopularPeople() {
        val PERSON_API_URL = "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        val client = AsyncHttpClient()
        client.get(PERSON_API_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                response?.let {
                    val personJsonArray: JSONArray = it.getJSONArray("results")
                    for (i in 0 until personJsonArray.length()) {
                        val personJson = personJsonArray.getJSONObject(i)
                        val person = Person.fromJson(personJson)
                        peopleList.add(person)
                    }
                    peopleAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                // Handle error
            }
        })
    }
}
{
}
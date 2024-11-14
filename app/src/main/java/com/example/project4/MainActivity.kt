package com.example.project4
package com.sarcb.flixsterplus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var peopleRecyclerView: RecyclerView
    private lateinit var peopleList: MutableList<Person>
    private lateinit var peopleAdapter: PeopleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView
        peopleRecyclerView = findViewById(R.id.peopleRecyclerView)
        peopleList = mutableListOf()

        // Set up the adapter and layout manager for RecyclerView
        peopleAdapter = PeopleAdapter(peopleList, object : PeopleAdapter.OnClickListener {
            override fun onItemClick(person: Person) {
                // When a person item is clicked, open the DetailActivity
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("person", person)
                startActivity(intent)
            }
        })
        peopleRecyclerView.adapter = peopleAdapter
        peopleRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch popular people from TMDB API
        fetchPopularPeople()
    }

    private fun fetchPopularPeople() {
        val PERSON_API_URL = "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        val client = AsyncHttpClient()

        client.get(PERSON_API_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.d("MainActivity", "onSuccess: $json")
                try {
                    val results: JSONArray = json.jsonObject.getJSONArray("results")
                    // Parse JSON response and populate the RecyclerView
                    for (i in 0 until results.length()) {
                        val personJson: JSONObject = results.getJSONObject(i)
                        val person = Person.fromJson(personJson)
                        peopleList.add(person)
                    }
                    peopleAdapter.notifyDataSetChanged() // Notify adapter of data change
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("MainActivity", "Failed to parse JSON response")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("MainActivity", "onFailure: $statusCode")
            }
        })
    }
}

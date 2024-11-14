package com.example.project4

package com.sarcb.flixsterplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PeopleAdapter(private val people: List<Person>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int = people.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val knownForTextView: TextView = itemView.findViewById(R.id.knownForTextView)
        private val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)

        fun bind(person: Person) {
            nameTextView.text = person.name
            knownForTextView.text = person.knownFor
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${person.profilePath}")
                .into(profileImageView)
        }
    }
}

package cij.cidertime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cij.cidertime.models.Beverage
import com.example.cidertime.R

class BeverageAdapter(private val beverages: List<Beverage>) : RecyclerView.Adapter<BeverageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var beverageName: TextView = itemView.findViewById(R.id.beverageName)
        var beverageRating: TextView = itemView.findViewById(R.id.beverageRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beverage_rating_text, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.beverageName.text = beverages[position].name
        holder.beverageRating.text = buildString {
            append(beverages[position].rating) //actual rating
            append(R.string.totalRating) //rating maximum
        }
    }

    override fun getItemCount(): Int {
        return beverages.size
    }
}
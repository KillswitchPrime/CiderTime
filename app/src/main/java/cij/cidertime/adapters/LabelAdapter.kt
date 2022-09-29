package cij.cidertime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cij.cidertime.models.Label
import com.example.cidertime.R

class LabelAdapter(private val labels: List<Label>): RecyclerView.Adapter<LabelAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var labelTitle: TextView = itemView.findViewById(R.id.label)
        var beverageList: RecyclerView = itemView.findViewById(R.id.beverageList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beverage_rating_text, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.labelTitle.text = labels[position].title
        holder.beverageList.adapter = BeverageAdapter(labels[position].beverages)
    }

    override fun getItemCount(): Int {
        return labels.size
    }
}
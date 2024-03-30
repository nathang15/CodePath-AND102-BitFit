package com.example.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val context: Context, private val foodList: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle, parent, false)
        return ViewHolder(view, mListener)
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val food = itemView.findViewById<TextView>(R.id.tvFood)
        private val calories = itemView.findViewById<TextView>(R.id.tvCalories)

        init {
            itemView.setOnLongClickListener {
                listener.onItemClick(adapterPosition)
                true
            }

        }

        fun bind(foodItem: Food) {
            food.text = foodItem.food
            calories.text = foodItem.calories
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }


}
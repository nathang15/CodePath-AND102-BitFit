package com.example.bitfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Byte


class AveragesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_averages, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val caloriesMax = activity?.findViewById(R.id.MaxCal) as TextView
        val caloriesMin = activity?.findViewById(R.id.minCal) as TextView
        val avg = activity?.findViewById(R.id.AVGCal) as TextView

        var small: Int = Byte.MAX_VALUE.toInt()
        var max: Int = Byte.MIN_VALUE.toInt()
        var num: Int = 0
        var sum: Int = 0


        lifecycleScope.launch(Dispatchers.IO) {
            for (item in (activity?.application as MyApplication).db.foodDao().getCal()){
                num++
                sum = sum + Integer.parseInt(item)

                var average = sum/num

                avg.text = average.toString()

                // Reset the min if it has lesser calories
                if(Integer.parseInt(item) < small){
                    small = Integer.parseInt(item)
                }

                // Reset the max if it has greater calories
                if(Integer.parseInt(item) > max){
                    max = Integer.parseInt(item)
                }

                caloriesMax.text = max.toString()
                caloriesMin.text = small.toString()
            }
        }
    }
}
package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FoodFragment : Fragment() {

    private val entries = mutableListOf<Food>()
    private lateinit var recycle: RecyclerView
    private lateinit var foodAdapter : FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_records, container, false)

        recycle = view.findViewById<RecyclerView>(R.id.recyclerView)
        foodAdapter = FoodAdapter(requireContext(),entries)
        recycle.adapter = foodAdapter

        recycle.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recycle.addItemDecoration(dividerItemDecoration)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{

            (requireActivity().application as MyApplication).db.foodDao().getAll().collect { databaseList ->
                entries.clear()
                databaseList.map { mappedList ->
                    entries.addAll(listOf(mappedList))
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        recycle.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recycle.addItemDecoration(dividerItemDecoration)
        }


        //Calling an intent on Button Click
        val add = view.findViewById<Button>(R.id.button)
        add.setOnClickListener {
            val i = Intent(requireActivity(), DetailActivity::class.java)
            startActivity(i)
        }

        //Button to delete all Entries
        val delete = view.findViewById<Button>(R.id.btnDelete)
        delete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (requireActivity().application as MyApplication).db.foodDao().deleteAll()
            }

            entries.clear()
            foodAdapter.notifyDataSetChanged()
        }


        // Remove Item
        foodAdapter.setOnItemClickListener(object: FoodAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "Item removed at position $position", Toast.LENGTH_LONG).show()
                val itemToDelete = entries[position]

                lifecycleScope.launch(Dispatchers.IO) {
                    (requireActivity().application as MyApplication).db.foodDao().deleteItem(itemToDelete)
                }
                entries.removeAt(position)
                foodAdapter.notifyItemRemoved(position)
            }
        })
    }

}
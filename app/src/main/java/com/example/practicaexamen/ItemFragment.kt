package com.example.practicaexamen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaexamen.adapter.AdapterRecyclerView
import com.example.practicaexamen.databinding.FragmentRecyclerviewBinding

class itemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecyclerviewBinding.inflate(inflater,container,false)
        val view = binding?.root

        val mainActivity = activity as?MainActivity
        val recipeHistoryFr = mainActivity?.getRecipe()

        val recipe = AdapterRecyclerView(recipeHistoryFr)

        binding.recyclerViewFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFragment.adapter = recipe

        return view
    }

    companion object{

        @JvmStatic
        fun newInstance() =
            itemFragment()
    }

}





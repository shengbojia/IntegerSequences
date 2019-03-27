package com.shengbojia.integersequences.ui


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.shengbojia.integersequences.R
import com.shengbojia.integersequences.util.InjectorUtils
import com.shengbojia.integersequences.viewmodel.SearchViewModel
import com.shengbojia.integersequences.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = InjectorUtils.provideSearchViewModelFactory(requireContext())

        searchViewModel = activity?.run {
            ViewModelProviders.of(this, factory)
                .get(SearchViewModel::class.java)
        } ?: throw Exception("Invalid activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnSearchSearch.setOnClickListener {
            searchBtnClick(it)
        }

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(activity, "Pressed settings", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchBtnClick(view: View) {
        Log.d(TAG, "Clicked search button")
        val queryInput = filterInput(binding.etSearchQueryInput.text.toString())

        searchViewModel.search(queryInput)
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment()
        findNavController().navigate(direction)
    }

    private fun filterInput(queryInput: String): String {
        return queryInput

    }

    companion object {
        private const val TAG = "FragmentHome"
    }

}

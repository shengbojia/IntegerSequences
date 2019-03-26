package com.shengbojia.integersequences.ui


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.shengbojia.integersequences.R
import com.shengbojia.integersequences.adapter.SequenceAdapter
import com.shengbojia.integersequences.databinding.FragmentSearchResultBinding
import com.shengbojia.integersequences.model.IntSequence
import com.shengbojia.integersequences.util.InjectorUtils
import com.shengbojia.integersequences.viewmodel.SearchViewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchResultFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: SequenceAdapter

    private lateinit var binding: FragmentSearchResultBinding

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
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        // Register an observer for the LiveData
        initAdapter()

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

    private fun initAdapter() {
        adapter = SequenceAdapter()
        binding.recyclerSequenceList.adapter = adapter

        searchViewModel.sequences.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }






}

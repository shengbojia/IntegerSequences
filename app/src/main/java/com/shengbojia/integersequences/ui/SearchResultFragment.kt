package com.shengbojia.integersequences.ui


import android.os.Bundle
import android.util.Log
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
import com.shengbojia.integersequences.repository.NetworkState
import com.shengbojia.integersequences.repository.ResultState
import com.shengbojia.integersequences.util.InjectorUtils
import com.shengbojia.integersequences.util.Utils
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
        initResultSummary()
        handleNetworkState()

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

    private fun handleNetworkState() {
        searchViewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.apply {

                if (it == null || it == NetworkState.LOADING) {
                    Log.d(TAG, "null or Loading: $it")
                    progressBarResultFragLoading.visibility = View.VISIBLE

                } else if (it == NetworkState.LOADED) {
                    progressBarResultFragLoading.visibility = View.GONE
                    recyclerSequenceList.visibility = View.VISIBLE
                    linearResultFragResultSummary.visibility = View.VISIBLE

                } else if (it.msg != null) {
                    handleNetworkError(it.msg)
                }
            }
        })
    }

    /*

    private fun handleResultState() {
        Log.d(TAG, "handleLoadedResult")

        searchViewModel.resultState.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it == null || it == ResultState.NORMAL) {

                    tvResultFragInvalidResult.visibility = View.GONE

                } else if (it == ResultState.NO_RESULTS) {

                    tvResultFragInvalidResult.text = getString(R.string.invalidResult_noResults)
                    tvResultFragInvalidResult.visibility = View.VISIBLE

                } else if (it == ResultState.TOO_MANY_RESULTS) {

                    tvResultFragInvalidResult.text = getString(R.string.invalidResult_tooMany)
                    tvResultFragInvalidResult.visibility = View.VISIBLE

                }
            }

        })
    }
    */

    private fun handleNetworkError(errorMsg: String) {
        Log.d(TAG, "handleNetworkError($errorMsg)")
    }

    private fun initAdapter() {
        adapter = SequenceAdapter()
        binding.recyclerSequenceList.adapter = adapter

        searchViewModel.sequences.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initResultSummary() {
        searchViewModel.totalCount.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it == null) {
                    linearResultFragResultSummary.visibility = View.GONE
                    return@Observer
                }
                tvResultFragResultsNum.text = it.toString()
                if (it > 50000) {
                    tvResultFragInvalidResult.text = getString(R.string.invalidResult_tooMany)
                    tvResultFragInvalidResult.visibility = View.VISIBLE
                } else if (it == 0) {
                    tvResultFragInvalidResult.text = getString(R.string.invalidResult_noResults)
                    tvResultFragInvalidResult.visibility = View.VISIBLE
                } else {
                    tvResultFragInvalidResult.visibility = View.GONE
                }
            }

        })
    }

    companion object {
        private const val TAG = "FragSearchResult"
    }

}

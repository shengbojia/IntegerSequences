package com.shengbojia.integersequences.ui


import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.shengbojia.integersequences.R
import com.shengbojia.integersequences.util.InjectorUtils
import com.shengbojia.integersequences.viewmodel.SearchViewModel
import com.shengbojia.integersequences.databinding.FragmentHomeBinding
import com.shengbojia.integersequences.util.Utils
import com.shengbojia.integersequences.worker.ClearCacheWorker

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

        // Activity level view model
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

        initSearch()

        binding.btnSearchSearch.setOnClickListener {
            search()
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

    /**
     * Set up input method action for the edit text.
     */
    private fun initSearch() {
        binding.etSearchQueryInput.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    search()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Starts search based on query in edit text input, navigates to [SearchResultFragment].
     */
    private fun search() {
        Log.d(TAG, "Clicked search button")
        val queryInput = binding.etSearchQueryInput.text.toString()

        if (queryInput.isBlank()) {
            Toast.makeText(activity, getString(R.string.toast_emptyQuery), Toast.LENGTH_SHORT).show()
            return
        }

        if (searchViewModel.lastQueryValue() == null || searchViewModel.lastQueryValue() != queryInput) {

            val workRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<ClearCacheWorker>().build()
            WorkManager.getInstance().enqueue(workRequest)
            searchViewModel.search(queryInput)

        }

        Utils.hideSoftKeyboard(activity)

        val direction = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment()
        findNavController().navigate(direction)
    }

    /**
     * Cleans up the user input into a form fit for the query.
     */
    private fun filterInput(queryInput: String): String {

        return queryInput
    }

    companion object {
        private const val TAG = "FragmentHome"
    }

}

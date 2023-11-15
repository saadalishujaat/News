package com.example.bboxnewstask.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bboxnewstask.R
import com.example.bboxnewstask.databinding.FragmentHomeBinding
import com.example.bboxnewstask.network.ApiState
import com.example.bboxnewstask.ui.fragments.adapters.HeadlinesAdapter
import com.example.bboxnewstask.ui.fragments.viewmodels.HeadlinesViewModel
import com.example.bboxnewstask.utils.ExtensionFunctions.toTimeAgo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback


    private val headlinesViewModel: HeadlinesViewModel by activityViewModels()

    private lateinit var headlinesAdapter: HeadlinesAdapter

    /**OVERRIDES**/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val headlinesViewModel =
            ViewModelProvider(this).get(HeadlinesViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newsSource.text = "bbc-news"

        setupAdapter()
        return root
    }

    override fun onResume() {
        super.onResume()
        pandaBackPress()
    }
    override fun onPause() {
        super.onPause()
        if (this::callback.isInitialized) {
            callback.isEnabled = false
            callback.remove()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::callback.isInitialized) {
            callback.isEnabled = false
            callback.remove()
        }
        _binding = null
    }


    /**Functions*/
    private fun setupAdapter() {
        if (!::headlinesAdapter.isInitialized) {
            headlinesAdapter = HeadlinesAdapter {
                // on click callback from adapter item

                headlinesViewModel.currentArticle = it
                if(findNavController().currentDestination?.id == R.id.navigation_home) {
                    findNavController().navigate(R.id.action_navigation_home_to_navigation_news)
                }
            }
        }
        binding.headlinesRv.adapter = headlinesAdapter
        observers()

    }
    private fun observers() {

        headlinesViewModel.getHeadlines("bbc-news")
        lifecycleScope.launchWhenStarted {
            headlinesViewModel.headlineStateFlow.collect { state ->
                when (state) {
                    is ApiState.Loading -> {

                    }

                    is ApiState.Success -> {
                        Log.d("testResult", "SuccessChannelsData: ${state.headlines.articles.last().publishedAt.toTimeAgo() } ")
                        headlinesAdapter.submitList(state.headlines.articles)
                    }

                    is ApiState.Failure -> {
                        binding.apply {

                            Log.d("testResult", "failed: ${state.error.toString()}\n\n")

                        }
                    }

                    is ApiState.Empty -> {
                        binding.apply {
                            Log.d("testResult", "observers: empty")
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * Handles back press
     */
    private fun pandaBackPress() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}
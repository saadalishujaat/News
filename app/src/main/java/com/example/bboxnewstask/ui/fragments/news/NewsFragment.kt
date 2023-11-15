package com.example.bboxnewstask.ui.fragments.news

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.bboxnewstask.databinding.FragmentNewsBinding
import com.example.bboxnewstask.models.Article
import com.example.bboxnewstask.ui.fragments.viewmodels.HeadlinesViewModel
import com.example.bboxnewstask.utils.ExtensionFunctions.toTimeAgo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var article: Article

    private val headlinesViewModel: HeadlinesViewModel by activityViewModels()


    /**Overrides*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)


//        val article = NewsFragmentArgs.fromBundle(requireArguments()).article

        setupUI()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**Functions*/
    private fun setupUI() {


        headlinesViewModel.currentArticle?.let {
            CoroutineScope(Dispatchers.IO).launch {
                Glide.with(binding.image.context)
                    .load(it.urlToImage)
                    .also {
                        withContext(Dispatchers.Main) {
                            it.into(binding.image)
                        }
                    }
            }

            binding.sourceName.text = it.source.name
            binding.publishedAt.text = it.publishedAt.toTimeAgo()
            binding.title.text = it.title
            binding.description.text = it.description
            binding.content.text = it.content
            binding.url.text = it.url
            binding.url.movementMethod = LinkMovementMethod.getInstance()
        }

    }
}
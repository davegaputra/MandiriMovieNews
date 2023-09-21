package com.example.mandirimovienews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mandirimovienews.R
import com.example.mandirimovienews.adapter.NewsAdapter
import com.example.mandirimovienews.adapter.TrendingAdapter
import com.example.mandirimovienews.databinding.FragmentNewsBinding
import com.example.mandirimovienews.databinding.ItemNewsCategoryBinding
import com.example.mandirimovienews.databinding.ItemNewsTrendingBinding
import com.example.mandirimovienews.model.Article
import dagger.hilt.android.AndroidEntryPoint
import com.example.mandirimovienews.util.Resource
import com.example.mandirimovienews.util.Tools
import com.example.mandirimovienews.view.activity.HomeActivity
import com.example.mandirimovienews.view_model.NewsViewModel
import com.google.gson.Gson
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.NewsClickListener,
    TrendingAdapter.TrendingClickListener {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentNewsBinding

    @Inject lateinit var adapterTrending: TrendingAdapter
    @Inject lateinit var adapterCategory: NewsAdapter

    private val newsCategoryPage = 1
    private val newsTrendingPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        Tools.setStatusBarTransparent(requireActivity())
        Tools.setMargins(
            binding.cardViewSearch,
            left = Tools.dpToPx(requireContext(), 16),
            top = Tools.dpToPx(requireContext(), 16) + Tools.getStatusBarHeight(requireContext()),
            right = 0,
            bottom = 0
        )

        HomeActivity.animate(false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            edtSearch.setOnClickListener {
                val action = NewsFragmentDirections.actionNewsFragmentToSearchNewsFragment()
                findNavController().navigate(action)
            }

            radioGroup.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.radioBusiness -> {
                        getNewsCategory("business", newsCategoryPage)
                    }
                    R.id.radioEntertainment -> {
                        getNewsCategory("entertainment", newsCategoryPage)
                    }
                    R.id.radioGeneral -> {
                        getNewsCategory("general", newsCategoryPage)
                    }
                    R.id.radioHealth -> {
                        getNewsCategory("health", newsCategoryPage)
                    }
                    R.id.radioScience -> {
                        getNewsCategory("science", newsCategoryPage)
                    }
                    R.id.radioSports -> {
                        getNewsCategory("sports", newsCategoryPage)
                    }
                    else -> {
                        getNewsCategory("technology", newsCategoryPage)
                    }
                }
            }

            getNewsCategory("business", newsCategoryPage)
            getNewsTrending(newsTrendingPage)
        }
    }

    private fun getNewsCategory(category: String, page: Int) {
        viewModel.getHeadlinesNews(category, page)
        viewModel.headlineNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    isLoadingCategory(false)
                    response.data?.let {
                        with(adapterCategory) {
                            binding.recyclerViewCategory.apply {
                                adapter = this@with
                            }
                            differ.submitList(it)
                            newsClickListener = this@NewsFragment
                        }
                    }
                }
                is Resource.Error -> {
                    isLoadingCategory(false)
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    isLoadingCategory(true)
                }
            }
        }
    }

    private fun isLoadingCategory(bool: Boolean) {
        binding.apply {
            shimmerCategory.visibility = if (bool) View.VISIBLE else View.GONE
            recyclerViewCategory.visibility = if (bool) View.GONE else View.VISIBLE
        }
    }

    private fun getNewsTrending(page: Int) {
        viewModel.getTrendingNews(page)
        viewModel.trendingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    isLoadingTrending(false)
                    response.data?.let {
                        with(adapterTrending) {
                            binding.recyclerViewTrending.apply {
                                adapter = this@with
                            }
                            differ.submitList(it)
                            trendingClickListener = this@NewsFragment
                        }
                    }
                }
                is Resource.Error -> {
                    isLoadingTrending(false)
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    isLoadingTrending(true)
                }
            }
        }
    }

    private fun isLoadingTrending(bool: Boolean) {
        binding.apply {
            shimmerTrending.visibility = if (bool) View.VISIBLE else View.GONE
            recyclerViewTrending.visibility = if (bool) View.GONE else View.VISIBLE
        }
    }

    private fun showNewsDetails(news: Article) {
        val gson = Gson()
        val jsonArticle : String = gson.toJson(news)

        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(jsonArticle)
        findNavController().navigate(action)

//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(news.url)
//        startActivity(intent)
    }

    override fun onNewsClicked(binding: ItemNewsCategoryBinding, article: Article) {
        showNewsDetails(article)
    }

    override fun onTrendingClicked(binding: ItemNewsTrendingBinding, article: Article) {
        showNewsDetails(article)
    }

}
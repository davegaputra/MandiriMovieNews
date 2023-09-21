package com.example.mandirimovienews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mandirimovienews.adapter.NewsPagingAdapter
import com.example.mandirimovienews.databinding.FragmentSearchNewsBinding
import com.example.mandirimovienews.databinding.ItemNewsTrendingBinding
import com.example.mandirimovienews.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.mandirimovienews.util.PagingLoadStateAdapter
import com.example.mandirimovienews.util.Tools
import com.example.mandirimovienews.util.Tools.Companion.hideKeyboard
import com.example.mandirimovienews.view.activity.HomeActivity
import com.example.mandirimovienews.view_model.SearchNewsViewModel
import com.google.gson.Gson
import javax.inject.Inject

@AndroidEntryPoint
class SearchNewsFragment : Fragment(), NewsPagingAdapter.NewsClickListener {

    companion object {
        fun newInstance() = SearchNewsFragment()
    }

    private val viewModel: SearchNewsViewModel by viewModels()
    private lateinit var binding: FragmentSearchNewsBinding

    @Inject lateinit var mAdapter: NewsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)

        Tools.setStatusBarTransparent(requireActivity())
        Tools.setMargins(
            binding.back,
            left = Tools.dpToPx(requireContext(), 16),
            top = 0,
            right = 0,
            bottom = 0
        )
        Tools.setMargins(
            binding.cardViewSearch,
            left = Tools.dpToPx(requireContext(), 16),
            top = Tools.dpToPx(requireContext(), 16) + Tools.getStatusBarHeight(requireContext()),
            right = Tools.dpToPx(requireContext(), 16),
            bottom = 0
        )
        HomeActivity.animate(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            back.setOnClickListener {
                findNavController().popBackStack()
            }

            mAdapter.apply {
                recyclerViewSearch.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )
                newsClickListener = this@SearchNewsFragment
            }

            edtSearch.setOnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    searchNews(edtSearch.text.toString().trim())
                }
                false
            }
        }
    }

    private fun searchNews(keyword: String) {
        view?.let { context?.hideKeyboard(it) }
        lifecycleScope.launch {
            viewModel.getSearchNews(keyword = keyword)
        }
        lifecycleScope.launch {
            viewModel.newsFlow.collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun showNewsDetail(article: Article) {
        val gson = Gson()
        val jsonArticle = gson.toJson(article)

        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToNewsDetailFragment(jsonArticle)
        findNavController().navigate(action)

//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(article.url)
//        startActivity(intent)
    }

    override fun onNewsClicked(binding: ItemNewsTrendingBinding, article: Article) {
        showNewsDetail(article)
    }

}
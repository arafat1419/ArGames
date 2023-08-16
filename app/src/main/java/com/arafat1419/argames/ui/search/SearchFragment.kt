package com.arafat1419.argames.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arafat1419.argames.BaseFragment
import com.arafat1419.argames.core.ui.paging.GamesPagingAdapter
import com.arafat1419.argames.core.utils.CommonUtils
import com.arafat1419.argames.core.utils.CommonUtils.showKeyboard
import com.arafat1419.argames.databinding.FragmentSearchBinding
import com.arafat1419.argames.ui.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    private val viewModel: MainViewModel by viewModel()

    private val gamesPagingAdapter: GamesPagingAdapter by lazy {
        GamesPagingAdapter()
    }

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            CommonUtils.setRecyclerView(rvGames, LinearLayoutManager(context), gamesPagingAdapter)

            edtSearch.let { editText ->
                editText.requestFocus()
                editText.showKeyboard()

                editText.doAfterTextChanged { text ->
                    searchRunnable?.let { searchHandler.removeCallbacks(it) }

                    searchRunnable = Runnable {
                        getFavoriteGamesId {listId ->
                            gamesPagingAdapter.setData(listId)
                            searchGames(text.toString())
                        }
                    }
                    searchHandler.postDelayed(searchRunnable!!, 300L)
                }
            }
        }
    }

    override fun onClickHandler() {
        gamesPagingAdapter.onItemClicked = {
            if (it.id != null) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.id!!)
                )
            }
        }

        gamesPagingAdapter.onFavoriteClicked = { data, isFavorite ->
            if (isFavorite) viewModel.deleteGame(data) else viewModel.insertGame(data)
        }
    }

    private fun searchGames(search: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchGames(search).collectLatest {
                gamesPagingAdapter.submitData(it)
            }
        }
    }

    private fun getFavoriteGamesId(onSuccess: (listId: List<Int>) -> Unit) {
        viewModel.getFavoriteGamesId().observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                onSuccess(it)
            }
        }
    }

    override fun onDestroyView() {
        searchRunnable?.let { searchHandler.removeCallbacks(it) }
        super.onDestroyView()
    }
}
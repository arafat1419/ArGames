package com.arafat1419.argames.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arafat1419.argames.BaseFragment
import com.arafat1419.argames.core.ui.basic.GamesAdapter
import com.arafat1419.argames.core.utils.CommonUtils
import com.arafat1419.argames.databinding.FragmentHomeBinding
import com.arafat1419.argames.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: MainViewModel by viewModel()

    private val gamesAdapter: GamesAdapter by lazy {
        GamesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CommonUtils.setRecyclerView(binding.rvGames, LinearLayoutManager(requireContext()), gamesAdapter)
        getListGames()
    }

    override fun onClickHandler() {
        with(binding) {
            edtSearch.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                )
            }

            gamesAdapter.onItemClicked = {
                if (it.id != null) {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
                    )
                }
            }

            gamesAdapter.onFavoriteClicked = { data, isFavorite ->
                if (isFavorite) viewModel.deleteGame(data) else viewModel.insertGame(data)
            }
        }
    }

    private fun getListGames() {
        viewModel.listGames().observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (it.results == null) return@handle

                getFavoriteGamesId {listId ->
                    gamesAdapter.setData(it.results, listId)
                }
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
}
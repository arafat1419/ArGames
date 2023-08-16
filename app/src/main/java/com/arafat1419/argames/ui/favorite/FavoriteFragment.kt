package com.arafat1419.argames.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arafat1419.argames.BaseFragment
import com.arafat1419.argames.R
import com.arafat1419.argames.core.ui.basic.GamesAdapter
import com.arafat1419.argames.core.utils.CommonUtils
import com.arafat1419.argames.databinding.FragmentFavoriteBinding
import com.arafat1419.argames.databinding.FragmentHomeBinding
import com.arafat1419.argames.ui.MainViewModel
import com.arafat1419.argames.ui.home.HomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
){
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
        gamesAdapter.onItemClicked = {
            if (it.id != null) {
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteFragment2ToDetailFragment(it.id!!)
                )
            }
        }

        gamesAdapter.onFavoriteClicked = { data, isFavorite ->
            if (isFavorite) viewModel.deleteGame(data) else viewModel.insertGame(data)
        }
    }

    private fun getListGames() {
        viewModel.getFavoriteGames().observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                getFavoriteGamesId {listId ->
                    gamesAdapter.setData(it, listId)
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
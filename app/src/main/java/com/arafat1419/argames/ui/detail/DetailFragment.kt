package com.arafat1419.argames.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arafat1419.argames.BaseFragment
import com.arafat1419.argames.assets.R
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.ui.basic.ImageSliderAdapter
import com.arafat1419.argames.databinding.FragmentDetailBinding
import com.arafat1419.argames.ui.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(
    FragmentDetailBinding::inflate
) {
    private val viewModel: MainViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()
    }

    override fun onClickHandler() {
        with(binding) {
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun showData() {
        viewModel.gameDetail(args.gameId).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) { game ->
                with(binding) {
                    txtTitle.text = game.name
                    txtPublisher.text = getString(
                        R.string.by_publisher_format,
                        if (game.publishers.isNullOrEmpty()) "-" else game.publishers!![0].name
                    )
                    txtRelease.text = getString(
                        R.string.release_format,
                        game.released ?: "-"
                    )
                    txtDesc.text = Html.fromHtml(game.description, Html.FROM_HTML_MODE_LEGACY)
                    txtRating.text = game.rating
                    txtPlayed.text = game.playtime

                    getScreenshots { listScreenshots ->
                        val imageSliderAdapter = ImageSliderAdapter(listScreenshots.map {
                            it.image.toString()
                        })

                        vpScreenshots.adapter = imageSliderAdapter
                    }

                    isFavorite {
                        btnFavorite.apply {
                            if (it) {
                                setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_favorite_24
                                    )
                                )
                                setOnClickListener {
                                    viewModel.deleteGame(game)
                                }
                            } else {
                                setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_favorite_border_24
                                    )
                                )
                                setOnClickListener {
                                    viewModel.insertGame(game)
                                }
                            }
                        }
                    }

                    btnBrowser.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(game.website))
                        startActivity(browserIntent)
                    }
                }
            }
        }
    }

    private fun getScreenshots(onSuccess: (listScreenshots: List<ScreenshotDomain>) -> Unit) {
        viewModel.gameScreenshots(args.gameId).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (it.results == null) return@handle
                onSuccess(it.results!!)
            }
        }
    }

    private fun isFavorite(onSuccess: (state: Boolean) -> Unit) {
        viewModel.isGamesFavorite(args.gameId).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                onSuccess(it)
            }
        }
    }
}
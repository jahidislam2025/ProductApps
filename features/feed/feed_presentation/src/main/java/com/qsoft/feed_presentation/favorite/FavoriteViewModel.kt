package com.qsoft.feed_presentation.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qsoft.feed_domain.use_case.GetFavoritesProductsUseCase
import com.qsoft.feed_domain.use_case.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getFavoritesProductsUseCase: GetFavoritesProductsUseCase
) : ViewModel() {

    var state by mutableStateOf(FavoriteState())
        private set

    init {
        loadLocalData()
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            getFavoritesProductsUseCase().collectLatest { products ->
                state = state.copy(productsList = products)

            }
        }
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnFavoriteClickEvent -> {
                viewModelScope.launch {
                    updateFavoriteUseCase(
                        productId = event.productId,
                        isFavorite = event.isFavorite
                    )
                }
            }
        }
    }
}
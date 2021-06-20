package com.vegan.ui.refrigerator

import androidx.lifecycle.MutableLiveData
import com.vegan.domain.ProductsInteractor
import com.vegan.models.Product
import com.vegan.models.ProductModel
import com.vegan.ui.base.BaseViewModel
import com.vegan.ui.base.addTo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


@HiltViewModel
class RefrigeratorViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor
) : BaseViewModel() {

    val screenState = MutableLiveData<List<ProductModel>>()
    val saveButtonState = MutableLiveData(false)

    val progressState = MutableLiveData<ScreenState>()

    private lateinit var productModelsSubject: List<ProductModel>
    private lateinit var originalProductModelsSubject: List<ProductModel>
    private val productModelSubject = BehaviorSubject.create<ProductModel>()

    fun loadUserProducts(isRefrigerator: Boolean) {
        userProducts(
            if (isRefrigerator) productsInteractor.userRefrigerator()
            else productsInteractor.userStopList()
        )
    }

    init {
        productModelSubject.map { newProductModel ->
            createNewStateProductModels(newProductModel)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { newProductModels ->
                productModelsSubject = newProductModels
                saveButtonState.value = !newProductModels.containsAll(originalProductModelsSubject)
            }
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

    private fun userProducts(userProducts: Maybe<List<ProductModel>>) {
        userProducts
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressState.value = ScreenState.SHOW_LOADING_CONTENT_PROGRESS }
            .doOnSuccess {
                originalProductModelsSubject = it
                productModelsSubject = it
            }
            .doOnSuccess { progressState.value = ScreenState.SHOW_CONTENT }
            .subscribe(screenState::setValue)
            .addTo(compositeDisposable)
    }

    private fun createNewStateProductModels(newProductModel: ProductModel) =
        productModelsSubject.map { oldProductModel ->
            if (oldProductModel.product.id == newProductModel.product.id) {
                ProductModel(newProductModel.product, !oldProductModel.isPicked)
            } else {
                oldProductModel
            }
        }

    fun onProductClick(model: ProductModel) {
        productModelSubject.onNext(model)
    }

    fun onSaveButtonClick(isRefrigerator: Boolean) {
        val ids = mapToProductIds()
        val saveTo = if (isRefrigerator) productsInteractor.saveRefrigerator(ids)
        else productsInteractor.saveStopList(ids)
        saveTo
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressState.value = ScreenState.SHOW_SAVE_PROGRESS }
            .doOnComplete { progressState.value = ScreenState.SAVED }
            .subscribe()
            .addTo(compositeDisposable)
    }


    private fun mapToProductIds() = productModelsSubject
        .filter(ProductModel::isPicked)
        .map(ProductModel::product)
        .map(Product::id)

    enum class ScreenState {

        SHOW_LOADING_CONTENT_PROGRESS,
        SHOW_CONTENT,
        SHOW_SAVE_PROGRESS,
        SAVED

    }
}
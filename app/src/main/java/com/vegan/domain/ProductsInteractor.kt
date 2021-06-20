package com.vegan.domain

import com.vegan.data.ProductsRepository
import com.vegan.data.UserRepository
import com.vegan.models.ProductModel
import io.reactivex.Maybe
import javax.inject.Inject


class ProductsInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val productsRepository: ProductsRepository
) {

    fun userRefrigerator() = Maybe.zip(
        userRepository.user(),
        productsRepository.refrigeratorProducts(),
        { user, products ->
            products.map { product ->
                ProductModel(
                    product,
                    user.refrigerator.contains(product.id)
                )
            }
        }
    )

    fun userStopList() = Maybe.zip(
        userRepository.user(),
        productsRepository.stopListProducts(),
        { user, products ->
            products.map { product ->
                ProductModel(
                    product,
                    user.stopList.contains(product.id)
                )
            }
        }
    )

    fun saveRefrigerator(pickedIds: List<String>) = userRepository.updateRefrigerator(pickedIds)

    fun saveStopList(pickedIds: List<String>) = userRepository.updateStopList(pickedIds)

}
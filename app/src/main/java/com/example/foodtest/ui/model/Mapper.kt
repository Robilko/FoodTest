package com.example.foodtest.ui.model

import com.example.foodtest.data.api.model.ProductDTO
import com.example.foodtest.utils.ui.Category

object ProductDtoToUiMapper {
    operator fun invoke(
        productList: List<ProductDTO>, category: Category
    ) : List<ProductItem> {
        return productList.map { productDTO ->
            ProductItem(
                id = productDTO.id,
                imageUrl = productDTO.img,
                price = "${productDTO.price} \$",
                title = productDTO.name,
                description = productDTO.dsc,
                category = category
            )
        }
    }
}
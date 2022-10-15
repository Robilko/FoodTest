package com.example.foodtest.ui.model

import com.example.foodtest.data.api.model.ProductDTO
import com.example.foodtest.data.db.entity.ProductEntity
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

object ProductItemToDbMapper {
    operator fun invoke(
        productList: List<ProductItem>
    ) : List<ProductEntity> {
        return productList.map { productItem ->
            ProductEntity(
                id = productItem.id,
                imageUrl = productItem.imageUrl,
                price = productItem.price,
                title = productItem.title,
                description = productItem.description,
                category = productItem.category
            )
        }
    }
}

object ProductEntityToUiMapper {
    operator fun invoke(
        productList: List<ProductEntity>
    ) : List<ProductItem> {
        return productList.map { productItem ->
            ProductItem(
                id = productItem.id,
                imageUrl = productItem.imageUrl,
                price = productItem.price,
                title = productItem.title,
                description = productItem.description,
                category = productItem.category
            )
        }
    }
}
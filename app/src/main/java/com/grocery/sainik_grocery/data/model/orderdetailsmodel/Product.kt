package com.grocery.sainik_grocery.data.model.orderdetailsmodel


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("brandId")
    val brandId: String,
    @SerializedName("brandName")
    val brandName: Any?,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("categoryName")
    val categoryName: Any?,
    @SerializedName("code")
    val code: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Any?,
    @SerializedName("id")
    val id: String,
    @SerializedName("mrp")
    val mrp: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("productTaxes")
    val productTaxes: List<Any>,
    @SerializedName("productUrl")
    val productUrl: String,
    @SerializedName("purchasePrice")
    val purchasePrice: Double,
    @SerializedName("qrCodeUrl")
    val qrCodeUrl: Any?,
    @SerializedName("salesPrice")
    val salesPrice: Double,
    @SerializedName("skuCode")
    val skuCode: String,
    @SerializedName("skuName")
    val skuName: String,
    @SerializedName("unit")
    val unit: Any?,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: Any?,
    @SerializedName("warehouseId")
    val warehouseId: Any?,
    @SerializedName("warehouseName")
    val warehouseName: Any?
)
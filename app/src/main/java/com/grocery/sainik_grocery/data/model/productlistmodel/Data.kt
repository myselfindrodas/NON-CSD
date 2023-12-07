package com.grocery.sainik_grocery.data.model.productlistmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("brandId")
    val brandId: String,
    @SerializedName("stock")
    val stock: String,
    @SerializedName("brandName")
    val brandName: String,
    @SerializedName("cart")
    val cart: Any?,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("isProductOrderTime")
    val isProductOrderTime: Boolean,
    @SerializedName("mrp")
    val mrp: Double?,
    @SerializedName("name")
    val name: String,
    @SerializedName("orderEndTime")
    val orderEndTime: String?,
    @SerializedName("orderStartTime")
    val orderStartTime: String?,
    @SerializedName("productCategory")
    val productCategory: Any?,
    @SerializedName("productTaxes")
    val productTaxes: List<Any>,
    @SerializedName("productUrl")
    val productUrl: String,
    @SerializedName("purchasePrice")
    val purchasePrice: Double?,
    @SerializedName("qrCodeUrl")
    val qrCodeUrl: Any?,
    @SerializedName("rackNo")
    val rackNo: String?,
    @SerializedName("salesPrice")
    val salesPrice: String?,
    @SerializedName("skuCode")
    val skuCode: String,
    @SerializedName("skuName")
    val skuName: String,
    @SerializedName("unit")
    val unit: Unit,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: String,
    @SerializedName("warehouseId")
    val warehouseId: Any?,
    @SerializedName("warehouseName")
    val warehouseName: Any?
)
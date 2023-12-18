package com.grocery.sainik_grocery.data.model.productdetailsmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("brandId")
    val brandId: String,
    @SerializedName("brandName")
    val brandName: Any?,
    @SerializedName("cart")
    val cart: Any?,
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
    val discount: Double?,
    @SerializedName("hsnCode")
    val hsnCode: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("inventory")
    val inventory: Inventory,
    @SerializedName("isProductOrderTime")
    val isProductOrderTime: Boolean,
    @SerializedName("mainCategoryId")
    val mainCategoryId: Any?,
    @SerializedName("mainCategoryName")
    val mainCategoryName: Any?,
    @SerializedName("margin")
    val margin: String,
    @SerializedName("mrp")
    val mrp: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("orderEndTime")
    val orderEndTime: String,
    @SerializedName("orderStartTime")
    val orderStartTime: String,
    @SerializedName("productCategory")
    val productCategory: Any?,
    @SerializedName("productTaxes")
    val productTaxes: List<Any>,
    @SerializedName("productUrl")
    val productUrl: Any?,
    @SerializedName("purchasePrice")
    val purchasePrice: Double,
    @SerializedName("qrCodeUrl")
    val qrCodeUrl: Any?,
    @SerializedName("rackNo")
    val rackNo: String,
    @SerializedName("salesPrice")
    val salesPrice: String,
    @SerializedName("skuCode")
    val skuCode: String,
    @SerializedName("skuName")
    val skuName: String,
    @SerializedName("stock")
    val stock: Any?,
    @SerializedName("unit")
    val unit: Any?,
    @SerializedName("unitId")
    val unitId: String,
    @SerializedName("unitName")
    val unitName: Any?,
    @SerializedName("warehouseId")
    val warehouseId: String,
    @SerializedName("warehouseName")
    val warehouseName: Any?
)
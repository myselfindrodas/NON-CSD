package com.grocery.sainik_grocery.data.repository

import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressAddRequest
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressEditRequest
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
import com.grocery.sainik_grocery.data.model.categorymodel.CategoryRequest
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartRequest
import com.grocery.sainik_grocery.data.model.deletefullcartmodel.DeleteCustomerCartRequest
import com.grocery.sainik_grocery.data.model.deletewishlistmodel.DeletewishlistRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.getwishlistmodel.WishlistRequest
import com.grocery.sainik_grocery.data.model.loginmodel.LoginRequest
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.OrderdetailsRequest
import com.grocery.sainik_grocery.data.model.orderlistmodel.OrderlistRequest
import com.grocery.sainik_grocery.data.model.ordersummerymodel.OrdersummeryRequest
import com.grocery.sainik_grocery.data.model.otpverifymodel.OtpverifyRequest
import com.grocery.sainik_grocery.data.model.postordermodel.PostOrderRequest
import com.grocery.sainik_grocery.data.model.productdetailsmodel.ProductDetailsRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.profilemodel.GetProfileRequest
import com.grocery.sainik_grocery.data.model.salesordermodel.SalesOrderPaymentRequest
import com.grocery.sainik_grocery.data.model.searchmodel.SearchRequest
import com.grocery.sainik_grocery.data.model.setprimaryaddressmodel.PrimaryAddressRequest
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.model.updateprofilemodel.UpdateProfileRequest
import com.grocery.sainik_grocery.data.model.wishlistaddmodel.WishListAddRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun generatetoken(requestBody: TokenRequest) = apiHelper.generatetoken(requestBody)
    suspend fun login(requestBody: LoginRequest) = apiHelper.login(requestBody)
    suspend fun otpverify(requestBody: OtpverifyRequest) = apiHelper.otpverify(requestBody)
    suspend fun getnonCSDlist() = apiHelper.getnonCSDlist()
    suspend fun GetNewOrderNumber() = apiHelper.GetNewOrderNumber()

    suspend fun LoginPageBanners() = apiHelper.LoginPageBanners()

    suspend fun categorylist(requestBody: CategoryRequest) = apiHelper.categorylist(requestBody)
    suspend fun SalesOrderPayment(requestBody: SalesOrderPaymentRequest) = apiHelper.SalesOrderPayment(requestBody)


    suspend fun ProductMainCategoriesList() = apiHelper.ProductMainCategoriesList()

    suspend fun addresslist(customerid:String) = apiHelper.addresslist(customerid)
    suspend fun notificationlist(customerid:String) = apiHelper.notificationlist(customerid)
    suspend fun banners() = apiHelper.banners()
    suspend fun productList(requestBody: ProductListRequest) = apiHelper.productList(requestBody)
    suspend fun productDetails(requestBody: ProductDetailsRequest) = apiHelper.productDetails(requestBody)
    suspend fun AddToCart(requestBody: AddtocartRequest) = apiHelper.AddToCart(requestBody)
    suspend fun CartList(requestBody: CartListRequest) = apiHelper.CartList(requestBody)
    suspend fun addtowishlist(requestBody: WishListAddRequest) = apiHelper.addtowishlist(requestBody)
    suspend fun orderdetails(requestBody: OrderdetailsRequest) = apiHelper.orderdetails(requestBody)
    suspend fun orderlist(requestBody: OrderlistRequest) = apiHelper.orderlist(requestBody)
    suspend fun addressadd(requestBody: AddressAddRequest) = apiHelper.addressadd(requestBody)
    suspend fun editaddress(id: String, requestBody: AddressEditRequest) = apiHelper.editaddress(id, requestBody)
    suspend fun wishlist(requestBody: WishlistRequest) = apiHelper.wishlist(requestBody)
    suspend fun postorder(requestBody: PostOrderRequest) = apiHelper.postorder(requestBody)

    suspend fun search(requestBody: SearchRequest) = apiHelper.search(requestBody)

    suspend fun getProfile(requestBody: GetProfileRequest) = apiHelper.getProfile(requestBody)
    suspend fun updateprofile(requestBody: UpdateProfileRequest) = apiHelper.updateprofile(requestBody)

    suspend fun support() = apiHelper.support()
    suspend fun faq() = apiHelper.faq()

    suspend fun updatecart(requestBody: CartUpdateRequest) = apiHelper.updatecart(requestBody)
    suspend fun deletecart(requestBody: DeleteCartRequest) = apiHelper.deletecart(requestBody)
    suspend fun DeleteCustomerCart(requestBody: DeleteCustomerCartRequest) = apiHelper.DeleteCustomerCart(requestBody)

    suspend fun deletewishlist(requestBody: DeletewishlistRequest) = apiHelper.deletewishlist(requestBody)
    suspend fun ordersummery(requestBody: OrdersummeryRequest) = apiHelper.ordersummery(requestBody)
    suspend fun deleteaddress(id:String) = apiHelper.deleteaddress(id)
    suspend fun primaryaddress(id:String, requestBody: PrimaryAddressRequest) = apiHelper.primaryaddress(id, requestBody)

//    suspend fun login(requestBody: LoginRequestModel) = apiHelper.login(requestBody)
//    suspend fun urclist(requestBody: UrcRequestModel) = apiHelper.urclist(requestBody)
//    suspend fun categoryall(requestBody: CategoryRequestModel) = apiHelper.categoryall(requestBody)
//    suspend fun getAllProduct(requestBody: ProductListRequestModel,page:String) = apiHelper.getAllProduct(requestBody,page)
//    suspend fun getProductDetails(id:String) = apiHelper.getProductDetails(id)
//    suspend fun dashboard(requestBody: DashboardRequestModel) = apiHelper.dashboard(requestBody)
//    suspend fun logout() = apiHelper.logout()
//    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiHelper.forgotpassword(requestBody)
//    suspend fun cartadd(requestBody: CartRequestModel) = apiHelper.cartadd(requestBody)
//    suspend fun cartDelete(id:String) = apiHelper.cartDelete(id)
//    suspend fun cartList() = apiHelper.cartList()
//    suspend fun addressList() = apiHelper.addressList()
//    suspend fun addAddress(requestBody: AddAddressRequestModel) = apiHelper.addAddress(requestBody)
//    suspend fun editAddress(requestBody: AddAddressRequestModel) = apiHelper.editAddress(requestBody)
//    suspend fun deleteAddress(id:String) = apiHelper.deleteAddress(id)
//    suspend fun primaryAddress(id:String) = apiHelper.primaryAddress(id)
//    suspend fun addtoWishlist(requestBody: AddWishlistRequestModel) = apiHelper.addtoWishlist(requestBody)
//    suspend fun wishlist() = apiHelper.wishlist()
//    suspend fun wishlistDelete(id:String) = apiHelper.wishlistDelete(id)
//    suspend fun notificationlist() = apiHelper.notificationlist()
//    suspend fun addpaymentcard(requestBody: AddcardRequestModel) = apiHelper.addpaymentcard(requestBody)
//    suspend fun paymentcardlist() = apiHelper.paymentcardlist()
//    suspend fun paymentcardPrimary(id:String) = apiHelper.paymentcardPrimary(id)
//    suspend fun paymentcardDelete(id:String) = apiHelper.paymentcardDelete(id)
//    suspend fun addOrderDetails(requestBody: AddOrderRequestModel) = apiHelper.addOrderDetails(requestBody)
//    suspend fun orderSummeryDetails() = apiHelper.orderSummeryDetails()
//    suspend fun myOrderList() = apiHelper.myOrderList()
//    suspend fun orderDetails(id:String) = apiHelper.orderDetails(id)
//    suspend fun getSupportAndFAQ(id:String) = apiHelper.getSupportAndFAQ(id)
//    suspend fun orderpayment(requestBody: OrderPaymentRequestModel) = apiHelper.orderpayment(requestBody)
//
//    suspend fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = apiHelper.profileupdate(name, last_name, phone, gender, birthday, password, old_password, part)
//
//    suspend fun profileget() = apiHelper.profileget()
//    suspend fun filterResponse() = apiHelper.filterResponse()
//    suspend fun returnDelivery(requestBody: ReturnDeliveryRequestModel) = apiHelper.returnDelivery(requestBody)
//
}
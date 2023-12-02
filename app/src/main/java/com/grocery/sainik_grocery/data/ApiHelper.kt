package com.grocery.sainik_grocery.data

import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressAddRequest
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressEditRequest
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
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
import com.grocery.sainik_grocery.data.model.searchmodel.SearchRequest
import com.grocery.sainik_grocery.data.model.setprimaryaddressmodel.PrimaryAddressRequest
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.model.updateprofilemodel.UpdateProfileRequest
import com.grocery.sainik_grocery.data.model.wishlistaddmodel.WishListAddRequest


class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun generatetoken(requestBody: TokenRequest) = apiInterface.generatetoken(requestBody)
    suspend fun login(requestBody: LoginRequest) = apiInterface.login(requestBody)
    suspend fun otpverify(requestBody: OtpverifyRequest) = apiInterface.otpverify(requestBody)
    suspend fun getnonCSDlist() = apiInterface.getnonCSDlist()
    suspend fun GetNewOrderNumber() = apiInterface.GetNewOrderNumber()
    suspend fun categorylist() = apiInterface.categorylist()
    suspend fun addresslist(customerid:String) = apiInterface.addresslist(customerid)
    suspend fun notificationlist(customerid:String) = apiInterface.notificationlist(customerid)
    suspend fun productList(requestBody: ProductListRequest) = apiInterface.productList(requestBody)
    suspend fun productDetails(requestBody: ProductDetailsRequest) = apiInterface.productDetails(requestBody)
    suspend fun AddToCart(requestBody: AddtocartRequest) = apiInterface.AddToCart(requestBody)
    suspend fun CartList(requestBody: CartListRequest) = apiInterface.CartList(requestBody)
    suspend fun addtowishlist(requestBody: WishListAddRequest) = apiInterface.addtowishlist(requestBody)
    suspend fun orderdetails(requestBody: OrderdetailsRequest) = apiInterface.orderdetails(requestBody)
    suspend fun orderlist(requestBody: OrderlistRequest) = apiInterface.orderlist(requestBody)
    suspend fun addressadd(requestBody: AddressAddRequest) = apiInterface.addressadd(requestBody)
    suspend fun editaddress(id: String, requestBody: AddressEditRequest) = apiInterface.editaddress(id, requestBody)
    suspend fun wishlist(requestBody: WishlistRequest) = apiInterface.wishlist(requestBody)
    suspend fun postorder(requestBody: PostOrderRequest) = apiInterface.postorder(requestBody)
    suspend fun search(requestBody: SearchRequest) = apiInterface.search(requestBody)
    suspend fun getProfile(requestBody: GetProfileRequest) = apiInterface.getProfile(requestBody)
    suspend fun updateprofile(requestBody: UpdateProfileRequest) = apiInterface.updateprofile(requestBody)
    suspend fun updatecart(requestBody: CartUpdateRequest) = apiInterface.updatecart(requestBody)
    suspend fun deletecart(requestBody: DeleteCartRequest) = apiInterface.deletecart(requestBody)
    suspend fun DeleteCustomerCart(requestBody: DeleteCustomerCartRequest) = apiInterface.DeleteCustomerCart(requestBody)
    suspend fun deletewishlist(requestBody: DeletewishlistRequest) = apiInterface.deletewishlist(requestBody)
    suspend fun ordersummery(requestBody: OrdersummeryRequest) = apiInterface.ordersummery(requestBody)
    suspend fun deleteaddress(id:String) = apiInterface.deleteaddress(id)
    suspend fun primaryaddress(id:String, requestBody: PrimaryAddressRequest) = apiInterface.primaryaddress(id, requestBody)

//    suspend fun login(requestBody: LoginRequestModel) = apiInterface.login(requestBody)
//    suspend fun urclist(requestBody: UrcRequestModel) = apiInterface.urclist(requestBody)
//    suspend fun categoryall(requestBody: CategoryRequestModel) = apiInterface.categoryall(requestBody)
//    suspend fun getAllProduct(requestBody: ProductListRequestModel,page:String) = apiInterface.productList(requestBody,page)
//    suspend fun getProductDetails(id:String) = apiInterface.getProductDetails(id)
//    suspend fun dashboard(requestBody: DashboardRequestModel) = apiInterface.dashboard(requestBody)
//    suspend fun logout() = apiInterface.logout()
//    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiInterface.forgotpassword(requestBody)
//    suspend fun cartadd(requestBody: CartRequestModel) = apiInterface.cartadd(requestBody)
//    suspend fun cartDelete(id:String) = apiInterface.cartDelete(id)
//    suspend fun cartList() = apiInterface.cartList()
//    suspend fun addressList() = apiInterface.addressList()
//    suspend fun addAddress(requestBody: AddAddressRequestModel) = apiInterface.addAddress(requestBody)
//    suspend fun editAddress(requestBody: AddAddressRequestModel) = apiInterface.editAddress(requestBody)
//    suspend fun deleteAddress(id:String) = apiInterface.addressDelete(id)
//    suspend fun primaryAddress(id:String) = apiInterface.addressPrimary(id)
//    suspend fun addtoWishlist(requestBody: AddWishlistRequestModel) = apiInterface.addtoWishlist(requestBody)
//    suspend fun wishlist() = apiInterface.wishlist()
//    suspend fun wishlistDelete(id:String) = apiInterface.wishlistDelete(id)
//    suspend fun notificationlist() = apiInterface.notificationlist()
//    suspend fun addpaymentcard(requestBody: AddcardRequestModel) = apiInterface.addpaymentcard(requestBody)
//    suspend fun paymentcardlist() = apiInterface.paymentcardlist()
//    suspend fun paymentcardPrimary(id:String) = apiInterface.paymentcardPrimary(id)
//    suspend fun paymentcardDelete(id:String) = apiInterface.paymentcardDelete(id)
//    suspend fun addOrderDetails(requestBody: AddOrderRequestModel) = apiInterface.addOrderDetails(requestBody)
//    suspend fun orderSummeryDetails() = apiInterface.orderSummeryDetails()
//    suspend fun myOrderList() = apiInterface.myOrderList()
//    suspend fun orderDetails(id:String) = apiInterface.orderDetails(id)
//    suspend fun getSupportAndFAQ(id:String) = apiInterface.getSupportAndFAQ(id)
//    suspend fun orderpayment(requestBody: OrderPaymentRequestModel) = apiInterface.orderpayment(requestBody)
//
//    suspend fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = apiInterface.profileupdate(name, last_name, phone, gender, birthday, password, old_password, part)
//
//
//    suspend fun profileget() = apiInterface.profileget()
//    suspend fun filterResponse() = apiInterface.filterResponse()
//    suspend fun returnDelivery(requestBody: ReturnDeliveryRequestModel) = apiInterface.returnDelivery(requestBody)


}
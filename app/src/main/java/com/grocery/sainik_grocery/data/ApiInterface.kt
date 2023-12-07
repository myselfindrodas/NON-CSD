package com.grocery.sainik_grocery.data


import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressAddRequest
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressAddResponse
import com.grocery.sainik_grocery.data.model.addressaddmodel.AddressEditRequest
import com.grocery.sainik_grocery.data.model.addresslistmodel.AddressListResponse
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartRequest
import com.grocery.sainik_grocery.data.model.addtocartmodel.AddtocartResponse
import com.grocery.sainik_grocery.data.model.bannermodel.HomeBannerResponse
import com.grocery.sainik_grocery.data.model.categorymodel.CategoryListResponse
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartRequest
import com.grocery.sainik_grocery.data.model.deletecartmodel.DeleteCartResponse
import com.grocery.sainik_grocery.data.model.deletefullcartmodel.DeleteCustomerCartRequest
import com.grocery.sainik_grocery.data.model.deletewishlistmodel.DeletewishlistRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListRequest
import com.grocery.sainik_grocery.data.model.getcartlistmodel.CartListResponse
import com.grocery.sainik_grocery.data.model.getwishlistmodel.WishlistRequest
import com.grocery.sainik_grocery.data.model.getwishlistmodel.WishlistResponse
import com.grocery.sainik_grocery.data.model.loginbannermodel.LoginBannerResponse
import com.grocery.sainik_grocery.data.model.loginmodel.LoginRequest
import com.grocery.sainik_grocery.data.model.loginmodel.LoginResponse
import com.grocery.sainik_grocery.data.model.newordernumbermodel.NewOrderNumberResponse
import com.grocery.sainik_grocery.data.model.noncsdmodel.NoncsdResponse
import com.grocery.sainik_grocery.data.model.notificationmodel.NotificationListResponse
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.OrderdetailsRequest
import com.grocery.sainik_grocery.data.model.orderdetailsmodel.OrderdetailsResponse
import com.grocery.sainik_grocery.data.model.orderlistmodel.OrderlistRequest
import com.grocery.sainik_grocery.data.model.orderlistmodel.OrderlistResponse
import com.grocery.sainik_grocery.data.model.ordersummerymodel.OrdersummeryRequest
import com.grocery.sainik_grocery.data.model.ordersummerymodel.OrdersummeryResponse
import com.grocery.sainik_grocery.data.model.otpverifymodel.OtpVerifiedResponse
import com.grocery.sainik_grocery.data.model.otpverifymodel.OtpverifyRequest
import com.grocery.sainik_grocery.data.model.postordermodel.PostOrderRequest
import com.grocery.sainik_grocery.data.model.postordermodel.PostorderResponse
import com.grocery.sainik_grocery.data.model.productdetailsmodel.ProductDetailsRequest
import com.grocery.sainik_grocery.data.model.productdetailsmodel.ProductDetailsResponse
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListResponse
import com.grocery.sainik_grocery.data.model.profilemodel.GetProfileRequest
import com.grocery.sainik_grocery.data.model.profilemodel.ProfileResponse
import com.grocery.sainik_grocery.data.model.searchmodel.SearchRequest
import com.grocery.sainik_grocery.data.model.setprimaryaddressmodel.PrimaryAddressRequest
import com.grocery.sainik_grocery.data.model.setprimaryaddressmodel.PrimaryaddressResponse
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenRequest
import com.grocery.sainik_grocery.data.model.tokenmodel.TokenResponse
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateRequest
import com.grocery.sainik_grocery.data.model.updatecartmodel.CartUpdateResponse
import com.grocery.sainik_grocery.data.model.updateprofilemodel.UpdateProfileRequest
import com.grocery.sainik_grocery.data.model.updateprofilemodel.UpdateProfileResponse
import com.grocery.sainik_grocery.data.model.wishlistaddmodel.WishListAddRequest
import com.grocery.sainik_grocery.data.model.wishlistaddmodel.WishlistAddResponse
import retrofit2.http.*


interface ApiInterface {


    @POST("Authentication")
    suspend fun generatetoken(
        @Body requestBody: TokenRequest
    ): TokenResponse


    @POST("LoginCustomers")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): LoginResponse


    @POST("CustomersOTPVerify")
    suspend fun otpverify(
        @Body requestBody: OtpverifyRequest
    ): OtpVerifiedResponse


    @POST("GetProductsList")
    suspend fun productList(
        @Body requestBody: ProductListRequest
    ): ProductListResponse


    @POST("GetProductDetails")
    suspend fun productDetails(
        @Body requestBody: ProductDetailsRequest
    ): ProductDetailsResponse


    @POST("AddToCart")
    suspend fun AddToCart(
        @Body requestBody: AddtocartRequest
    ): AddtocartResponse


    @POST("GetCartList")
    suspend fun CartList(
        @Body requestBody: CartListRequest
    ): CartListResponse


    @PUT("UpdateToCart")
    suspend fun updatecart(
        @Body requestBody: CartUpdateRequest
    ): CartUpdateResponse




    @HTTP(method = "DELETE", path = "DeleteCustomerCart", hasBody = true)
    suspend fun DeleteCustomerCart(
        @Body requestBody: DeleteCustomerCartRequest
    ): DeleteCartResponse



    @HTTP(method = "DELETE", path = "DeleteToCart", hasBody = true)
    suspend fun deletecart(
        @Body requestBody: DeleteCartRequest
    ): DeleteCartResponse






    @HTTP(method = "DELETE", path = "DeleteWishlist", hasBody = true)
    suspend fun deletewishlist(
        @Body requestBody: DeletewishlistRequest
    ): DeleteCartResponse




    @GET("GetNonCSDlist")
    suspend fun getnonCSDlist(
    ): NoncsdResponse



    @GET("GetNewOrderNumber")
    suspend fun GetNewOrderNumber(
    ): NewOrderNumberResponse



    @GET("GetLoginPageBanners")
    suspend fun LoginPageBanners(
    ): LoginBannerResponse




    @GET("ProductCategoriesList")
    suspend fun categorylist(
    ): CategoryListResponse



//
//    @GET("GetCustomerAddresses?CustomerId={id}")
//    suspend fun addresslist(
//        @Query("id") customerid: String
//    ): AddressListResponse


    @GET("GetCustomerAddresses")
    suspend fun addresslist(
        @Query("CustomerId") customerid: String?
    ): AddressListResponse






    @GET("GetCustomerNotifications")
    suspend fun notificationlist(
        @Query("CustomerId") customerid: String?
    ): NotificationListResponse







    @GET("GetBanners")
    suspend fun banners(
    ): HomeBannerResponse






    @HTTP(method = "DELETE", path = "CustomerAddress/{id}")
    suspend fun deleteaddress(
        @Path ("id") id: String?
    ): DeleteCartResponse





    @PUT("CustomerAddress/{id}")
    suspend fun primaryaddress(
        @Path  ("id") id: String?,
        @Body requestBody: PrimaryAddressRequest
    ): PrimaryaddressResponse






    @POST("AddToWishlist")
    suspend fun addtowishlist(
        @Body requestBody: WishListAddRequest
    ): WishlistAddResponse





    @POST("GetCustomerOrderDetails")
    suspend fun orderdetails(
        @Body requestBody: OrderdetailsRequest
    ): OrderdetailsResponse







    @POST("GetAllCustomerOrderList")
    suspend fun orderlist(
        @Body requestBody: OrderlistRequest
    ): OrderlistResponse






    @POST("GetOrderSummary")
    suspend fun ordersummery(
        @Body requestBody: OrdersummeryRequest
    ): OrdersummeryResponse






    @POST("CustomerAddress")
    suspend fun addressadd(
        @Body requestBody: AddressAddRequest
    ): AddressAddResponse









    @PUT("CustomerAddress/{id}")
    suspend fun editaddress(
        @Path  ("id") id: String?,
        @Body requestBody: AddressEditRequest
    ): AddressAddResponse





    @POST("GetWishlist")
    suspend fun wishlist(
        @Body requestBody: WishlistRequest
    ): WishlistResponse





    @POST("CreateCustomerSalesOrder")
    suspend fun postorder(
        @Body requestBody: PostOrderRequest
    ): PostorderResponse





    @POST("GetProductsList")
    suspend fun search(
        @Body requestBody: SearchRequest
    ): ProductListResponse







    @POST("GetCustomerProfile")
    suspend fun getProfile(
        @Body requestBody: GetProfileRequest
    ): ProfileResponse






    @PUT("UpdateCustomerProfile")
    suspend fun updateprofile(
        @Body requestBody: UpdateProfileRequest
    ): UpdateProfileResponse


//
//    @GET("/todos/{id}")
//    suspend fun getTodo(@Path(value = "id") todoId: Int, @Query("name") name: String?): Todo


//    @POST("user/login")
//    suspend fun login(
//        @Body requestBody: LoginRequestModel
//    ): LoginResponseModel
//
//
//
//    @POST("user/urc-list")
//    suspend fun urclist(
//        @Body requestBody: UrcRequestModel
//    ): UrcModel
//
//
//    @POST("user/urc-category")
//    suspend fun categoryall(
//        @Body requestBody: CategoryRequestModel
//    ): CategoryResponseModel
//
//
//    @POST("user/urc-dashboard")
//    suspend fun dashboard(
//        @Body requestBody: DashboardRequestModel
//    ): DashboardResponseModel
//
//    @POST("user/urc-product-list")
//    suspend fun productList(
//        @Body requestBody: ProductListRequestModel,
//        @Query("page") page: String
//    ): ProductListResponseModel
//
//    @GET("user/urc-product-details/{id}")
//    suspend fun getProductDetails(
//        @Path("id") id: String
//    ): ProductDetailsResponseModel
//
//    @GET("user/logout")
//    suspend fun logout(
//    ): LogoutResponseModel
//
//    @POST("user/forgot-password")
//    suspend fun forgotpassword(
//        @Body requestBody: ForgetPassRequestModel
//    ): ForgetPasswordResponseModel
//
//
//    @POST("user/cart-add")
//    suspend fun cartadd(
//        @Body requestBody: CartRequestModel
//    ): CartResponseModel
//
//    @GET("user/cart-delete/{id}")
//    suspend fun cartDelete(
//        @Path("id") id: String
//    ): CartResponseModel
//
//    @GET("user/cart-list")
//    suspend fun cartList(): CartListResponseModel
//
//    @GET("user/address-list")
//    suspend fun addressList(): AddressListResponseModel
//
//    @GET("user/address-delete/{id}")
//    suspend fun addressDelete(
//        @Path("id") id: String
//    ): AddAddressResponseModel
//
//    @GET("user/address-primary/{id}")
//    suspend fun addressPrimary(
//        @Path("id") id: String
//    ): AddAddressResponseModel
//
//    @POST("user/address-add")
//    suspend fun addAddress(
//        @Body requestBody: AddAddressRequestModel
//    ): AddAddressResponseModel
//
//    @POST("user/address-edit")
//    suspend fun editAddress(
//        @Body requestBody: AddAddressRequestModel
//    ): AddAddressResponseModel
//
//
//    @POST("user/wishlist-add")
//    suspend fun addtoWishlist(
//        @Body requestBody: AddWishlistRequestModel
//    ): AddtowishlistResponseModel
//
//
//    @GET("user/wishlist-list")
//    suspend fun wishlist(): WishListResponseModel
//
//
//    @GET("user/wishlist-delete/{id}")
//    suspend fun wishlistDelete(
//        @Path("id") id: String
//    ): AddtowishlistResponseModel
//
//
//    @GET("user/notification-list")
//    suspend fun notificationlist(): NotificationModelResponse
//
//
//    @POST("user/paymentcard-add")
//    suspend fun addpaymentcard(
//        @Body requestBody: AddcardRequestModel
//    ): AddcardResponseModel
//
//    @GET("user/paymentcard-list")
//    suspend fun paymentcardlist(): SaveCardListResponseModel
//
//
//    @GET("user/paymentcard-primary/{id}")
//    suspend fun paymentcardPrimary(
//        @Path("id") id: String
//    ): AddPaymentPrimaryCardResponse
//
//
//    @GET("user/paymentcard-delete/{id}")
//    suspend fun paymentcardDelete(
//        @Path("id") id: String
//    ): AddPaymentPrimaryCardResponse
//
//
//    @GET("user/order-summary")
//    suspend fun orderSummeryDetails(): OrderSummeryResponseModel
//
//    @GET("user/order-list")
//    suspend fun myOrderList(): MyOrderListResponseModel
//
//    @POST("user/add-order")
//    suspend fun addOrderDetails(
//        @Body requestBody: AddOrderRequestModel
//    ): AddOrderResponseModel
//
//    @GET("user/order-details/{id}")
//    suspend fun orderDetails(
//        @Path("id") id: String
//    ): OrderDetailsResponseModel
//
//    @GET("user/content-lists/{id}")
//    suspend fun getSupportAndFAQ(
//        @Path("id") id: String
//    ): SupportFAQResponseModel
//
//
//    @POST("user/order-payment")
//    suspend fun orderpayment(
//        @Body requestBody: OrderPaymentRequestModel
//    ): OrderPaymentResponseModel
//
//    @Multipart
//    @POST("user/profile-edit")
//    suspend fun profileupdate(
//        @Part("name") name: RequestBody,
//        @Part("last_name") last_name: RequestBody,
//        @Part("phone") phone: RequestBody,
//        @Part("gender") gender: RequestBody,
//        @Part("birthday") birthday: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("old_password") old_password: RequestBody,
//        @Part file: MultipartBody.Part,
//    ): ProfileUpdateResponseModel
//
//
//
//    @GET("user/profile")
//    suspend fun profileget(): ProfileDetailsResponse
//
//    @GET("user/attribute-list")
//    suspend fun filterResponse(): FilterResponseModel
//
//    @POST("user/return-order")
//    suspend fun returnDelivery(
//        @Body requestBody: ReturnDeliveryRequestModel
//    ): ReviewResponseModel


//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister1(@Body registerRequest: RegisterRequest): Single<CommonResponseModel>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("language") language: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_PROFILE_UPDATE)
//    fun postProfileUpdate(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part proileFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("user_id") userId: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @GET(NetworkConstants.ENDPOINT_USER_MOBILE_OTP)
//    fun otp(@Query("phone_no") phone_no: Long): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_FORGET_PASS)
//    fun forgotPassword(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_LOGIN)
//    fun login(@Body loginRequestModel: LoginRequestModel): Single<LoginResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CHANGE_LANGUAGE)
//    fun changeLanguage(@Body changeLanguageRequestModel: ChangeLanguageRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_PROFILE_DETAILS)
//    fun profileDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<ProfileResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_HUB_LIST)
//    fun hubListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<HubListResponse>
//
//    @POST(NetworkConstants.ENDPOINT_DELETE_CART_ITEM)
//    fun deleteCartItem(@Body deleteCartItemRequestModel: DeleteCartItemRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_GET_CART)
//    fun getCartDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<GetCartResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CART_CHECKOUT)
//    fun getCartCheckout(@Body checkoutRequestModel: CheckoutRequestModel): Single<CheckoutResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CATEGORY_LIST)
//    fun categoryListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<CategoryListResponse>
//    @POST(NetworkConstants.ENDPOINT_PAYMENT_STATUS)
//    fun postPaymentStatus(@Body paymentStatusRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//    @POST(NetworkConstants.ENDPOINT_ITEM_PARTICULAR_LIST)
//    fun itemParticularListDetails(@Body getItemParticulateRequestModel: GetItemParticulateRequestModel): Single<ItemParticualarResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAST_ORDER)
//    fun pastOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_UPCOMING_ORDER)
//    fun upcomingtOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_NOTIFICATION)
//    fun notificationListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<NotificationResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CHANGEPASSWORD)
//    fun changePassword(@Body changePasswordRequestModel: ChangePasswordRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_SUPPLY_MILL_LIST)
//    fun millListDetails(@Body millRequestModel: SupplyMillRequestModel): Single<SupplyMillResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_ADD_TO_CART)
//    fun addToCart(@Body addToCartRequesModel: AddToCartRequesModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAY_NOW)
//    fun postPayNow(@Body payNowRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CREATE_PASSWORD)
//    fun createPassword(@Body loginRequestModel: LoginRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_RESEND_OTP)
//    fun resendOTP(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @GET(NetworkConstants.ENDPOINT_SOCIETY_LIST)
//    fun societyList(): Single<SocietyListResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_LOGOUT)
//    fun logout(@Body getProfileRequestModel: GetProfileRequestModel): Single<CommonResponseModel>

}
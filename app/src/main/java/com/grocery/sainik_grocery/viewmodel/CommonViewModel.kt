package com.grocery.sainik_grocery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.grocery.sainik_grocery.data.Resource
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
import com.grocery.sainik_grocery.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CommonViewModel(private val mainRepository: MainRepository) : ViewModel() {


    var cartListItem: MutableLiveData<Int> = MutableLiveData()

    fun generatetoken(requestBody: TokenRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.generatetoken(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun login(requestBody: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.login(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun otpverify(requestBody: OtpverifyRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.otpverify(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun getnonCSDlist() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.getnonCSDlist()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun appversion() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.appversion()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun GetNewOrderNumber() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.GetNewOrderNumber()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun LoginPageBanners() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.LoginPageBanners()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun categorylist(requestBody: CategoryRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.categorylist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun SalesOrderPayment(requestBody: SalesOrderPaymentRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.SalesOrderPayment(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun ProductMainCategoriesList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.ProductMainCategoriesList()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun addresslist(customerid:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.addresslist(customerid)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun notificationlist(customerid:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.notificationlist(customerid)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun banners() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.banners()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun productList(requestBody: ProductListRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.productList(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun productDetails(requestBody: ProductDetailsRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.productDetails(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun AddToCart(requestBody: AddtocartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.AddToCart(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun CartList(requestBody: CartListRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.CartList(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun addtowishlist(requestBody: WishListAddRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.addtowishlist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun orderdetails(requestBody: OrderdetailsRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.orderdetails(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun orderlist(requestBody: OrderlistRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.orderlist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun addressadd(requestBody: AddressAddRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.addressadd(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun editaddress(id:String, requestBody: AddressEditRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.editaddress(id, requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }








    fun wishlist(requestBody: WishlistRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.wishlist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun postorder(requestBody: PostOrderRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.postorder(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }







    fun search(requestBody: SearchRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.search(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun getProfile(requestBody: GetProfileRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.getProfile(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun updateprofile(requestBody: UpdateProfileRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.updateprofile(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun support() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.support()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun faq() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.faq()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun updatecart(requestBody: CartUpdateRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.updatecart(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun deletecart(requestBody: DeleteCartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.deletecart(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun DeleteCustomerCart(requestBody: DeleteCustomerCartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.DeleteCustomerCart(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun deletewishlist(requestBody: DeletewishlistRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.deletewishlist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun ordersummery(requestBody: OrdersummeryRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.ordersummery(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }





    fun deleteaddress(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.deleteaddress(id)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }






    fun primaryaddress(id:String, requestBody: PrimaryAddressRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.primaryaddress(id, requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
//
//
//
//    fun urclist(requestBody: UrcRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.urclist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun categoryall(requestBody: CategoryRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.categoryall(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun productList(requestBody: ProductListRequestModel,page:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getAllProduct(requestBody,page)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun productDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getProductDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun cartadd(requestBody: CartRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartadd(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addressList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addressList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun editAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.editAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun deleteAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.deleteAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun primaryAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.primaryAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun dashboard(requestBody: DashboardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.dashboard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun logout() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.logout()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun forgotpassword(requestBody: ForgetPassRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.forgotpassword(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun addtoWishlist(requestBody: AddWishlistRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addtoWishlist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlistDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlistDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun notificationlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.notificationlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun addpaymentcard(requestBody: AddcardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addpaymentcard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun paymentcardlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardPrimary(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardPrimary(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addOrderDetails(requestBody: AddOrderRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addOrderDetails(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderSummeryDetails() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderSummeryDetails()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun myOrderList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.myOrderList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun getSupportAndFAQ(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getSupportAndFAQ(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderpayment(requestBody: OrderPaymentRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderpayment(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//
//        try {
//            emit(Resource.success(data = mainRepository.profileupdate(
//                name,
//                last_name,
//                phone,
//                gender,
//                birthday,
//                password,
//                old_password,
//                part)))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun profileget() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.profileget()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun filterResponse() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.filterResponse()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun returnDelivery(requestBody: ReturnDeliveryRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.returnDelivery(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
}
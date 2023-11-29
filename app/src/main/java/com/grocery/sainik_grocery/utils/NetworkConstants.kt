package com.grocery.sainik_grocery.utils

object NetworkConstants {
    const val CONNECTION_TIMEOUT: Long = 60
    const val READ_TIMEOUT: Long = 60

    //const val LOCAL_BASE_URL = "http://166.62.54.122/tantuja-web/"
    const val PROD_BASE_URL = "https://tantujaonline.com/"

    const val LOCAL_BASE_URL1 = "http://166.62.54.122/tantuja_latest/"



    const val API_AUTHENTICATION_FAILED = "Authentication Failed"
    const val API_BAD_REQUEST = "Bad Request"
    const val API_TRY_AGAIN = "Error Try Again"
    const val TOKEN_INVALID = "Token Invalid"
    const val API_SERVER_ERROR = "Server Error"
    const val API_SERVER_MAINTANANCE = "Server Maintanance"
    const val API_SOMETHING_WENT_WRONG = "Something went wrong"

    /*end points*/

    const val ENDPOINT_USER_LOGIN = "api/weaver-login"
    const val ENDPOINT_USER_CREATE_PASSWORD = "api/weaver-create-pass"
    const val ENDPOINT_USER_CHANGE_LANGUAGE = "api/change-language"
    const val ENDPOINT_USER_PROFILE_DETAILS = "api/profile-details"
    const val ENDPOINT_USER_RESEND_OTP = "api/resend-otp"
    const val ENDPOINT_USER_FORGET_PASS = "api/weaver-forgot-pass"
    const val ENDPOINT_USER_MOBILE_OTP = "api/verify-otp"
    const val ENDPOINT_USER_REGISTER = "api/weaver_register"
    const val ENDPOINT_PROFILE_UPDATE = "api/profile-update"
    const val ENDPOINT_SOCIETY_LIST = "api/society_list"
    const val ENDPOINT_HUB_LIST = "api/hub-list"
    const val ENDPOINT_CATEGORY_LIST = "api/category-list"
    const val ENDPOINT_GET_CART = "api/get-cart"
    const val ENDPOINT_CART_CHECKOUT = "api/cart-checkout"
    const val ENDPOINT_DELETE_CART_ITEM = "api/delete-cart-item"
    const val ENDPOINT_ITEM_PARTICULAR_LIST = "api/particular-list"
    const val ENDPOINT_SUPPLY_MILL_LIST = "api/mill-list"
    const val ENDPOINT_ADD_TO_CART = "api/add-to-cart"
    const val ENDPOINT_PAST_ORDER = "api/orderpast"
    const val ENDPOINT_UPCOMING_ORDER = "api/ordernew"
    const val ENDPOINT_NOTIFICATION = "api/notification"
    const val ENDPOINT_CHANGEPASSWORD = "api/change-password"
    const val ENDPOINT_PAY_NOW = "api/paynow"
    const val ENDPOINT_PAYMENT_STATUS = "api/paymentstatus"
    const val ENDPOINT_LOGOUT = "api/logout"

}


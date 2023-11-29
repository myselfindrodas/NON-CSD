package com.grocery.sainik_grocery.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.grocery.sainik_grocery.R
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utilities {

    fun makeShortToast(context: Context?, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun makeLongToast(context: Context?, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    /* fun isNetworkConnected(context: Context?):Boolean{
         val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val nw      = connectivityManager.activeNetwork ?: return false
         val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
         return when {
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
             //for other device how are able to connect with Ethernet
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
             //for check internet over Bluetooth
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
             else -> false
         }
     }*/


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    true
                } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } else {
                false
            }
        } else {
            val info = connectivityManager.activeNetworkInfo
            info != null && info.isConnected
        }
    }

     var lastTimeClicked: Long = 0
     fun isClickRecently():Boolean{
        if (SystemClock.elapsedRealtime() - lastTimeClicked < 1500) {
            return true
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        return false
    }

    fun getProfileNameLogo(text: String): String {
        return if (text.trim().substringAfterLast(" ").isNotEmpty()) {
            (text.trim().substring(0, 1) + text.trim().substringAfterLast(" ")
                .substring(0, 1)).uppercase()
        } else {
            (text.trim().substring(0, 1) + text.trim().substring(1, 2)).uppercase()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkConnected(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        //1
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        val version = Build.VERSION.SDK_INT
        return if (version >= 21) {
            ContextCompat.getDrawable(context, id)
        } else {
            context.resources.getDrawable(id)
        }
    }

    fun isEmail(text: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val p: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val m: Matcher = p.matcher(text)
        return m.matches()
    }

    fun isPhone(text: String?): Boolean {
        return if (text != null) {
            if (!TextUtils.isEmpty(text)) {
                TextUtils.isDigitsOnly(text) && text.trim().length == 10
            } else {
                false
            }
        } else {
            false
        }
    }

    fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                enableDisableView(group.getChildAt(idx), enabled)
            }
        }
    }

    @SuppressLint("Range")
    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val fileName: String?
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        fileName = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        cursor?.close()
        return fileName
    }

    @SuppressLint("Range")
    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(column_index)
        cursor.close()
        return path
    }

    private fun getfileExtension(context: Context, uri: Uri): String {
        val extension: String
        val contentResolver: ContentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))!!
        return extension
    }

    fun alertDialogUtil(
        context: Context?,
        title: String,
        message: String,
        isCancelable: Boolean = false,
        isPositive: Boolean = false,
        isNegetive: Boolean = false,
        isNeutral: Boolean = false,
        positiveTxt: String = context!!.resources.getString(R.string.ok),
        negetiveTxt: String = context!!.resources.getString(R.string.no),
        neutralTxt: String = context!!.resources.getString(R.string.maybe),
        onItemClickListener: OnItemClickListener
    ) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(isCancelable)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        if (isPositive) {
            builder.setPositiveButton(positiveTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(1, dialog)

                /*Toast.makeText(
                    context,
                    positiveTxt, Toast.LENGTH_SHORT
                ).show()*/
            }
        }

        if (isNegetive) {
            builder.setNegativeButton(negetiveTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(2, dialog)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.cancelled), Toast.LENGTH_SHORT
                ).show()
            }

        }
        if (isNeutral) {
            builder.setNeutralButton(neutralTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(3, dialog)
                Toast.makeText(
                    context,
                    neutralTxt, Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.show()
    }


    interface OnItemClickListener {

        fun onItemClickAction(type: Int, dialogInterface: DialogInterface)
    }

}
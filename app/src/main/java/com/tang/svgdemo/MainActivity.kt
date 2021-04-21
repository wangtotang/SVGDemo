package com.tang.svgdemo

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.Xml
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.xml.sax.XMLReader
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var newValue: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.setSupportZoom(true)
        settings.javaScriptCanOpenWindowsAutomatically = true

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {

            override fun onJsPrompt(
                view: WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult?
            ): Boolean {
                val uri = Uri.parse(message)
                Log.d("TAG", "onJsPrompt: $uri")
                if (uri.scheme == "js") {
                    var arg = uri.getQueryParameter("arg")
                    Log.d("TAG", "onJsPrompt: $arg")
                    return true
                }
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsConfirm(view, url, message, result)
            }

        }

        webView.addJavascriptInterface(MyJavascriptInterface(this), "main")

//        var content = ""
//        try {
//            val fis = assets.open("index.html")
//
//            val baos = ByteArrayOutputStream()
//
//            val buffer = ByteArray(1024)
//
//            while(true){
//                var length = fis.read(buffer)
//                if (length == -1) {
//                    break
//                }
//                baos.write(buffer, 0, length)
//            }
//            content = String(baos.toByteArray())
//            fis.close()
//            baos.close()
//        }catch (e: IOException) {
//            e.printStackTrace()
//        }
//        var html = Html.fromHtml(content).toString()

        webView.loadUrl("file:///android_asset/index.html")
//        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null)
    }

    fun getSvg(view: View) {
//        val settings = webView.settings
//        settings.loadWithOverviewMode = true
//        settings.javaScriptEnabled = true
//        settings.useWideViewPort = true
//        settings.builtInZoomControls = true
//        settings.setSupportZoom(true)
//
//        webView.loadUrl("file:android_asset/crocodile-1.html")
        load()
    }

    @SuppressLint("NewApi")
    fun onChange(view: View){
//        var json = JSONObject()
//        json.put("id", "270575")
//        newValue = if (newValue == "0") "6" else "0"
//        json.put("newValue", newValue)
        val connect = EventData()
        connect.stationName = "官桥站"
        connect.devName = "m3_connect"
        connect.deviceStatus = 1

        val bolt = EventData()
        bolt.stationName = "官桥站"
        bolt.devName = "m3_bolt"
        bolt.deviceStatus = 1

        val lock = EventData()
        lock.stationName = "官桥站"
        lock.devName = "m2_lock"
        lock.deviceStatus = 1

        var arrayListOf = arrayListOf<EventData>(connect, bolt, lock)

        var json = Gson().toJson(arrayListOf)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.post {
                webView.loadUrl("javascript:editSVG('$json')")
            }
        } else {
            webView.post {
                webView.evaluateJavascript("javascript:editSVG('$json')") { }
            }
        }
    }

    @SuppressLint("NewApi")
    fun load() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.post {
                webView.loadUrl("javascript:loadSVG()")
            }
        } else {
            webView.post {
                webView.evaluateJavascript("javascript:loadSVG()") { }
            }
        }

    }

    class MyJavascriptInterface(val context: Context) {
        @android.webkit.JavascriptInterface
        fun callJava(str: String) {
            Log.d("TAG", "callJava: $str")
        }

        @android.webkit.JavascriptInterface
        fun getJson(): String {
            var str = ""
            try {
                val fis = context.assets.open("香港地铁演示")

                val baos = ByteArrayOutputStream()

                val buffer = ByteArray(1024)

                while (true) {
                    var length = fis.read(buffer)
                    if (length == -1) {
                        break
                    }
                    baos.write(buffer, 0, length)
                }
                str = String(baos.toByteArray())
                fis.close()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return str
        }
    }

//    private void load() {
//
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.addJavascriptInterface(new JavascriptInterface(), "mainActivity");
//
//        String data = Html.fromHtml(content).toString();
//        //替换img属性
//        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
//
//        //点击查看
//        String jsimg = "function()\n { var imgs = document.getElementsByTagName(\"img\");for(var i = 0; i < imgs.length; i++){  imgs[i].onclick = function()\n{mainActivity.startPhotoActivity(this.src);}}}";
//
//        webview.loadDataWithBaseURL("http://www.youwebhost.com", varjs + data, "text/html", "UTF-8", null);
//
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView webView, String s) {
//                webview.loadUrl("javascript:(" + jsimg + ")()");
//            }
//        });
//
//    }
//
//}
//
//public class JavascriptInterface {
//    @android.webkit.JavascriptInterface
//    public void startPhotoActivity(String imageUrl) {
//        //根据URL查看大图逻辑
//
//    }
//}

}
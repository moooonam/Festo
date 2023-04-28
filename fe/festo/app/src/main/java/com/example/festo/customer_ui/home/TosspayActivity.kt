package com.example.festo.customer_ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.festo.R
import java.net.URISyntaxException

class TosspayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tosspay)

//        val webView = findViewById<WebView>(R.id.webView)
//        webView.settings.javaScriptEnabled = true // 자바스크립트 허용
//        webView.webViewClient = MyWebViewClient() // WebViewClient 설정
//
//        // 위의 코드를 WebView에서 로드
//        webView.loadUrl("file:///android_asset/payment.html")
//
//    }
//
//    @Suppress("DEPRECATION", "UNREACHABLE_CODE")
//    class MyWebViewClient : WebViewClient() {
//        override fun onPageFinished(view: WebView?, url: String?) {
//            // 페이지 로드 완료 후 PaymentWidget 사용
//            val paymentWidgetScript = """
//            const clientKey = 'test_ck_k6bJXmgo28em0BeWYEA8LAnGKWx4';
//            const customerKey = 'YbX2HuSlsC9uVJW6NMRMj';
//            const paymentWidget = PaymentWidget(clientKey, customerKey);
//            paymentWidget.renderPaymentMethods('#payment-method', 15000);
//            paymentWidget.requestPayment({
//                orderId: 'AD8aZDpbzXs4EQa-UkIX6',
//                orderName: '토스 티셔츠 외 2건',
//                successUrl: 'http://localhost:8080/success',
//                failUrl: 'http://localhost:8080/fail',
//                customerEmail: 'customer123@gmail.com',
//                customerName: '김토스'
//            });
//        """.trimIndent()
//            view?.evaluateJavascript(paymentWidgetScript, null)
//        }
//
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//            url?.let {
//                if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
//                    val uri = try {
//                        Uri.parse(url)
//                    } catch (e: Exception) {
//                        return false
//                    }
//
//                    return when (uri.scheme) {
//                        "intent" -> {
//                            startSchemeIntent(it)
//                        }
//                        else -> {
//                            return try {
//                                startActivity(view.context, Intent(Intent.ACTION_VIEW, uri))
//                                true
//                            } catch (e: java.lang.Exception) {
//                                false
//                            }
//                        }
//                    }
//                } else {
//                    return false
//                }
//            } ?: return false
//
//            return false
//        }
//
//        private fun startSchemeIntent(url: String): Boolean {
//            val schemeIntent: Intent = try {
//                Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
//            } catch (e: URISyntaxException) {
//                return false
//            }
//            try {
//                startActivity(schemeIntent)
//                return true
//            } catch (e: ActivityNotFoundException) {
//                val packageName = schemeIntent.getPackage()
//
//                if (!packageName.isNullOrBlank()) {
//                    startActivity(view.context,
//                        Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse("market://details?id=$packageName")
//                        )
//                    )
//                    return true
//                }
//            }
//            return false
//        }
    }

}
package com.example.festo.customer_ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.tosspayments.paymentsdk.model.TossPaymentResult
import java.net.URISyntaxException

class TosspayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tosspay)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true // 자바스크립트 허용
        webView.webViewClient = MyWebViewClient() // WebViewClient 설정

        // 위의 코드를 WebView 로드
        webView.loadUrl("file:///android_asset/payment.html")
    }

    class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url?.toString()
            url?.let {
                if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
                    val uri = try {
                        Uri.parse(url)
                    } catch (e: Exception) {
                        return@let false
                    }

                    when (uri.scheme) {
                        "intent" -> {
                            return if (startSchemeIntent(view?.context, it)) {
                                true
                            } else {
                                // "intent" 스키마를 처리할 수 있는 앱이 없을 때, 기본 WebView를 열어서 처리하도록 함
                                view?.loadUrl(url)
                                true
                            }
                        }

                        "http", "https" -> {
                            // 해당 링크를 브라우저에서 열도록 처리
                            return try {
                                view?.context?.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                true
                            } catch (e: java.lang.Exception) {
                                false
                            }
                        }

                        else -> {
                            return false
                        }
                    }
                } else {
                    return false
                }
            }
            url?.let {
                if (it.contains("http://localhost:8080/success/")) {
                    // 결제 성공 시 redirectURL 처리
                    handleSuccessRedirectUrl(it)
                    return true
                } else if (it.contains("http://localhost:8080/fail/")) {
                    // 결제 실패 시 redirectURL 처리
                    handleFailRedirectUrl(it)
                    return true
                }
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            val paymentWidgetScript = """
                // PaymentWidget 스크립트를 추가
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.src = 'https://cdn.tosspayments.com/js/v1/payment-widget.js';
                document.head.appendChild(script);
        
                // PaymentWidget을 사용한 결제 처리
                const clientKey = 'test_ck_k6bJXmgo28em0BeWYEA8LAnGKWx4';
                const customerKey = 'YbX2HuSlsC9uVJW6NMRMj';
                const paymentWidget = PaymentWidget(clientKey, customerKey);
                paymentWidget.renderPaymentMethods('#payment-method', 600);
                paymentWidget.requestPayment({
                    orderId: 'AD8aZDpbzXs4EQa-UkIX6',
                    orderName: '토스 티셔츠 외 2건',
                    successUrl: 'http://localhost:8080/success/',
                    failUrl: 'http://localhost:8080/fail',
                    customerEmail: 'customer123@gmail.com',
                    customerName: '김토스'
                });
            """.trimIndent()
            view?.evaluateJavascript(paymentWidgetScript, null)
        }

//        override fun onPageFinished(view: WebView?, url: String?) {
//            // 페이지 로드 완료 후 PaymentWidget 사용
//            val paymentWidgetScript = """
//            const clientKey = 'test_ck_k6bJXmgo28em0BeWYEA8LAnGKWx4';
//            const customerKey = 'YbX2HuSlsC9uVJW6NMRMj';
//            const paymentWidget = PaymentWidget(clientKey, customerKey);
//            paymentWidget.renderPaymentMethods('#payment-method', 600);
//            paymentWidget.requestPayment({
//                orderId: 'AD8aZDpbzXs4EQa-UkIX6',
//                orderName: '토스 티셔츠 외 2건',
//                successUrl: 'http://localhost:8080/success/',
//                failUrl: 'http://localhost:8080/fail',
//                customerEmail: 'customer123@gmail.com',
//                customerName: '김토스'
//            }, function(result) {
//                const redirectURL = result;
//                console.log(result); // redirectURL 확인용
//            });
//            """.trimIndent()
//            view?.evaluateJavascript(paymentWidgetScript, null)
//        }

        private fun handleSuccessRedirectUrl(url: String) {
            // 결제 성공 시 처리할 로직 구현
            // 현재 페이지 URL 출력
            println("성공url!!!!!!!!!!" + "Current URL: $url")
        }

        private fun handleFailRedirectUrl(url: String) {
            // 결제 실패 시 처리할 로직 구현
            println("실패url!!!!!!!!!!" + "Current URL: $url")
        }

        private fun startSchemeIntent(context: Context?, url: String): Boolean {
            val schemeIntent: Intent = try {
                Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            } catch (e: URISyntaxException) {
                return false
            }

            return try {
                context?.startActivity(schemeIntent)
                true
            } catch (e: java.lang.Exception) {
                false
            }
        }


    }


}
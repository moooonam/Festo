package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethodWidget


class TosspayActivity : AppCompatActivity() {
    private lateinit var paymentWidget: PaymentWidget

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tosspay)

        val totalPrice = intent.getIntExtra("totalPrice", 0)

        paymentWidget = PaymentWidget(
            activity = this,
            clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq",
            customerKey = "testtest"
        )
        val methodWidget = findViewById<PaymentMethodWidget>(R.id.payment_widget)

        paymentWidget.setMethodWidget(methodWidget)

        paymentWidget.renderPaymentMethodWidget(
            amount = totalPrice,
            orderId = "toss106923984729847289"
        )

        val paymentButton = findViewById<Button>(R.id.requestPay) // 결제 버튼 찾기
        paymentButton.setOnClickListener {
            paymentWidget.requestPayment(
                paymentResultLauncher = tossPaymentActivityResult,
                orderId = "toss106923984729847289",
                orderName = "tester"
            )
        }

    }
//    sealed class TossPaymentResult{
//        class Success(paymentKey: String, orderId: String, amount: Number, additionalParameters: Map<String, String>): TossPaymentResult()
//        class Fail(errorCode: String, errorMessage: String, orderId: String): TossPaymentResult()
//    }


    // 성공 및 실패 시 처리 함수
    private val tossPaymentActivityResult: ActivityResultLauncher<Intent> =
        PaymentWidget.getPaymentResultLauncher(
            this,
            { success ->
                handlePaymentSuccessResult(success)
            },
            { fail ->
                handlePaymentFailResult(fail)
            })

    private fun handlePaymentSuccessResult(success: TossPaymentResult.Success) {
        startActivity(
            PaymentResultActivity.getIntent(
                this@TosspayActivity,
                true,
                arrayListOf(
                    "PaymentKey|${success.paymentKey}",
                    "OrderId|${success.orderId}",
                    "Amount|${success.amount}",
                )
            )
        )
    }


    private fun handlePaymentFailResult(fail: TossPaymentResult.Fail) {
//        startActivity(
//            PaymentFailActivity.getIntent(
//                this@TosspayActivity,
//                false,
//                arrayListOf(
//                    "ErrorCode|${fail.errorCode}",
//                    "ErrorMessage|${fail.errorMessage}",
//                    "OrderId|${fail.orderId}"
//                )
//            )
//        )
        val intent = Intent(this, PaymentFailActivity::class.java)
        startActivity(intent)
    }


}

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


@Suppress("DEPRECATION")
class TosspayActivity : AppCompatActivity() {
    private lateinit var paymentWidget: PaymentWidget
    private lateinit var myOrderList: ArrayList<*>
    private lateinit var boothId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tosspay)

        // 전달받은 데이터
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        boothId = intent.getStringExtra("boothId").toString()
        myOrderList = intent.getSerializableExtra("myOrderList") as ArrayList<*>

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

    //PaymentKey, OrderId, Amount 순서
    private fun handlePaymentSuccessResult(success: TossPaymentResult.Success) {
        val intent = Intent(this, PaymentResultActivity::class.java).apply {
            putExtra("myOrderList", myOrderList)
            putExtra("success", true)
            putExtra("PaymentKey", success.paymentKey)
            putExtra("OrderId", success.orderId)
            putExtra("Amount", success.amount)
            putExtra("boothId", boothId)
        }
        startActivity(intent)
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

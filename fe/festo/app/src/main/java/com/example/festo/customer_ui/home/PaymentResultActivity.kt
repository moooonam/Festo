package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.data.API.UserAPI
import com.example.festo.data.req.OrderInfo
import com.example.festo.data.req.OrderReq
import com.example.festo.data.req.PaymentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class PaymentResultActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    companion object {
        private const val EXTRA_RESULT = "extraResult"
        private const val EXTRA_DATA = "extraData"

        fun getIntent(context: Context, result: Boolean, data: ArrayList<String>): Intent {
            return Intent(context, PaymentResultActivity::class.java).apply {
                putExtra(EXTRA_RESULT, result)
                putStringArrayListExtra(EXTRA_DATA, data)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_result)

        val isSuccess = intent?.getBooleanExtra(EXTRA_RESULT, false) == true
        val resultDataList = intent?.getStringArrayListExtra(EXTRA_DATA).orEmpty()
        val myOrderList = intent.getSerializableExtra("myOrderList") as ArrayList<MyOrderList>
        val paymentKey = intent.getStringExtra("PaymentKey")
        val orderId = intent.getStringExtra("OrderId")
        val amount = intent.getDoubleExtra("Amount", 0.0)
        val boothId = intent.getStringExtra("boothId")


////        PaymentKey
//        println(resultDataList[0].substring(11))
////        OrderId
//        println(resultDataList[1].substring(8))
////        Amount
//        println(resultDataList[2].substring(7))

        // 부스 아이디 전달받아야함
        val orderInfos = myOrderList.map {
            OrderInfo(menuId = it.product_id.toLong(), quantity = it.cnt)
        }
        val orderReq = OrderReq(
            boothId = boothId!!.toLong(),
            orderMenus = orderInfos,
            paymentInfo = PaymentInfo(paymentKey = paymentKey, orderId = orderId, amount = amount.toInt())
        )

        // 주문 retrofit 전송
        println(orderReq)
        val postApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        postApi!!.orderMenu(token, orderReq).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("주문성공!!!!!!!!!!!!!!!!!!!")
                }
//                println("오잉!!!!!!!!!!!!!!!!!!!")
//                println(response)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("주문실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

//         주문내역으로 이동
        val goOrderListBtn = findViewById<Button>(R.id.goOrderList)

        goOrderListBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "orderListFragment")
            startActivity(intent)
        }
    }
}
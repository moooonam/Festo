package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R


class PaymentResultActivity : AppCompatActivity() {
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

        val resultText = findViewById<TextView>(R.id.result)
        resultText.text = isSuccess.toString()

//         주문내역으로 이동
        val goOrderListBtn = findViewById<Button>(R.id.goOrderList)

        goOrderListBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "orderListFragment")
            startActivity(intent)
        }
    }
}
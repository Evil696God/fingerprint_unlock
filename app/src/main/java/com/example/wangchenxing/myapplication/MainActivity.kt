package com.example.wangchenxing.myapplication

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity(), View.OnClickListener {
  private var fingerprintHelper: FingerprintHelper? = null
  private var button: Button? = null
  private var textView: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    button = findViewById(R.id.bt_test)
    textView = findViewById(R.id.tv_test)

    button?.setOnClickListener(this)
  }

  override fun onClick(view: View?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      fingerprintHelper = FingerprintHelper(this)
      fingerprintHelper?.setAuthenticationCallback(object : FingerprintHelper.AuthenticationCallback {
        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        }

        override fun onAuthenticationFailed() {
          textView?.text = "解锁失败"
        }

        override fun onAuthenticationSucceeded(value: String) {
          textView?.text = "解锁成功"
        }

        override fun onAuthenticationFail(errorCode: Int, errString: CharSequence) {
          textView?.text = errString.toString()
        }
      })
      textView?.text = "等待解锁"
      fingerprintHelper?.startFingerprintUnlock()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      fingerprintHelper?.stopAuthenticate()
    }
  }
}

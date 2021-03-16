package com.zzq.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zzq.common.utils.showLongToast

//java.lang.SecurityException: NFC permission required: Neither user 10005 nor current process has android.permission.NFC.
class MainActivity : AppCompatActivity() {

    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mWriteTagFilters: Array<IntentFilter>
    private lateinit var mNfcPendingIntent: PendingIntent

    private lateinit var tvNfcInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvNfcInfo = findViewById(R.id.tv_nfc_info)

        val temp = NfcAdapter.getDefaultAdapter(this)
        if (temp == null) {
            showLongToast("机型不支持NFC！")
            tvNfcInfo.text = "机型不支持NFC！"
            return
        } else {
            mNfcAdapter = temp
        }
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        mWriteTagFilters = arrayOf(tagDetected)
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    override fun onResume() {
        super.onResume()
        if (this::mNfcAdapter.isInitialized) {
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Tag writing mode
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent!!.action) {
            val detectedTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            Log.e("tetetetete", "data: " + detectedTag?.toString())
        }
    }
}
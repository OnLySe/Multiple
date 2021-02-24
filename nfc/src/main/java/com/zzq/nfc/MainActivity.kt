package com.zzq.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

//java.lang.SecurityException: NFC permission required: Neither user 10005 nor current process has android.permission.NFC.
class MainActivity : AppCompatActivity() {

    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mWriteTagFilters: Array<IntentFilter>
    private lateinit var mNfcPendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        mWriteTagFilters = arrayOf(tagDetected)
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null)
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
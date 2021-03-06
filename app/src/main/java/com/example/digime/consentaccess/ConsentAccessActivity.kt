package me.digi.examples.barebonesapp.consentaccess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digime.R
import kotlinx.android.synthetic.main.consent_access_activity_layout.*
import me.digi.examples.barebonesapp.util.ConsentAccessInProgress
import me.digi.sdk.DMEPullClient
import me.digi.sdk.entities.DMEPullConfiguration
import me.digi.sdk.interapp.DMEAppCommunicator
import me.digi.sdk.utilities.crypto.DMECryptoUtilities

class ConsentAccessActivity : AppCompatActivity() {
    private lateinit var client: DMEPullClient
    private lateinit var pk: String
    private lateinit var cfg: DMEPullConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consent_access_activity_layout)

        pk = DMECryptoUtilities(applicationContext).privateKeyHexFrom(
            applicationContext.getString(R.string.digime_p12_filename),
            applicationContext.getString(R.string.digime_p12_password)
        )

        cfg = DMEPullConfiguration(
            applicationContext.getString(R.string.digime_application_id),
            applicationContext.getString(R.string.digime_contract_id),
            pk
        )

        item_ca_button_share_digime.setOnClickListener {
            displayReceiving()
            shareViaDigiMe()
        }
    }

    private fun shareViaDigiMe() {
        client = DMEPullClient(applicationContext, cfg)
        client.authorize(this) { session, error ->
            session?.let {
                client.getSessionData({ file, error ->
                    if (file != null) {
                        Log.d("File contained ", String(file.fileContent))
                        removeReceiving("")
                    } else
                        error?.message?.let { it1 -> removeReceiving(it1) }
                })
                { fileList, error ->

                    if (error == null)
                        removeReceiving("")
                    else
                        removeReceiving(error.message)
                }
            }
            error?.message?.let { it -> removeReceiving(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        DMEAppCommunicator.getSharedInstance().onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun displayReceiving() {
        val bundle = Bundle()
        bundle.putString("progressText", "Receiving RequestData")

        val sendingDataFragment = ConsentAccessInProgress()
        sendingDataFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .addToBackStack("in_progress")
            .replace(android.R.id.content, sendingDataFragment)
            .commit()
    }

    private fun displayResult() {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ConsentAccessFragment())
            .addToBackStack("shady_car_insurance_fragment")
            .commit()
    }

    private fun removeReceiving(errorMessage: String) {
        supportFragmentManager.popBackStackImmediate()
        if (errorMessage.isNotEmpty())
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        else
            displayResult()
    }
}
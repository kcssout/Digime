package me.digi.examples.barebonesapp.postbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digime.GoogleDrive.mainGoogleDrive
import com.example.digime.R
import kotlinx.android.synthetic.main.postbox_activity_layout.*
import me.digi.examples.barebonesapp.util.ConsentAccessInProgress
import me.digi.sdk.DMEPullClient
import me.digi.sdk.DMEPushClient
import me.digi.sdk.entities.DMEMimeType
import me.digi.sdk.entities.DMEPullConfiguration
import me.digi.sdk.entities.DMEPushConfiguration
import me.digi.sdk.entities.DMEPushPayload
import me.digi.sdk.interapp.DMEAppCommunicator
import me.digi.sdk.utilities.crypto.DMECryptoUtilities
import java.io.IOException

class PostboxActivity : AppCompatActivity() {
    private lateinit var client: DMEPushClient
    private lateinit var cfg: DMEPushConfiguration
    private lateinit var pullClient: DMEPullClient
    val TAG = "PostboxAct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postbox_activity_layout)

        cfg = DMEPushConfiguration(
            applicationContext.getString(R.string.digime_application_id),
            applicationContext.getString(R.string.digime_postbox_contract_id)
        )
        client = DMEPushClient(applicationContext, cfg)
        val privateKeyHex = DMECryptoUtilities(applicationContext).privateKeyHexFrom("y3lHtFPSLnfo7EldRkOVLXeIP7qPflxt.p12", "monkey periscope")
        val configuration = DMEPullConfiguration("jDlT16D0AR8vGOOddajfZGfUsDxY7wNL", "y3lHtFPSLnfo7EldRkOVLXeIP7qPflxt", privateKeyHex)
        pullClient = DMEPullClient(applicationContext, configuration)

        item_postbox_button.setOnClickListener {
            if(DMEAppCommunicator.getSharedInstance().canOpenDMEApp())
                createPostbox()
            else
                Toast.makeText(this, "Please install digi.me in order to continue", Toast.LENGTH_SHORT).show()
        }

        createPull.setOnClickListener { createPull() }

        getPulldata.setOnClickListener {  getPulldata()}

        getDriveBox.setOnClickListener {
            val intent = Intent(this, mainGoogleDrive::class.java)
            startActivity(intent)
        }

    }

    private fun createPull(){
        pullClient.authorize(this) { session, error ->
            Log.d(TAG, "session : "+ session + "\n" + error)

        }
    }

    private fun getPulldata(){
        pullClient.getSessionData({ file, error ->
            Log.d(TAG, "file : "+ String(file!!.fileContent) + "\n" + error)
        }) { fileList, error ->
            // Any errors interupting the flow of RequestData will be directed here, or null once all files are retrieved.
            // The file list here will represent the complete list of files that were downloaded.
        }
    }

    private fun createPostbox() {
        displaySendingData()

        client.createPostbox(this) { dmePostbox, error ->
            if (dmePostbox != null) {
                Log.d(TAG, "dmePostbox not null \n>>>>>>>>>>>>> "+ dmePostbox.toString())
                val fileContent = getFileContent("file.png")
                val metadata = getFileContent("metadatapng.json")

                client.pushDataToPostbox(
                    DMEPushPayload(
                        dmePostbox,
                        metadata,
                        fileContent,
                        DMEMimeType.IMAGE_PNG
                    )
                ) {
                    if (error == null) {
                        displayResults()
                    } else removeSending(error.message)
                }
            } else {
                Log.i("DME", "Postbox Create Error: $error")
                error?.message?.let { removeSending(it) }
            }
        }
    }

    private fun getFileContent(fileName: String): ByteArray {
        return try {
            val stream = assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            buffer
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ByteArray(2)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DMEAppCommunicator.getSharedInstance().onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "requestCode : "+ requestCode+"\n")
        Log.d(TAG, "requestCode : "+ resultCode+"\n")
        Log.d(TAG, "requestCode : "+ data+"\n")
    }

    private fun displayResults() {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PostboxFragment())
            .commit()
    }

    private fun removeSending(errorMessage: String) {
        supportFragmentManager.popBackStack()
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun displaySendingData() {
        Log.d(TAG, "displaySending Data")
        val bundle = Bundle()
        bundle.putString("progressText", "Sending RequestData")

        val sendingDataFragment = ConsentAccessInProgress()
        sendingDataFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .addToBackStack("in_progress")
            .replace(android.R.id.content, sendingDataFragment)
            .commit()
    }
}
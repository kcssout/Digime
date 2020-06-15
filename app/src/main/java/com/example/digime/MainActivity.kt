package me.digi.examples.barebonesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.digime.R
import com.example.digime.http.Header
import com.example.digime.http.RequestData
import com.example.digime.http.RetriveandSaveJSONdatafromfile
import com.example.digime.http.httpconnect
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import me.digi.examples.barebonesapp.consentaccess.ConsentAccessActivity
import me.digi.examples.barebonesapp.postbox.PostboxActivity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private var httpsConnectionManager: httpconnect? = null
    var TAG = "TTAAGG"

    val Bncd = HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        httpsConnectionManager = httpconnect.getInstance(this);
        val currentDateTime = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("HHmmss", Locale.KOREA).format(currentDateTime)
        var dateYYmm = SimpleDateFormat("YYYYMMdd", Locale.KOREA).format(currentDateTime)
        var IsTuno = Math.floor(Math.random() * 1000000) + 100000;
        if (IsTuno > 1000000) {
            IsTuno = (IsTuno - 100000);
        }


        var a = Header(
            "InquireDepositorAccountNumber",
            dateYYmm,
            dateFormat,
            "000299",
            "001",
            "DrawingTransferA",
            IsTuno.toString(),
            "2f645985549c33c9ade90ed7d932e9078e7189bfcec9702541beffc4e31c5407"
        )
        var reqdata = RequestData(a, "011", "3020000001251")

        httpsConnectionManager!!.login(reqdata, applicationContext);

        btnOpenPostboxEx.setOnClickListener {
            val intent = Intent(this, PostboxActivity::class.java)
            startActivity(intent)
        }
        btnOpenCAEx.setOnClickListener {
            val intent = Intent(this, ConsentAccessActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onResume() {
        super.onResume()

        // Check that an Application ID has been configured.
        if (getString(R.string.digime_application_id).isEmpty()) {

            val msg = AlertDialog.Builder(this)
            msg.setTitle("Missing Application ID")
            msg.setMessage(
                """
                You must provide an application ID in strings.xml.
                Please follow the instructions in the README to obtain yours.
                
                The application will now exit.
                """.trimIndent()
            )
            msg.setNeutralButton("Okay") { _, _ ->
                exitProcess(1)
            }
            msg.create().show()
        }
    }
}

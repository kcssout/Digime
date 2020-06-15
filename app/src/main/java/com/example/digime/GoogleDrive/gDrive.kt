package com.example.digime.GoogleDrive

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import com.example.digime.R
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class gDrive : Activity() {

    private val PREF_ACCOUNT_NAME = "accountName"

    val TAG = "TasksSample"

    val REQUEST_GOOGLE_PLAY_SERVICES = 0
    val REQUEST_AUTHORIZATION = 1
    val REQUEST_ACCOUNT_PICKER = 2
    val CAPTURE_IMAGE = 3;

    val httpTransport = AndroidHttp.newCompatibleTransport()
    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    var credential : GoogleAccountCredential? =null
    var service:Drive? = null
    var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gdrive)
        var credential = GoogleAccountCredential.usingOAuth2(this, Arrays.asList(DriveScopes.DRIVE_FILE))

        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data: Intent ) {
        when (requestCode) {
            REQUEST_ACCOUNT_PICKER->
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                var accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                if (accountName != null) {
                    credential!!.setSelectedAccountName(accountName);
                    service = getDriveService(credential!!);
                    startCameraIntent();
                }
            }
            REQUEST_AUTHORIZATION->
            if (resultCode == Activity.RESULT_OK) {
                saveFileToDrive();
            } else {
                startActivityForResult(credential!!.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
            }
            CAPTURE_IMAGE->
            if (resultCode == Activity.RESULT_OK) {
                saveFileToDrive();
            }
        }
    }

    fun getDriveService(credential: GoogleAccountCredential) : Drive {
        return  Drive.Builder(AndroidHttp.newCompatibleTransport(),  GsonFactory(), credential).build();
    }

    fun startCameraIntent() {
        var mediaStorageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath();
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format( Date());
        fileUri = Uri.fromFile( java.io.File(mediaStorageDir + java.io.File.separator + "IMG_"
                + timeStamp + ".jpg"));

        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    fun saveFileToDrive() {
//        var t =  Thread( Runnable() {
//             run() {
//                try {
//
//                    // File's binary content
//                    var fileContent =  File(fileUri!!.getPath());
//                    var mediaContent =  FileContent("image/jpeg", fileContent);
//
//                    // File's metadata.
//                    var pathname = System.getProperty("java.io.tmpdir")
//                    var filename = "some-file.txt"
//
//                    val body = File(pathname + fileContent.getName())
//                    body.set
//                    body.setTitle(fileContent.getName());
//                    body.setMimeType("image/jpeg");
//                    var file = service!!.files().insert(body, mediaContent).execute();
//                    if (file != null) {
////                        showToast("Photo uploaded: " + file.getTitle());
//                        startCameraIntent();
//                    }
//                } catch (UserRecoverableAuthIOException e) {
//                    startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
    }


}
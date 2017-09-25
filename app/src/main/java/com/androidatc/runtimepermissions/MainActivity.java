package com.androidatc.runtimepermissions;

/**
 * Copyright (c) 2016 Android ATC.
 *
 * Author: Android ATC Training Team.
 *
 * Source code in this project is provided for trainers of Android ATC
 * course AND-401 titled "Android Application Development".
 *
 * The is the source code for Lab 3 of the text book.
 *
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Permision code that will be checked in the method onRequestPermissionsResult
    private int RUNTIME_PERMISSION_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing button
        Button btn_request_permissions = (Button) findViewById(R.id.btn_request_permissions);

        //Adding a click listener
        btn_request_permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First checking if the app is already having the permission
                if (isCameraAccessAllowed()) {
                    //If permission is already having then showing the toast
                    Toast.makeText(v.getContext(), "You already have the permission to access Camera", Toast.LENGTH_LONG).show();
                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestCameraPermission();
            }
        });
    }

    //We are calling this method to check the permission status
    private boolean isCameraAccessAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            showAlertDialog();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RUNTIME_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == RUNTIME_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can use the camera", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Runtime Permissions");

        // set dialog message
        alertDialogBuilder
                .setMessage("This is tutorial for Runtime Permission. This needs permission of accessing your device Camera." +
                        "Please grant the permission")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}


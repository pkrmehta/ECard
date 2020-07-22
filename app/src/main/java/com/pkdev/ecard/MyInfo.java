package com.pkdev.ecard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MyInfo extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        findViewById(R.id.myInfo_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                createRequest();
                LoginManager.getInstance().logOut();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(MyInfo.this, Login.class));
            }
        });

        findViewById(R.id.myInfo_phoneAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);


                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_phoneadd,null);

                final EditText editPhone = popPhoneLayout.findViewById(R.id.popupPhone_phone);
                final Button saveButton = popPhoneLayout.findViewById(R.id.popupPhone_save);
                final Button cancelButton = popPhoneLayout.findViewById(R.id.popupPhone_cancel);


                dialog.setView(popPhoneLayout);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "save", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        findViewById(R.id.myInfo_addressAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);


                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_adressadd,null);

                final EditText editAddress = popAddressLayout.findViewById(R.id.popupAddress_address);
                final Button saveButton = popAddressLayout.findViewById(R.id.popupAddress_save);
                final Button cancelButton = popAddressLayout.findViewById(R.id.popupAddress_cancel);
                final CheckBox checkDefault = popAddressLayout.findViewById(R.id.popupAddress_defaultCheck);


                dialog.setView(popAddressLayout);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "save", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        findViewById(R.id.myInfo_websiteAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);


                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_websiteadd,null);

                final EditText editPhone = popPhoneLayout.findViewById(R.id.popupWebsite_link);
                final Button saveButton = popPhoneLayout.findViewById(R.id.popupWebsite_save);
                final Button cancelButton = popPhoneLayout.findViewById(R.id.popupWebsite_cancel);


                dialog.setView(popPhoneLayout);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "save", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        findViewById(R.id.myInfo_emailAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);


                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_email,null);

                final EditText editEmail = popAddressLayout.findViewById(R.id.popupEmail_email);
                final Button saveButton = popAddressLayout.findViewById(R.id.popupEmail_save);
                final Button cancelButton = popAddressLayout.findViewById(R.id.popupEmail_cancel);
                final CheckBox checkPrimary = popAddressLayout.findViewById(R.id.popupEmail_primaryCheck);


                dialog.setView(popAddressLayout);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "save", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        findViewById(R.id.myInfo_workAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);


                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_work,null);

                final EditText editDesignation = popAddressLayout.findViewById(R.id.popupWork_designation);
                final EditText editCompany = popAddressLayout.findViewById(R.id.popupWork_company);
                final EditText editExperience = popAddressLayout.findViewById(R.id.popupWork_experience);
                final Button saveButton = popAddressLayout.findViewById(R.id.popupWork_save);
                final Button cancelButton = popAddressLayout.findViewById(R.id.popupWork_cancel);


                dialog.setView(popAddressLayout);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "save", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do things for save
                        Toast.makeText(MyInfo.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });



    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}

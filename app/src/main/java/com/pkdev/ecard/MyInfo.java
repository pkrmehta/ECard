package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pkdev.ecard.adapter.AddressAdapter;
import com.pkdev.ecard.adapter.ContactAdapter;
import com.pkdev.ecard.adapter.EmailAdapter;
import com.pkdev.ecard.adapter.PhoneAdapter;
import com.pkdev.ecard.adapter.WebsiteAdapter;
import com.pkdev.ecard.adapter.WorkAdapter;
import com.pkdev.ecard.model.Address;
import com.pkdev.ecard.model.Contact;
import com.pkdev.ecard.model.Email;
import com.pkdev.ecard.model.Phone;
import com.pkdev.ecard.model.Website;
import com.pkdev.ecard.model.Work;

import java.util.ArrayList;
import java.util.List;

public class MyInfo extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    RecyclerView mPhoneList;
    List<Phone> phoneList;
    PhoneAdapter phoneAdapter;

    RecyclerView mAddressList;
    List<Address> addressList;
    AddressAdapter addressAdapter;

    RecyclerView mEmailList;
    List<Email> emailList;
    EmailAdapter emailAdapter;

    RecyclerView mWebsiteList;
    List<Website> websiteList;
    WebsiteAdapter websiteAdapter;

    RecyclerView mWorkList;
    List<Work> workList;
    WorkAdapter workAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog pd;

    TextView name, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        pd = new ProgressDialog(MyInfo.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        name = findViewById(R.id.myInfo_name);
        title = findViewById(R.id.myInfo_desc);


        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name.setText(task.getResult().get("name").toString());
                title.setText(task.getResult().get("title").toString());
            }
        });

        populatePhone();

        populateAddress();

        populateWebsite();

        populateEmail();

        populateWork();

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
    }

    private void populateEmail() {
        mEmailList = (RecyclerView) findViewById(R.id.myInfo_emailList);
        mEmailList.setHasFixedSize(true);
        mEmailList.setLayoutManager(new LinearLayoutManager(this));
        emailList = new ArrayList<>();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("email").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Email email = doc.toObject(Email.class);
                        emailList.add(email);
                    }
                    emailAdapter = new EmailAdapter(MyInfo.this, emailList);
                    mEmailList.setAdapter(emailAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

        findViewById(R.id.myInfo_emailAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_email, null);

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
    }

    private void populateAddress() {
        mAddressList = (RecyclerView) findViewById(R.id.myInfo_addressList);
        mAddressList.setHasFixedSize(true);
        mAddressList.setLayoutManager(new LinearLayoutManager(this));
        addressList = new ArrayList<>();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Address address = doc.toObject(Address.class);
                        addressList.add(address);
                    }
                    addressAdapter = new AddressAdapter(MyInfo.this, addressList);
                    mAddressList.setAdapter(addressAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

        findViewById(R.id.myInfo_addressAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_adressadd, null);

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
    }

    private void populateWork() {
        mWorkList = (RecyclerView) findViewById(R.id.myInfo_workList);
        mWorkList.setHasFixedSize(true);
        mWorkList.setLayoutManager(new LinearLayoutManager(this));
        workList = new ArrayList<>();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("job").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Work work = doc.toObject(Work.class);
                        workList.add(work);
                    }
                    workAdapter = new WorkAdapter(MyInfo.this, workList);
                    mWorkList.setAdapter(workAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

        findViewById(R.id.myInfo_workAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_work, null);

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

    private void populateWebsite() {
        mWebsiteList = (RecyclerView) findViewById(R.id.myInfo_websiteList);
        mWebsiteList.setHasFixedSize(true);
        mWebsiteList.setLayoutManager(new LinearLayoutManager(this));
        websiteList = new ArrayList<>();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("website").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Website website = doc.toObject(Website.class);
                        websiteList.add(website);
                    }
                    websiteAdapter = new WebsiteAdapter(MyInfo.this, websiteList);
                    mWebsiteList.setAdapter(websiteAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

        findViewById(R.id.myInfo_websiteAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_websiteadd, null);

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
    }

    private void populatePhone() {
        mPhoneList = (RecyclerView) findViewById(R.id.myInfo_phoneList);
        mPhoneList.setHasFixedSize(true);
        mPhoneList.setLayoutManager(new LinearLayoutManager(this));
        phoneList = new ArrayList<>();
        pd.show();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Phone phone = doc.toObject(Phone.class);
                        phoneList.add(phone);
                    }
                    phoneAdapter = new PhoneAdapter(MyInfo.this, phoneList);
                    mPhoneList.setAdapter(phoneAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

        findViewById(R.id.myInfo_phoneAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_phoneadd, null);

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

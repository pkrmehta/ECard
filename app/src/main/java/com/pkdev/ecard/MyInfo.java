package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.pkdev.ecard.adapter.PhoneAdapter;
import com.pkdev.ecard.model.Address;
import com.pkdev.ecard.model.Contact;
import com.pkdev.ecard.model.Phone;

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

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog pd;

    TextView name,title;
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

        mPhoneList = (RecyclerView) findViewById(R.id.myInfo_phoneList);
        mPhoneList.setHasFixedSize(true);
        mPhoneList.setLayoutManager(new LinearLayoutManager(this));
        phoneList = new ArrayList<>();
        pd.show();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Phone phone = doc.toObject(Phone.class);
                        phoneList.add(phone);
                    }
                    phoneAdapter = new PhoneAdapter(MyInfo.this, phoneList);
                    mPhoneList.setAdapter(phoneAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                }
                else {
                    pd.dismiss();
                }
            }
        });

        mAddressList = (RecyclerView) findViewById(R.id.myInfo_addressList);
        mAddressList.setHasFixedSize(true);
        mAddressList.setLayoutManager(new LinearLayoutManager(this));
        addressList = new ArrayList<>();

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Address address = doc.toObject(Address.class);
                        addressList.add(address);
                    }
                    addressAdapter = new AddressAdapter(MyInfo.this, addressList);
                    mAddressList.setAdapter(addressAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                }
                else {
                    pd.dismiss();
                }
            }
        });

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

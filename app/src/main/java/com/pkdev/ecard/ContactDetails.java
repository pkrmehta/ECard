package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pkdev.ecard.adapter.AddressAdapter;
import com.pkdev.ecard.adapter.PhoneAdapter;
import com.pkdev.ecard.adapter.WebsiteAdapter;
import com.pkdev.ecard.model.Address;
import com.pkdev.ecard.model.Phone;
import com.pkdev.ecard.model.Website;

import java.util.ArrayList;
import java.util.List;

public class ContactDetails extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    RecyclerView mPhoneList;
    List<Phone> phoneList;
    PhoneAdapter phoneAdapter;

    RecyclerView mAddressList;
    List<Address> addressList;
    AddressAdapter addressAdapter;

    RecyclerView mWebsiteList;
    List<Website> websiteList;
    WebsiteAdapter websiteAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog pd;

    TextView name,title;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        user_id = getIntent().getStringExtra("USER_ID").toString();

        pd = new ProgressDialog(ContactDetails.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        name = findViewById(R.id.contactDetails_name);
        title = findViewById(R.id.contactDetails_title);


        db.collection("users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name.setText(task.getResult().get("name").toString());
                title.setText(task.getResult().get("title").toString());
            }
        });

        populateAddress();
        populatePhone();
        populateWebsite();
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

    private void populateWebsite(){
        mWebsiteList = (RecyclerView) findViewById(R.id.contactDetails_websiteList);
        mWebsiteList.setHasFixedSize(true);
        mWebsiteList.setLayoutManager(new LinearLayoutManager(this));
        websiteList = new ArrayList<>();

        db.collection("users").document(user_id).collection("website").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Website website = doc.toObject(Website.class);
                        websiteList.add(website);
                    }
                    websiteAdapter = new WebsiteAdapter(ContactDetails.this, websiteList);
                    mWebsiteList.setAdapter(websiteAdapter);
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
    }

    private void populateAddress(){
        mAddressList = (RecyclerView) findViewById(R.id.contactDetails_addressList);
        mAddressList.setHasFixedSize(true);
        mAddressList.setLayoutManager(new LinearLayoutManager(this));
        addressList = new ArrayList<>();

        db.collection("users").document(user_id).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Address address = doc.toObject(Address.class);
                        addressList.add(address);
                    }
                    addressAdapter = new AddressAdapter(ContactDetails.this, addressList);
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
    }

    private void populatePhone(){
        mPhoneList = (RecyclerView) findViewById(R.id.contactDetails_phoneList);
        mPhoneList.setHasFixedSize(true);
        mPhoneList.setLayoutManager(new LinearLayoutManager(this));
        phoneList = new ArrayList<>();
        pd.show();

        db.collection("users").document(user_id).collection("phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Phone phone = doc.toObject(Phone.class);
                        phoneList.add(phone);
                    }
                    phoneAdapter = new PhoneAdapter(ContactDetails.this, phoneList);
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
    }
}

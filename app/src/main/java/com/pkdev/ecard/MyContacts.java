package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pkdev.ecard.adapter.ContactAdapter;
import com.pkdev.ecard.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MyContacts extends AppCompatActivity {

    RecyclerView mContactList;
    List<Contact> contactList;
    ContactAdapter contactAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        pd = new ProgressDialog(MyContacts.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        //Populating RecyclerView With contactList
        mContactList = (RecyclerView) findViewById(R.id.myContact_list);
        mContactList.setHasFixedSize(true);
        mContactList.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();
        pd.show();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("contacts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Contact contact = doc.toObject(Contact.class);
                        contactList.add(contact);
                    }

                    contactAdapter = new ContactAdapter(MyContacts.this, contactList);
                    mContactList.setAdapter(contactAdapter);
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

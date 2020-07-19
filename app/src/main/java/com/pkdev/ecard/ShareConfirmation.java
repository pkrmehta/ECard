package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ShareConfirmation extends AppCompatActivity {

    String shareId,userId;
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_confirmation);

        pd = new ProgressDialog(ShareConfirmation.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");


        shareId = getIntent().getStringExtra("USER_ID");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final HashMap<String,String> hashUser = new HashMap<>();
        hashUser.put("userid",userId);

        final HashMap<String,String> hashShare = new HashMap<>();
        hashShare.put("userid",shareId);

        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                mDatabase.collection("users").document(shareId).collection("contacts").document(userId).set(hashUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mDatabase.collection("users").document(userId).collection("contacts").document(shareId).set(hashShare).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                pd.dismiss();
                                Toast.makeText(ShareConfirmation.this,"Success",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }
        });
    }
}

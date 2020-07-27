package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShareConfirmation extends AppCompatActivity {

    String shareId, userId;
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    ProgressDialog pd;
    TextView nameText;

    HashMap<String, String> hashUser;
    HashMap<String, String> hashShare;
    String shareName, shareTitle, shareImage, userName, userTitle, userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_confirmation);

        pd = new ProgressDialog(ShareConfirmation.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        nameText = findViewById(R.id.confirmation_name);

        pd.show();

        getUsersId();

        getUsersData();

        findViewById(R.id.confirmation_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shareId.equals(userId) && hashUser != null && hashShare != null) {
                    pd.show();
                    mDatabase.collection("users").document(shareId).collection("contacts").document(userId).set(hashUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mDatabase.collection("users").document(userId).collection("contacts").document(shareId).set(hashShare).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            sendNotification(shareId, userId);
                                        } else {
                                            mDatabase.collection("users").document(shareId).collection("contacts").document(userId).delete();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    Toast.makeText(ShareConfirmation.this, "Exception", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * description : used to get id of the app user and id of the user we want to share with
     */
    private void getUsersId() {
        Intent intent = getIntent();
        shareId = intent.getStringExtra("USER_ID");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Uri data = intent.getData();
        if (data != null) {
            List<String> params = data.getPathSegments();
            shareId = params.get(params.size() - 1);
            Toast.makeText(this, shareId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * description : used to get "name","title","image" for current user and user we want to share with
     */
    private void getUsersData() {
        mDatabase.collection("users").document(shareId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    shareName = ds.get("name").toString();
                    shareTitle = ds.get("title").toString();
                    shareImage = ds.get("image").toString();
                    nameText.setText(shareName);

                    hashShare = new HashMap<>();
                    hashShare.put("userid", shareId);
                    hashShare.put("name", shareName);
                    hashShare.put("title", shareTitle);
                    hashShare.put("image", shareImage);
                    hashShare.put("saved", "false");
                    pd.dismiss();
                }
            }
        });
        mDatabase.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                if (task.isSuccessful()) {
                    userName = ds.get("name").toString();
                    userTitle = ds.get("title").toString();
                    userImage = ds.get("image").toString();

                    hashUser = new HashMap<>();
                    hashUser.put("userid", userId);
                    hashUser.put("name", userName);
                    hashUser.put("title", userTitle);
                    hashUser.put("image", userImage);
                    hashUser.put("saved", "false");
                }
            }
        });
    }

    /**
     * description : sends notification to the sender that his contact has been shared by some user
     * @param shareId : id of the sender
     * @param userId  : id of user, who accepts the contact share request
     */
    private void sendNotification(String shareId, String userId) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        HashMap<String, String> notification = new HashMap<String, String>();
        notification.put("contactid", userId);
        notification.put("desc", "Shared contact with you");
        notification.put("name", userName);
        notification.put("time", timeStamp);
        notification.put("heading","Shared Contact");
        notification.put("icon","icon");

        pd.show();
        mDatabase.collection("users").document(shareId).collection("notifications").document(userId).set(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    Intent intent = new Intent(ShareConfirmation.this,Congrats.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

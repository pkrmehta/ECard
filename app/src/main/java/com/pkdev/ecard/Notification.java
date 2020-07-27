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
import com.pkdev.ecard.adapter.NotificationCardAdapter;
import com.pkdev.ecard.model.Contact;
import com.pkdev.ecard.model.NotificationCard;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    RecyclerView mNotificationList;
    List<NotificationCard> notificationCardList;
    NotificationCardAdapter notificationCardAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        pd = new ProgressDialog(Notification.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        mNotificationList = (RecyclerView) findViewById(R.id.notification_recyclerView);
        mNotificationList.setHasFixedSize(true);
        mNotificationList.setLayoutManager(new LinearLayoutManager(this));
        notificationCardList = new ArrayList<>();
        pd.show();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("notifications").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        NotificationCard notificationCard = doc.toObject(NotificationCard.class);
                        notificationCardList.add(notificationCard);
                    }
                    notificationCardAdapter = new NotificationCardAdapter(Notification.this, notificationCardList);
                    mNotificationList.setAdapter(notificationCardAdapter);
                    //If ProgressDialog is showing Dismiss it
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                }
            }
        });

    }
}

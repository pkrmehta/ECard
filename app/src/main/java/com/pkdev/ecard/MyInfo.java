package com.pkdev.ecard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pkdev.ecard.adapter.AddressAdapter;
import com.pkdev.ecard.adapter.EmailAdapter;
import com.pkdev.ecard.adapter.PhoneAdapter;
import com.pkdev.ecard.adapter.WebsiteAdapter;
import com.pkdev.ecard.adapter.WorkAdapter;
import com.pkdev.ecard.model.Address;
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

    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    ImageButton logoutButton,addEmail,addWebsite,addWork,addAddress,addPhone;

    DocumentReference document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        logoutButton = findViewById(R.id.myInfo_logout);
        addEmail = findViewById(R.id.myInfo_emailAdd);
        addAddress = findViewById(R.id.myInfo_addressAdd);
        addWork = findViewById(R.id.myInfo_workAdd);
        addWebsite = findViewById(R.id.myInfo_websiteAdd);
        addPhone = findViewById(R.id.myInfo_phoneAdd);

        pd = new ProgressDialog(MyInfo.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");

        name = findViewById(R.id.myInfo_name);
        title = findViewById(R.id.myInfo_desc);

        document = db.collection("users").document(user_id);

        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name.setText(task.getResult().get("name").toString());
                title.setText(task.getResult().get("title").toString());
            }
        });

        setUpPhone();

        setUpAddress();

        setUpWebsite();

        setUpEmail();

        setUpWork();

        logoutButton.setOnClickListener(new View.OnClickListener() {
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

    private void setUpEmail() {
        mEmailList = (RecyclerView) findViewById(R.id.myInfo_emailList);
        mEmailList.setHasFixedSize(true);
        mEmailList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        emailList = new ArrayList<>();
        document.collection("email").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_email, null);

                final EditText editEmail = popAddressLayout.findViewById(R.id.popupEmail_email);
                final Button saveButton = popAddressLayout.findViewById(R.id.popupEmail_save);
                final Button cancelButton = popAddressLayout.findViewById(R.id.popupEmail_cancel);
                final CheckBox checkPrimary = popAddressLayout.findViewById(R.id.popupEmail_primaryCheck);

                dialog.setView(popAddressLayout);
                final AlertDialog alertDialog = dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setClickable(false);
                        final Email email = new Email();
                        email.setEmail(editEmail.getText().toString());
                        email.setType("work"); //TO BE CODED
                        pd.show();
                        if (emailList.size() < 2) {
                            document.collection("email").add(email).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    emailList.add(email);
                                    emailAdapter.notifyDataSetChanged();
                                    saveButton.setClickable(true);
                                }
                            });
                        } else {
                            Toast.makeText(MyInfo.this, "Can not add more entries", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    private void setUpAddress() {
        mAddressList = (RecyclerView) findViewById(R.id.myInfo_addressList);
        mAddressList.setHasFixedSize(true);
        mAddressList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        addressList = new ArrayList<>();

        document.collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        addAddress.setOnClickListener(new View.OnClickListener() {
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
                final AlertDialog alertDialog = dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setClickable(false);
                        final Address address = new Address();
                        address.setAddress(editAddress.getText().toString());
                        pd.show();
                        if (addressList.size() < 2) {
                            document.collection("address").add(address).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    addressList.add(address);
                                    addressAdapter.notifyDataSetChanged();
                                    saveButton.setClickable(true);
                                }
                            });
                        } else {
                            Toast.makeText(MyInfo.this, "Can not add more entries", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    private void setUpWork() {
        mWorkList = (RecyclerView) findViewById(R.id.myInfo_workList);
        mWorkList.setHasFixedSize(true);
        mWorkList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        workList = new ArrayList<>();

        document.collection("job").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popAddressLayout = inflater.inflate(R.layout.popup_work, null);

                final EditText editDesignation = popAddressLayout.findViewById(R.id.popupWork_designation);
                final EditText editCompany = popAddressLayout.findViewById(R.id.popupWork_company);
                final EditText editStart = popAddressLayout.findViewById(R.id.popupWork_start);
                final EditText editEnd = popAddressLayout.findViewById(R.id.popupWork_end);
                final CheckBox isPresent = popAddressLayout.findViewById(R.id.popupWork_isCurrent);
                final Button saveButton = popAddressLayout.findViewById(R.id.popupWork_save);
                final Button cancelButton = popAddressLayout.findViewById(R.id.popupWork_cancel);

                dialog.setView(popAddressLayout);
                final AlertDialog alertDialog = dialog.show();

                isPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(isPresent.isChecked()){
                            editEnd.setText("Present");
                            editEnd.setEnabled(false);
                        }
                        else {
                            editEnd.setText("");
                            editEnd.setEnabled(true);
                        }
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setClickable(false);
                        final Work work = new Work();
                        work.setPosition(editDesignation.getText().toString());
                        work.setCompany(editCompany.getText().toString());
                        work.setStart(editStart.getText().toString());
                        work.setEnd(editEnd.getText().toString());
                        pd.show();
                        if (workList.size() < 2) {
                            document.collection("job").add(work).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    workList.add(work);
                                    workAdapter.notifyDataSetChanged();
                                    saveButton.setClickable(true);
                                }
                            });
                        } else {
                            Toast.makeText(MyInfo.this, "Can not add more entries", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    private void setUpWebsite() {
        mWebsiteList = (RecyclerView) findViewById(R.id.myInfo_websiteList);
        mWebsiteList.setHasFixedSize(true);
        mWebsiteList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        websiteList = new ArrayList<>();

        document.collection("website").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        addWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_websiteadd, null);

                final EditText editWebsite = popPhoneLayout.findViewById(R.id.popupWebsite_link);
                final Button saveButton = popPhoneLayout.findViewById(R.id.popupWebsite_save);
                final Button cancelButton = popPhoneLayout.findViewById(R.id.popupWebsite_cancel);

                dialog.setView(popPhoneLayout);
                final AlertDialog alertDialog = dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setClickable(false);
                        final Website website = new Website();
                        website.setWebsite(editWebsite.getText().toString());
                        pd.show();
                        if (websiteList.size() < 2) {
                            document.collection("website").add(website).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    websiteList.add(website);
                                    websiteAdapter.notifyDataSetChanged();
                                    saveButton.setClickable(true);
                                }
                            });
                        } else {
                            Toast.makeText(MyInfo.this, "Can not add more entries", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    private void setUpPhone() {
        mPhoneList = (RecyclerView) findViewById(R.id.myInfo_phoneList);
        mPhoneList.setHasFixedSize(true);
        mPhoneList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        phoneList = new ArrayList<>();
        pd.show();

        document.collection("phone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo.this);
                LayoutInflater inflater = LayoutInflater.from(MyInfo.this);
                View popPhoneLayout = inflater.inflate(R.layout.popup_phoneadd, null);

                final EditText editPhone = popPhoneLayout.findViewById(R.id.popupPhone_phone);
                final Button saveButton = popPhoneLayout.findViewById(R.id.popupPhone_save);
                final Button cancelButton = popPhoneLayout.findViewById(R.id.popupPhone_cancel);

                dialog.setView(popPhoneLayout);
                final AlertDialog alertDialog = dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setClickable(false);
                        final Phone phone = new Phone();
                        phone.setNumber(editPhone.getText().toString());
                        pd.show();
                        if (phoneList.size() < 2) {
                            document.collection("phone").add(phone).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    phoneList.add(phone);
                                    phoneAdapter.notifyDataSetChanged();
                                    saveButton.setClickable(true);
                                }
                            });
                        } else {
                            Toast.makeText(MyInfo.this, "Can not add more entries", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
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

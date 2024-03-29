package com.example.slickkwear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetCodeActivity extends AppCompatActivity {

    private TextInputLayout verify1, verify2, verify3, verify4;
    private Button verificationButton;
    private TextView resend_OTP, resend_otp_counter, phone_number_reset_code_sent;
    private String userID;

    private FirebaseFirestore userRef;


    private static final int REQ_USER_CONSENT = 200;
    //    private static final int READ_SMS = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);

        userRef = FirebaseFirestore.getInstance();

        userID = getIntent().getStringExtra("userID");

        verify1 = (TextInputLayout) findViewById(R.id.verify_1);
        verify2 = (TextInputLayout) findViewById(R.id.verify_2);
        verify3 = (TextInputLayout) findViewById(R.id.verify_3);
        verify4 = (TextInputLayout) findViewById(R.id.verify_4);

        verificationButton = (Button) findViewById(R.id.reset_code_btn);

        resend_OTP = (TextView) findViewById(R.id.resend_OTP);
        resend_otp_counter = (TextView) findViewById(R.id.resend_OTP_counter);
        phone_number_reset_code_sent = (TextView) findViewById(R.id.phone_number_reset_code_sent);

        phone_number_reset_code_sent.setText("+" + userID);

        startSmsUserConsent();

        liveDataValidate();

        verificationButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        validateDataOnBtnClick();

                    }
                }
        );

        resendCodeCountDownTimer();

        resend_OTP.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveResetCode();
                    }
                }
        );
    }

    private void resendCodeCountDownTimer(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend_otp_counter.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
//                resend_otp_counter1.setText("done!");
                resend_otp_counter.setVisibility(View.GONE);
                resend_OTP.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void saveResetCode() {

        Random rand = new Random();
        String OTP = String.format("%04d", rand.nextInt(10000));

        userRef.collection("Users").document(userID).update("UserOTP", OTP)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                resend_OTP(OTP);
                            }
                        }
                );
    }

    private void resend_OTP(String OTP){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String baseUrl = "https://mysms.celcomafrica.com/api/services/sendsms/";
                int partnerId = 2881; // your ID here
                String apiKey = "d72d2587d85c517381ca0daa34ff4c9c"; // your API key
                String shortCode = "CELCOM_SMS"; // sender ID here e.g INFOTEXT, Celcom, e.t.c

                SmsGateway gateway = new SmsGateway(baseUrl, partnerId, apiKey, shortCode);

                String[] strings = {userID};
                String user_msg = OTP + ": is your Password Reset Code for Slickk Wear App.";

                try {
                    String res = gateway.sendBulkSms(user_msg, strings);
                    System.out.println(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        startSmsUserConsent();

        resend_OTP.setVisibility(View.GONE);
        resend_otp_counter.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Reset Code has been sent!", Toast.LENGTH_SHORT).show();
        resendCodeCountDownTimer();
    }

    private void liveDataValidate() {

        verify1.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 1)
                        {
                            verify2.getEditText().requestFocus();
                            verify1.setError(null);
                        }
                        else {
                            verify1.getEditText().requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

        verify2.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 1)
                        {
                            verify3.getEditText().requestFocus();
                            verify2.setError(null);
                        }
                        else {
                            verify1.getEditText().requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

        verify3.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 1)
                        {
                            verify4.getEditText().requestFocus();
                            verify3.setError(null);
                        }
                        else {
                            verify2.getEditText().requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

        verify4.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0)
                        {
                            verify3.getEditText().requestFocus();

                        }
                        else {
                            verify4.setError(null);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );
    }

    private void validateDataOnBtnClick(){
        String v1 = verify1.getEditText().getText().toString();
        String v2 = verify2.getEditText().getText().toString();
        String v3 = verify3.getEditText().getText().toString();
        String v4 = verify4.getEditText().getText().toString();

        if (TextUtils.isEmpty(v1))
        {
            verify1.setError("Required!");
        }
        if (TextUtils.isEmpty(v2))
        {
            verify2.setError("Required!");
        }
        if (TextUtils.isEmpty(v3))
        {
            verify3.setError("Required!");
        }
        if (TextUtils.isEmpty(v4))
        {
            verify4.setError("Required!");
        }

        if (!TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2) && !TextUtils.isEmpty(v3) && !TextUtils.isEmpty(v4)) {
            String OTP_Merged = v1 + v2 + v3 + v4;

            userRef.collection("Users").document(userID).get()
                    .addOnSuccessListener(
                            new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists())
                                    {
                                        if (OTP_Merged.equals(documentSnapshot.getString("UserOTP")))
                                        {
                                            Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                                            intent.putExtra("userID", userID);
                                            startActivity(intent);

                                        }
                                        else {
                                            Toast.makeText(ResetCodeActivity.this, "Wrong Reset Code !!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                    );
        }
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                textViewMessage.setText(
//                        String.format("%s - %s", getString(R.string.received_message), message));

                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String OTP = matcher.group(0);

            char n1 = OTP.charAt(0);
            char n2 = OTP.charAt(1);
            char n3 = OTP.charAt(2);
            char n4 = OTP.charAt(3);

            verify1.getEditText().setText(n1+"");
            verify2.getEditText().setText(n2+"");
            verify3.getEditText().setText(n3+"");
            verify4.getEditText().setText(n4+"");

//            Toast.makeText(this, ""+n1+","+n2+","+n3+","+n4, Toast.LENGTH_SHORT).show();
            validateDataOnBtnClick();
        }
    }


    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
//                        Toast.makeText(PhoneVerificationActivity.this, "Message received !!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

}
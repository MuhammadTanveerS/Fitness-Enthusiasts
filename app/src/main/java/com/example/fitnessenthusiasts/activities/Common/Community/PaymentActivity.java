package com.example.fitnessenthusiasts.activities.Common.Community;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunitySubscriberModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentActivity extends AppCompatActivity {
    Intent intent;
    FirebaseDatabase database;
    String key;

    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "https://thawing-crag-11806.herokuapp.com/";
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        intent = getIntent();
        key =intent.getStringExtra("comKey");


        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51LFI5pHHfmTJBuEkG8iNw6AAYUH0IWylHfpPfh49JOjyquFQ79r6bHUuQofnxCVs367qfw8BWE9TUQTqasNIwBRQ00CBUa17XS")
        );
        startCheckout();
    }

    public void sub(View view) {

        String  userId = FirebaseAuth.getInstance().getUid();
//        String  userId = "B4JP3pkpiQYlvtzi3m6WVSOXtFN2";

        Date now = new Date();
        DateFormat Date = DateFormat.getDateInstance();
        String subDateStart = Date.format(now);

        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date d=c.getTime();
        String subDateExp = Date.format(d);

        CommunitySubscriberModel sub = new CommunitySubscriberModel(userId,subDateStart,subDateExp);
        sub.setCommunityKey(key);

//        database.getReference().child("Communities").child(key)
//        .child("members").child(userId).setValue(sub).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                        .child("joinedCommunity").setValue(key);
//            }
//        });

        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
            stripe.confirmPayment(this, confirmParams);
        }



    }


    private void startCheckout() {
        // Create a PaymentIntent by calling the server's endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        double amount = 5*100;

        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        payMap.put("currency", "usd"); //dont change currency in testing phase otherwise it won't work
        itemMap.put("id", "photo_subscription");
        itemMap.put("amount", amount);
        itemList.add(itemMap);
        payMap.put("items", itemList);
        String json = new Gson().toJson(payMap);

        RequestBody body = RequestBody.create(json,mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));
        // Hook up the pay button to the card widget and stripe instance


    }
    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }


    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );
        paymentIntentClientSecret = responseMap.get("clientSecret");
    }


    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<PaymentActivity> activityRef;
        PayCallback(@NonNull PaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Errorrr: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Errorrrrr: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }


    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<PaymentActivity> activityRef;
        PaymentResultCallback(@NonNull PaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        @Override
        public void onError(@NonNull Exception e) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }


}
package com.example.troca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class PaymentActivity extends AppCompatActivity {
    TextView dateCommande ;

    private static final String BACKEND_URL="http://192.168.1.4:3000/pay/create-payment-intent";
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    Button payButton;
    INodeJS myAPI;
    CardInputWidget cardInputWidget;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_payment);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);


        Intent intent=getIntent();
        String idCommande = intent.getStringExtra("idC");
        String date = intent.getStringExtra("dateC");
        String idClient = intent.getStringExtra("emailC");
        /*
        intent.putExtra("emailC",object.getIdClient());
                        intent.putExtra("dateC",object.getDate());
                        intent.putExtra("lieuC",object.getLieu());
                        intent.putExtra("idC",object.getIdCommande());
         */
        dateCommande = findViewById(R.id.dateCommande);
        dateCommande.setText(date);




        //payment
        payButton = findViewById(R.id.BPay);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51HxsFXLHGVndGDY6enVgVBQ9uOLNj61Kqunb72wPjvDXzwmZG2eaUfsQ00GK997A7CRlSH6zRPG6RLm0lYBA1Gz400rryUYxJM")
        );
        startCheckout();
    }

    private void startCheckout() {
        double amount =70000;
        Map<String,Object> payMap = new HashMap<>();
        Map<String,Object> itemMap = new HashMap<>();
        List<Map<String,Object>> itemList =new ArrayList<>();
        payMap.put("currency","usd");
        itemMap.put("id","photo_subscription");
        itemMap.put("amount",amount);
        itemList.add(itemMap);
        payMap.put("items",itemList);
        String json = new Gson().toJson(payMap);
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL)
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardInputWidget cardInputWidget = PaymentActivity.this.findViewById(R.id.cardInputWidget);
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    progress();
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    stripe.confirmPayment(PaymentActivity.this, confirmParams);
                    Intent intent=getIntent();
                    String idCommande = intent.getStringExtra("idC");
                    String date = intent.getStringExtra("dateC");
                    String idClient = intent.getStringExtra("emailC");
                    commandePayee(idCommande,idClient,"7",date);
                    supprimerCommande(idCommande);

                }
            }
        });
    }

    private void commandePayee(String idCommande, String idClient, String idPro, String date) {
        compositeDisposable.add(myAPI.CommandePayee(idCommande,idClient,idPro,date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(PaymentActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.create().show();
    }

    private void redirect() {
        Intent intent= new Intent(this,ChoixType.class);
        startActivity(intent);
    }
    private void progress() {
        ProgressDialog dialog = ProgressDialog.show(this,"","Chargement...",false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
             Toast.makeText(activity, "Error: " + response.toString(), Toast.LENGTH_LONG).show();

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
                activity.AlertSuccess();
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.AlertFailure();
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
    public void AlertSuccess(){
        new SweetAlertDialog(PaymentActivity.this,SweetAlertDialog.SUCCESS_TYPE )
                .setTitleText("Paiement Réuissi")
                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        redirect();
                        finish();
                    }
                }).show();

    }
    public void AlertFailure(){
        new SweetAlertDialog(PaymentActivity.this,SweetAlertDialog.ERROR_TYPE )
                .setTitleText("Echec du paiement")
                .setContentText("Veuillez réessayer!")
                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        redirect();
                    }
                }).show();

    }
    private void supprimerCommande(String id)
    {
        retrofit2.Call<Void> call = myAPI.supprimerCmdR(id);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {

            }
        });
    }
}
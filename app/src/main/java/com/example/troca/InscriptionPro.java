package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class InscriptionPro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {
    ImageView DatePicker;
    INodeJS myApi3;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView editDateN, editName, editMailPr, editPswdPr, editTel, editCin, editNumCin, editAdresse, editRole, cartePro, confirmPswd;
    Button buttonRegisterPr;
    private static int valeur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_inscription_pro);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi3 = retrofit.create(INodeJS.class);
        NumberPicker numberPicker = findViewById(R.id.numPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(20);
        numberPicker.setOnValueChangedListener(this);


        Spinner spinnerCat = findViewById(R.id.spinnerCat);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.categorie, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter1);

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                final String text = parent.getItemAtPosition(position).toString();
                DatePicker = findViewById(R.id.DatePicker);

                editDateN = findViewById(R.id.editDateN);
                editName = findViewById(R.id.editName);
                editMailPr = findViewById(R.id.editMailPr);
                editPswdPr = findViewById(R.id.editPswdPr);
                editTel = findViewById(R.id.editTel);
                editCin = findViewById(R.id.editCin);
                editNumCin = findViewById(R.id.editNumCin);
                editAdresse = findViewById(R.id.editAdresse);
                confirmPswd = findViewById(R.id.confirmPswd);
                cartePro = findViewById(R.id.cartePro);
                buttonRegisterPr = findViewById(R.id.buttonRegisterPr);

                buttonRegisterPr.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        int tel = parseInt(editTel.getText().toString(), 10);
                        int numeroCin = parseInt(editNumCin.getText().toString(), 10);
                        int nbAnnee = 5;
                        String pswd = editPswdPr.getText().toString();
                        String confirm = confirmPswd.getText().toString();
                        String mail = editMailPr.getText().toString();
                        if (mail.indexOf("@") == -1) {
                            new SweetAlertDialog(InscriptionPro.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erreur")
                                    .setContentText("Format de l'Email incorrect!")
                                    .show();
                        } else if (pswd.equals(confirm)) {


                            registerPro(
                                    editName.getText().toString(),
                                    editMailPr.getText().toString(),
                                    editPswdPr.getText().toString(),
                                    tel,
                                    editDateN.getText().toString(),
                                    numeroCin,
                                    editCin.getText().toString(),
                                    editAdresse.getText().toString(),
                                    valeur,
                                    cartePro.getText().toString(),
                                    text
                            );
                        } else {
                            new SweetAlertDialog(InscriptionPro.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erreur")
                                    .setContentText("Les mots de passes ne correspondent pas!")
                                    .show();
                        }

                    }
                });
                DatePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment date = new DatePickerFragment();
                        date.show(getSupportFragmentManager(), "date ");
                    }
                });

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //date

    }

    public String returnNbAnnee(String text) {
        return text;
    }

    private void registerPro(String name, String email, String password, int tel, String dateN, int numCin, String cin, String adresse, int nbAnnee, String cartePro, String idC) {
        compositeDisposable.add(myApi3.registerPro(name, email, password, tel, dateN, numCin, cin, adresse, nbAnnee, cartePro, idC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")) {
                            Toast.makeText(InscriptionPro.this, "Inscrit Avec Succès", Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(InscriptionPro.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Inscription Réuissie")
                                    .setContentText("Veuillez vérifier votre boite mail pour confirmer votre compte")
                                    .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            redirect();
                                        }
                                    }).show();
                        } else {
                            new SweetAlertDialog(InscriptionPro.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erreur")
                                    .setContentText("Vérifier vos paramètres")
                                    .show();
                        }
                    }
                }));
    }

    private void redirect() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = sdf.format(c.getTime());
        editDateN.setText(currentDate);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Log.d("", String.valueOf(i1));
        valeur = i1;
    }

}
package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class InscriptionPro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView DatePicker;
    INodeJS myApi3;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView editDateN ,editName,editMailPr,editPswdPr,editTel,editCin,editNumCin,editAdresse,editRole,cartePro;
    Button buttonRegisterPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_inscription_pro);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi3 = retrofit.create(INodeJS.class);

        Spinner spinner = findViewById(R.id.spinnerExp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.experience,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerCat = findViewById(R.id.spinnerCat);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.categorie,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter1);

        DatePicker = findViewById(R.id.DatePicker);

       editDateN = findViewById(R.id.editDateN);
        editName = findViewById(R.id.editName);
        editMailPr = findViewById(R.id.editMailPr);
        editPswdPr= findViewById(R.id.editPswdPr);
        editTel= findViewById(R.id.editTel);
        editCin = findViewById(R.id.editCin);
        editNumCin= findViewById(R.id.editNumCin);
        editAdresse= findViewById(R.id.editAdresse);
        //editRole= findViewById(R.id.editRole);
        cartePro= findViewById(R.id.cartePro);
        buttonRegisterPr = findViewById(R.id.buttonRegisterPr);
        buttonRegisterPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tel=parseInt(editTel.getText().toString(),10);
                int numeroCin = parseInt( editNumCin.getText().toString(),10);
                int nbAnnee = 5;
                int idC= 2;

                registerPro(
                        editName.getText().toString(),
                        editMailPr.getText().toString(),
                        editPswdPr.getText().toString(),
                        tel,
                        editDateN.getText().toString(),
                        numeroCin,
                        editCin.getText().toString(),
                        editAdresse.getText().toString(),
                        nbAnnee,
                        cartePro.getText().toString(),
                        idC
                );


            }
        });
        //date
        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment date = new DatePickerFragment();
                date.show(getSupportFragmentManager(),"date ");
            }
        });








    }

    private void registerPro(String name, String email, String password, int tel,String dateN,int numCin,String cin,String adresse, int nbAnnee,String cartePro, int idC ) {
        compositeDisposable.add(myApi3.registerPro(name,email,password,tel,dateN,numCin,cin,adresse,nbAnnee,cartePro,idC)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (s.contains("enregistré")){
                    Toast.makeText(InscriptionPro.this, "Inscrit Avec Succès", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(InscriptionPro.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = sdf.format(c.getTime());
        editDateN.setText(currentDate);
    }
}
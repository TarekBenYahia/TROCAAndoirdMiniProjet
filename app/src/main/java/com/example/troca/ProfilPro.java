package com.example.troca;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ProfilPro extends Fragment {
    private TextView emailP;
    private ImageView modifP;
    private INodeJS myApi;
    static String JSONS = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_pro, container, false);
        emailP = view.findViewById(R.id.emailP);
        final EditText editTelP = view.findViewById(R.id.editTelP);
        editTelP.setVisibility(view.INVISIBLE);
        final TextView telP = view.findViewById(R.id.telP);
        final ImageView done = view.findViewById(R.id.done);
        done.setVisibility(view.INVISIBLE);
        final TextView adresseP = view.findViewById(R.id.adresseP);
        TextView cinP = view.findViewById(R.id.cinP);
        modifP = view.findViewById(R.id.modifP);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("ProData", MODE_PRIVATE);
        String display = sharedPreferences.getString("display", "");
        JSONS = display;
        try {
            JSONObject p = new JSONObject(display);
            emailP.setText(p.getString("emailPro"));
            telP.setText(p.getString("telPro"));
            adresseP.setText(p.getString("adressePro"));
            cinP.setText(p.getString("numCinPro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        modifP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telP.setVisibility(view.INVISIBLE);
                editTelP.setVisibility(view.VISIBLE);
                done.setVisibility(view.VISIBLE);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Retrofit retrofit = RetrofitClient.getInstance();
                        myApi = retrofit.create(INodeJS.class);
                        try {
                            JSONObject p = new JSONObject(JSONS);
                            String idP = p.getString("idPro");
                            Call<Void> call = myApi.modifierTel(idP, editTelP.getText().toString());
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getActivity(), "Numéro de téléphone modifié", Toast.LENGTH_SHORT).show();
                                    telP.setVisibility(view.VISIBLE);
                                    editTelP.setVisibility(view.INVISIBLE);
                                    done.setVisibility(view.INVISIBLE);
                                    telP.setText(editTelP.getText().toString());
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return view;
    }


}

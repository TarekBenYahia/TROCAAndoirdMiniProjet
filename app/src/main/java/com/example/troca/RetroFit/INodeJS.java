package com.example.troca.RetroFit;

import java.util.Date;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST ("cl/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @POST ("cl/inscription")
    @FormUrlEncoded
    Observable<String> registerUser (@Field("name") String nom,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("tel") int tel,
                                    @Field("dateN")String dateN,
                                    @Field("numCIN") int numCIN,
                                    @Field("cin") String cin,
                                    @Field("adresse") String adresse,
                                    @Field("role") String role

                                    );
    @POST ("cl/ForgotPass")
    @FormUrlEncoded
    Observable<String> resetPass (@Field("email") String email);
}

package com.example.troca.RetroFit;

import com.example.troca.Annonce;
import com.example.troca.Comment;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @POST ("api/v1/annonce")
    @FormUrlEncoded
    Observable<String> ajoutAnnonce(@Field("titreAnnonce") String titreAnnonce,
                                    @Field("descriptionAnnonce") String descriptionAnnonce,
                                    @Field("photoAnnonce") String photoAnnonce,
                                    @Field("idClient") int idClient,

                                    @Field("idCategorie") int idCategorie);
    @POST ("commentaire")
    @FormUrlEncoded
    Observable<String> ajoutCommentaire(@Field("Contenu") String Contenu,
                                    @Field("idAnnonce") int complaint,
                                    @Field("idClient") int idClient);


    @GET("api/v1/annonce")
    Call<List<Annonce>> getAnnonce();


    @GET("commentaire")
    Call<List<Comment>> getCommentaire();


}

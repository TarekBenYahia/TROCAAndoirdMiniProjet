package com.example.troca.RetroFit;

import com.example.troca.Annonce;
import com.example.troca.Client;
import com.example.troca.CommandesNonValidés;
import com.example.troca.Professionnel;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface INodeJS {
    @POST ("cl/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @GET ("cl/mailClient/{id}")
    Observable<String> getmail(@Path("id") String id);


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
    @POST ("pro/inscriptionPro")
    @FormUrlEncoded
    Observable<String> registerPro (@Field("name") String nom,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("tel") int tel,
                                     @Field("dateN")String dateN,
                                     @Field("numCIN") int numCIN,
                                     @Field("cin") String cin,
                                     @Field("adresse") String adresse,
                                     @Field("nbAnnee") int nbAnnee,
                                     @Field("cartePro") String cartePro,
                                     @Field("idC") int idC

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

    @GET("api/v1/annonce")
    Call<List<Annonce>> getAnnonce();
    @GET("cl/client")
    Call<List<Client>> getClient();

    @DELETE("cl/suppr/{id}")
    Call<Void> supprimer(@Path("id") String id);

    @GET("cl/professionnel")
    Call<List<Professionnel>> getPro();

    @POST ("cl/ajoutCommande/")
    @FormUrlEncoded
    Observable<String> ajouterCommande (@Field("idClient") int idClient,
                                    @Field("idPro") int idPro,
                                    @Field("date") String date,
                                    @Field("lieu") String lieu,
                                    @Field("prix") int prix
    );

    @POST ("commentaire/")
    @FormUrlEncoded
    Observable<String> ajouterCommentaire (@Field("idClient") String idClient,
                                           @Field("Contenu") String Contenu,
                                        @Field("idAnnonce") String idAnnonce,
                                        @Field("nbrLike")String nbrLike
    );

    @GET("cl/commandeNonValides/{idPro}")
    Call<List<CommandesNonValidés>> getCommandes(@Path("idPro") int idPro);


    @PUT("cl/accepterCommande/{idCommande}")
    Call<Void> accepterCommande(@Path("idCommande") String idCommande);

    @POST ("cl/refuserCommande")
    @FormUrlEncoded
    Observable<String> refuserCommande (@Field("idCommande") String idCommande,
                                     @Field("idClient") String idClient,
                                     @Field("idPro") String idPro,
                                     @Field("date") String date,
                                     @Field("lieu")String lieu

    );
    @DELETE("cl/supprimerCommande/{id}")
    Call<Void> supprimerCmdR(@Path("id") String id);








}

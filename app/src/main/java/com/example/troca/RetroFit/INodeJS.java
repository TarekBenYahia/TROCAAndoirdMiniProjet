package com.example.troca.RetroFit;

import com.example.troca.Annonce;
import com.example.troca.Client;
import com.example.troca.CommandesNonValidés;
import com.example.troca.Professionnel;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

import com.example.troca.Commentaires.AddCommentResponse;
import com.example.troca.Commentaires.AddCommnetRequest;
import com.example.troca.Commentaires.GetCommRequest;
import com.example.troca.Commentaires.GetCommResponse;

public interface INodeJS {
    @POST ("cl/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @GET ("cl/mailClient/{id}")
    Observable<String> getmail(@Path("id") String id);

    @GET("pro/NomPro/{idPro}")
    Observable<String> getEmailPro(@Path("idPro") String idPro);


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
                                     @Field("idCategorie") String idC

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
                                    @Field("idCategorie") String idCategorie);

    @GET("api/v1/annonce")
    Call<List<Annonce>> getAnnonce();
    @GET("cl/client")
    Call<List<Client>> getClient();

    @PUT("cl/modifTel/{idPro}")
    @FormUrlEncoded
    Call<Void> modifierTel(@Path("idPro") String idPro,
                           @Field ("telP") String telP);

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

    @POST ("cl/CommandesPayes")
    @FormUrlEncoded
    Observable<String> CommandePayee (@Field("idCommande") String idCommande,
                                        @Field("idClient") String idClient,
                                        @Field("idPro") String idPro,
                                        @Field("date") String date
    );


    @DELETE("cl/supprimerCommande/{id}")
    Call<Void> supprimerCmdR(@Path("id") String id);

    @GET("cl/commandeNonValidesClient/{idClient}")
    Call<List<CommandesNonValidés>> getCommandesClient(@Path("idClient") int idClient);

    @GET("cl/commandeValidesClient/{idClient}")
    Call<List<CommandesNonValidés>> getCommandesClientValides (@Path("idClient") int idClient);

    @POST("commentaire/addcomment")
    Call<AddCommentResponse>addComment(@Body AddCommnetRequest addCommnetRequest);

    @POST("commentaire/getcommentairebyidannonce")
    Call<List<GetCommResponse>>getCommByAnnonce(@Body GetCommRequest getCommRequest);

    @PUT("cl/changePassword/{pswd}")
    @FormUrlEncoded
    Call<Void> modifierMdp(@Path("pswd") String pswd,
                           @Field ("password") String password);

    @Multipart
    @PUT("cl/upload/{emailClient}")
    Call<ResponseBody> postImage(@Path("emailClient") String emailClient,
                                 @Part MultipartBody.Part image,
                                 @Part("upload") RequestBody name);


    //Notation Pro pro/NoterPr/tarek@tarek
    @PUT("pro/NoterPr/{idPro}")
    @FormUrlEncoded
    Call<Void> noterPro(@Path("idPro") String idPro,
                           @Field ("note") String note);

    @PUT("pro/NoterCommande/{idCommande}")
    @FormUrlEncoded
    Call<Void> noteCommande(@Path("idCommande") String idCommande,
                           @Field ("note") String note);










}

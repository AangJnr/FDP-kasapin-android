package org.grameen.fdp.kasapin.data.network;


import org.grameen.fdp.kasapin.data.db.model.FormsDataWrapper;
import org.grameen.fdp.kasapin.data.db.model.RecommendationsDataWrapper;
import org.grameen.fdp.kasapin.data.db.model.User;
import org.grameen.fdp.kasapin.data.network.model.LoginRequest;
import org.grameen.fdp.kasapin.data.network.model.LoginResponse;
import org.grameen.fdp.kasapin.data.network.model.Response;
import org.grameen.fdp.kasapin.utilities.AppConstants;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AangJnr on 05, December, 2018 @ 4:13 PM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */

public interface FdpApi {
    @POST(AppConstants.API_VERSION + "auth/user/login")
    Single<LoginResponse> makeLoginCall(@Body LoginRequest.ServerLoginRequest loginRequest);

    @GET(AppConstants.API_VERSION + "auth/user/details")
    Single<User> getUser(@Query("token") String token);

    @GET(AppConstants.API_VERSION + "auth/user/survey/{id}")
    Single<FormsDataWrapper> getSurveyData(@Path("id") int countryId, @Query("token") String token);


    @GET(AppConstants.API_VERSION + "auth/user/survey/{id}")
    Single<FormsDataWrapper> getSurveyData2(@Path("id") int countryId, @Query("token") String token);

    @GET(AppConstants.API_VERSION + "auth/user/recommendation/{crop_id}")
    Single<RecommendationsDataWrapper> getRecommendations(@Path("crop_id") int countryId, @Query("token") String token);


    @POST(AppConstants.API_VERSION + "auth/user/farmers")
    Single<Response> postFarmers(@Query("token") String token, @Body JSONObject farmersJson);






}

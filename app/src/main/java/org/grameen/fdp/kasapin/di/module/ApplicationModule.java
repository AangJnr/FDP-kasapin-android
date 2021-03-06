package org.grameen.fdp.kasapin.di.module;


import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;

import org.grameen.fdp.kasapin.BuildConfig;
import org.grameen.fdp.kasapin.data.db.AppDatabase;
import org.grameen.fdp.kasapin.data.network.FdpApi;
import org.grameen.fdp.kasapin.data.network.FdpApiService;
import org.grameen.fdp.kasapin.data.network.RetrofitInterceptor;
import org.grameen.fdp.kasapin.data.prefs.AppPreferencesHelper;
import org.grameen.fdp.kasapin.data.prefs.PreferencesHelper;
 import org.grameen.fdp.kasapin.di.Scope.ApplicationContext;
 import org.grameen.fdp.kasapin.utilities.AppConstants;

import javax.inject.Singleton;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AangJnr on 19, September, 2018 @ 11:04 PM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */


@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application app) {
        application = app;
    }


    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }


    @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    public AppDatabase providesDatabase() {

         /* Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        };*/
        return Room.databaseBuilder(application, AppDatabase.class, AppConstants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }



    @Singleton
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Singleton
    @Provides
    SharedPreferences providesSharedPrefs() {
        return application.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }


    @Singleton
    @Provides
    CompositeDisposable providesCompositeDisposable(){
        return new CompositeDisposable();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        if(BuildConfig.DEBUG) {
            return new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }else{
            return new OkHttpClient.Builder().build();
        }
    }




    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient client){

            return new Retrofit.Builder().baseUrl(providesSharedPrefs().getString(AppConstants.SERVER_URL, BuildConfig.END_POINT))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }




    @Singleton
    @Provides
    FdpApi providesFdpApi(Retrofit retrofit){
        return retrofit.create(FdpApi.class);
    }




    @Singleton
    @Provides
    FdpApiService providesFdpApiService(FdpApi fdpApi) {
        return new FdpApiService(fdpApi);
    }




}
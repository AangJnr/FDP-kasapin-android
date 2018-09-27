package org.grameen.fdp.kasapin.ui.base;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import org.grameen.fdp.kasapin.data.db.entity.Activity;

/**
 * Created by AangJnr on 19, September, 2018 @ 4:25 PM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */

public class BaseContract {

     public interface Presenter<T extends View> {

        /**
         * Binds presenter with a view when resumed. The Presenter will perform initialization here.
         *
         * @param view the view associated with this presenter
         */
        void takeView(T view);

        /**
         * Drops the reference to the view when destroyed
         */
        void dropView();


         void setUserAsLoggedOut();


         boolean isViewAttached();


         void openNextActivity();


         void toggleFullScreen(Boolean hideNavBar);


         void onTokenExpire();



     }



    public interface View<T> {

        void openNextActivity();

        void showLoading();

        void hideLoading();

        void openLoginActivityOnTokenExpire();

        void onError(@StringRes int resId);

        void onError(String message);

        void showMessage(String message);

        void showMessage(@StringRes int resId);

        boolean isNetworkConnected();

        void hideKeyboard();

        void onToggleFullScreenClicked(Boolean hideNavBar);

        void showDialog(Boolean cancelable, String title, String message, DialogInterface.OnClickListener onPositiveButtonClickListener, String positiveText, DialogInterface.OnClickListener onNegativeButtonClickListener, String negativeText, int icon_drawable);
    }
}
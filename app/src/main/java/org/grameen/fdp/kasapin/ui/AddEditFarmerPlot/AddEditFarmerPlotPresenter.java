package org.grameen.fdp.kasapin.ui.AddEditFarmerPlot;


import org.grameen.fdp.kasapin.R;
import org.grameen.fdp.kasapin.data.AppDataManager;
import org.grameen.fdp.kasapin.data.db.entity.FormAndQuestions;
import org.grameen.fdp.kasapin.data.db.entity.Plot;
import org.grameen.fdp.kasapin.ui.base.BaseActivity;
import org.grameen.fdp.kasapin.ui.base.BasePresenter;
import org.grameen.fdp.kasapin.utilities.AppConstants;
import org.grameen.fdp.kasapin.utilities.AppLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AangJnr on 18, September, 2018 @ 9:06 PM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */

public class AddEditFarmerPlotPresenter extends BasePresenter<AddEditFarmerPlotContract.View> implements AddEditFarmerPlotContract.Presenter{
    AppDataManager mAppDataManager;



    @Inject
    public AddEditFarmerPlotPresenter(AppDataManager appDataManager) {
        super(appDataManager);
        this.mAppDataManager = appDataManager;


    }




    @Override
    public void openNextActivity() {
        }

    @Override
    public void getPlotQuestions() {
        runSingleCall(getAppDataManager().getDatabaseManager().formAndQuestionsDao().getFormAndQuestionsByDisplayType("Plot form")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(formAndQuestions -> getView().showForm(formAndQuestions), throwable -> {
                    getView().showMessage(R.string.error_has_occurred);
                    throwable.printStackTrace();
                }));

    }


    @Override
    public void saveData(Plot plot, String flag) {
        runSingleCall(Single.fromCallable(() ->  getAppDataManager().getDatabaseManager().plotsDao().insertOne(plot))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    getView().showMessage("Plot data saved!");

                    getAppDataManager().setBooleanValue("reload", true);

                    if(flag != null && flag.equalsIgnoreCase("map"))
                        getView().moveToMapActivity(plot);
                    else
                        getView().showPlotDetailsActivity(plot);


                }, throwable -> {
                    getView().showMessage("An error occurred saving plot data. Please try again.");
                    throwable.printStackTrace();
                }));

    }
}

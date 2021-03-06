package org.grameen.fdp.kasapin.di.component;



import org.grameen.fdp.kasapin.di.Scope.PerActivity;
import org.grameen.fdp.kasapin.di.module.ViewModule;
import org.grameen.fdp.kasapin.ui.AddEditFarmerPlot.AddEditFarmerPlotActivity;
import org.grameen.fdp.kasapin.ui.addFarmer.AddEditFarmerActivity;
import org.grameen.fdp.kasapin.ui.farmerProfile.FarmerProfileActivity;
import org.grameen.fdp.kasapin.ui.form.fragment.DynamicFormFragment;
import org.grameen.fdp.kasapin.ui.landing.LandingActivity;
import org.grameen.fdp.kasapin.ui.main.FarmerListFragment;
import org.grameen.fdp.kasapin.ui.main.MainActivity;
import org.grameen.fdp.kasapin.ui.login.LoginActivity;
import org.grameen.fdp.kasapin.ui.map.MapActivity;
import org.grameen.fdp.kasapin.ui.plotDetails.PlotDetailsActivity;
import org.grameen.fdp.kasapin.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by AangJnr on 20, September, 2018 @ 2:12 AM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */

@PerActivity
@Component(modules = {ViewModule.class}, dependencies = {ApplicationComponent.class})
public interface ActivityComponent {

    void inject(SplashActivity view);
    void inject(LoginActivity view);
    void inject(LandingActivity view);
    void inject(MainActivity view);
    void inject(FarmerListFragment view);
    void inject(AddEditFarmerActivity view);
    void inject(FarmerProfileActivity view);
    void inject(DynamicFormFragment view);
    void inject(AddEditFarmerPlotActivity view);
    void inject(PlotDetailsActivity view);
    void inject(MapActivity view);




}
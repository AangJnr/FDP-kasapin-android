package org.grameen.fdp.kasapin.ui.addFarmer;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.grameen.fdp.kasapin.R;
import org.grameen.fdp.kasapin.data.db.entity.FormAndQuestions;
import org.grameen.fdp.kasapin.data.db.entity.FormAnswerData;
import org.grameen.fdp.kasapin.data.db.entity.RealFarmer;
import org.grameen.fdp.kasapin.ui.base.BaseActivity;
import org.grameen.fdp.kasapin.ui.farmerProfile.FarmerProfileActivity;
import org.grameen.fdp.kasapin.ui.form.fragment.DynamicFormFragment;
import org.grameen.fdp.kasapin.ui.main.MainActivity;
import org.grameen.fdp.kasapin.utilities.ActivityUtils;
import org.grameen.fdp.kasapin.utilities.AppConstants;
import org.grameen.fdp.kasapin.utilities.AppLogger;
import org.grameen.fdp.kasapin.utilities.CustomToast;
import org.grameen.fdp.kasapin.utilities.ImageUtil;
import org.grameen.fdp.kasapin.utilities.TimeUtils;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * A login screen that offers login via email/password.
 */
public class AddEditFarmerActivity extends BaseActivity implements AddEditFarmerContract.View {

    @Inject
    AddEditFarmerPresenter mPresenter;

    String IMAGE_URL = "";
    Uri URI;

    @BindView(R.id.farmerName)
    EditText farmerName;

    @BindView(R.id.farmerCode)
    EditText farmerCode;

    @BindView(R.id.save)
    Button save;

    @BindView(R.id.takeImage)
    TextView takePhoto;

    @BindView(R.id.villageSpinner)
    MaterialSpinner villageSpinner;

    @BindView(R.id.educationLevelSpinner)
    MaterialSpinner educationLevelSpinner;

    @BindView(R.id.genderSpinner)
    MaterialSpinner genderSpinner;
    @BindView(R.id.birthdayYearEdittext)
    EditText birthYearEdittext;

    @BindView(R.id.photo)
    CircleImageView circleImageView;

    @BindView(R.id.initials)
    TextView initials;

    RealFarmer FARMER;

    boolean isEditMode = false;
    boolean isNewFarmer = true;
    boolean shouldSaveData = true;

    String BASE64_STRING = "";

    List<String> villageList = new ArrayList<>();

    String[] educationLevels = {"Primary", "Secondary", "Tertiary", "Professional Course", "Other"};
    String[] genders = {"Male", "Female"};
    private boolean newDataSaved = false;
    private FormAndQuestions CURRENT_FORM_QUESTION;
    private DynamicFormFragment dynamicFormFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmer_details);


        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        mPresenter.takeView(this);


        if (getIntent() != null) {

            FARMER = gson.fromJson(getIntent().getStringExtra("farmer"), RealFarmer.class);
            if (FARMER != null) {
                isNewFarmer = false;

            }
                setUpViews();
            }


    }


    @Override
    public void setUpViews() {


        /**
         * Temporarily load dummy data
         *
         **/

     getAppDataManager().getCompositeDisposable().add(getAppDataManager().getDatabaseManager().villagesDao().getAll()
             .subscribeOn(Schedulers.io())
             .flatMap(villages -> Observable.fromIterable(villages)
                .doOnNext(village -> villageList.add(village.getName())).toList())
             .observeOn(AndroidSchedulers.mainThread())
     .subscribe(onSuccess -> {

         villageSpinner.setItems(villageList);
         if(!villageList.isEmpty())
         villageSpinner.setSelectedIndex(0);
     }));




        /**
         * If its Monitoring mode or farmer has agreed with fdp (is submitted/has agreed) and his
         * data has been synced, prevent views from being accessible - View Only Mode
         * Agents can still edit data if the farmer has agreed but agent has not synced data to server yet
         * @param boolean isMonitoring
         *                hasAgreed
         * @param int syncStatus
         */


        /**
         * Load the form fragment
         *
         *
         *
         */

        CURRENT_FORM_QUESTION = FORM_AND_QUESTIONS.get(CURRENT_FORM);


        //Fix Spinners


        educationLevelSpinner.setItems(educationLevels);
        educationLevelSpinner.setSelectedIndex(0);

        genderSpinner.setItems(genders);
        genderSpinner.setSelectedIndex(0);

        if (!isNewFarmer) {

            if (getAppDataManager().isMonitoring() || (FARMER.hasAgreed() && FARMER.getSyncStatus() == 1)) {
                disableViews();

                setToolbar("View Farmer Details");
            } else setToolbar("Edit Farmer Data");


            farmerName.setText(FARMER.getFarmerName());
            farmerCode.setText(FARMER.getCode());
            birthYearEdittext.setText(FARMER.getBirthYear());


            villageSpinner.setText(FARMER.getVillageName().toUpperCase());
            educationLevelSpinner.setText(FARMER.getEducationLevel());

            if (FARMER.getImageUrl() != null && !FARMER.getImageUrl().isEmpty()) {
                try {
                    circleImageView.setImageBitmap(ImageUtil.base64ToBitmap(BASE64_STRING));
                } catch (Exception ignored) {
                }
            } else {

                circleImageView.setImageBitmap(null);

                if (FARMER.getFarmerName().contains(" ")) {
                    String[] valueArray = FARMER.getFarmerName().split(" ");
                    String value = valueArray[0].substring(0, 1) + valueArray[1].substring(0, 1);
                    initials.setText(value);
                } else {
                    if (!FARMER.getFarmerName().trim().isEmpty())
                        initials.setText(FARMER.getFarmerName().substring(0, 1));
                }

                int[] mColors = getResources().getIntArray(R.array.recommendations_colors);
                int randomColor = mColors[new Random().nextInt(mColors.length)];
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(1000);
                drawable.setColor(randomColor);
                circleImageView.setBackground(drawable);
            }


            mPresenter.loadFormFragment(FARMER.getCode(), CURRENT_FORM_QUESTION.getForm().getId());


        } else {

            FARMER = new RealFarmer();
            FARMER.setCode(UUID.randomUUID().toString());
            farmerCode.setText(FARMER.getCode());


            setToolbar("Add a new Farmer");
            save.setText("Save");


            /**
             * As a new farmer, get the farmer profile form and questions and display to the farmer
             *
             **/


            for(FormAndQuestions formAndQuestions : FORM_AND_QUESTIONS){
                if(formAndQuestions.getForm().getFormNameC().equalsIgnoreCase(AppConstants.FARMER_PROFILE)){
                    CURRENT_FORM_QUESTION = formAndQuestions;
                    break;
                }
            }

            showFormFragment(null);

           // mPresenter.loadFormFragment(CURRENT_FORM_QUESTION, false, "", mAppDataManager.isMonitoring());
        }




    }


    @Override
    public void showFormFragment(FormAnswerData answerData){

        dynamicFormFragment = DynamicFormFragment.newInstance(CURRENT_FORM_QUESTION, !isNewFarmer, FARMER.getCode(), getAppDataManager().isMonitoring(), answerData);
        ActivityUtils.loadDynamicView(getSupportFragmentManager(), dynamicFormFragment, CURRENT_FORM_QUESTION.getForm().getFormNameC());

    }


    @OnClick(R.id.save)
    void saveAndContinue() {


        if(farmerName.getText().toString().trim().isEmpty() || farmerName.getText().toString().trim().equalsIgnoreCase(" ")) {
            showMessage(R.string.enter_valid_farmer_name);
            return;
        }

        if(birthYearEdittext.getText().toString().isEmpty()) {
            showMessage("Please enter birth year of farmer");
            return;

        }



            if (isNewFarmer) {
                FARMER = new RealFarmer();
                FARMER.setFirstVisitDate(new Date(System.currentTimeMillis()));
            }
            FARMER.setFarmerName(farmerName.getText().toString().trim());
            FARMER.setBirthYear(birthYearEdittext.getText().toString().trim());
            FARMER.setCode(farmerCode.getText().toString().trim());
            FARMER.setGender(genders[genderSpinner.getSelectedIndex()]);
            FARMER.setEducationLevel(educationLevels[educationLevelSpinner.getSelectedIndex()]);
            FARMER.setVillageId(villageSpinner.getSelectedIndex());
            FARMER.setVillageName(villageList.get(villageSpinner.getSelectedIndex()));
            FARMER.setLastModifiedDate(TimeUtils.getDateTime());
            FARMER.setImageUrl(BASE64_STRING);



            mPresenter.saveData(FARMER, dynamicFormFragment.getSurveyAnswer(), isNewFarmer);



    }




    @Override
    public void moveToNextForm() {


       /* if(dynamicFormFragment != null)
            getSupportFragmentManager().beginTransaction().remove(dynamicFormFragment);
*/
        CURRENT_FORM++;

        if (CURRENT_FORM < FORM_AND_QUESTIONS.size()) {
            //CURRENT_FORM_QUESTION = FORM_AND_QUESTIONS.get(CURRENT_FORM);

            Intent intent = new Intent(this, this.getClass());
            intent.putExtra("farmer", getGson().toJson(FARMER));
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
           //mPresenter.loadFormFragment(CURRENT_FORM_QUESTION, !isNewFarmer, FARMER.getCode(), mAppDataManager.isMonitoring());

        }else
            showFarmerDetailsActivity(FARMER);

    }



    @Override
    public void showFarmerDetailsActivity(RealFarmer farmer) {

        Intent intent = new Intent(this, FarmerProfileActivity.class);
        intent.putExtra("farmer", getGson().toJson(FARMER));
        startActivity(intent);
        finish();

    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }



    @Override
    public void disableViews() {
        findViewById(R.id.save).setVisibility(View.GONE);
        farmerName.setEnabled(false);
        farmerCode.setEnabled(false);
        villageSpinner.setEnabled(false);
        educationLevelSpinner.setEnabled(false);
        genderSpinner.setEnabled(false);
        birthYearEdittext.setEnabled(false);
        takePhoto.setVisibility(View.GONE);
    }


    @OnClick(R.id.takeImage)
    @Override
    public void startCameraIntent() {

        File photo;
        Intent takePictureIntent;

        if (!hasPermissions(this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(AddEditFarmerActivity.this, new String[]{Manifest.permission.CAMERA}, AppConstants.PERMISSION_CAMERA);
        } else {

            //Prevents data being saved on pause
            shouldSaveData = false;

            takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                // place where to store camera taken picture
                photo = createTemporaryFile("picture", ".jpg");
                photo.delete();
                //URI = Uri.fromFile(photo);
                URI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".org.grameen.fdp.provider", photo);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    AppLogger.d(TAG, "Starting camera intent");
                    startActivityForResult(takePictureIntent, AppConstants.CAMERA_INTENT);

                }

            } catch (Exception e) {
                AppLogger.e(TAG, e.getMessage());
            }

        }

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.dropView();
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AppLogger.d(TAG, "RESULT CODE = " + resultCode + " REQUEST CODE " + requestCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.CAMERA_INTENT) {

                Bitmap bitmap = null;
                try {


                    bitmap = ImageUtil.handleSamplingAndRotationBitmap(AddEditFarmerActivity.this, URI);
                    BASE64_STRING = ImageUtil.bitmapToBase64(bitmap);

                    if (circleImageView != null) {
                        circleImageView.setImageBitmap(bitmap);
                        initials.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                    AppLogger.d(TAG, "Failed to load", e);
                }
            }


        } else CustomToast.makeToast(this, "Something went wrong", Toast.LENGTH_LONG).show();
        shouldSaveData = true;

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == AppConstants.PERMISSION_CAMERA)
                startCameraIntent();

        } else {
            showDialog(true, getString(R.string.permission_required), getString(R.string.camera_permission_rationale),
                    (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        startCameraIntent();
                    }, getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss(), getString(R.string.no), 0);
        }
    }


    @Override
    public void setBackListener(@Nullable View view) {

        //Todo save data and exit if user clicks on yes

        showDialog(true, getStringResources(R.string.save_data), getStringResources(R.string.save_data_explanation),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    isNewFarmer = true;

                    saveAndContinue();
                }
                , getStringResources(R.string.yes),

                (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    Intent intent = new Intent(AddEditFarmerActivity.this, FarmerProfileActivity.class);
                    intent.putExtra("farmer", getGson().toJson(FARMER));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);

                }, getStringResources(R.string.no), 0);

    }


    @Override
    public void onBackPressed() {
        setBackListener(null);
    }
}
package org.grameen.fdp.kasapin.utilities;

import android.os.Environment;

import java.io.File;

public class AppConstants {


    public static int TABLET_COLUMN_COUNT = 6;
    public static int PHONE_COLUMN_COUNT = 4;


    public static final String SERVER_URL = "http://104.236.220.235/";
    public static final String API_VERSION = "fdp/api/v1/";


    public static final int PERMISSION_FINE_LOCATION = 1001;
    public static final int PLACE_PICKER_REQUEST = 1002;
    public static final int PERMISSION_CALL = 1003;
    public static int CAMERA_INTENT = 1004;
    public static int PERMISSION_CAMERA = 1005;

    public static final String DATABASE_NAME = "fdp_database";



    public static final String DISPLAY_TYPE_FORM = "form";





    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String DB_NAME = "fdp.db";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String PREF_NAME = "fdp_pref";
    public static final String FARMER_SUBMITTED_YES = "YES";


    public static final long NULL_INDEXL = -1L;
    public static final int NULL_INDEX = -1;

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static String ROOT_DIR = Environment
            .getExternalStorageDirectory() + File.separator + ".fdpkasapin";


    public static String CRASH_REPORTS_DIR = AppConstants.ROOT_DIR + File.separator + "crashReports";
    public static String DATABASE_BACKUP_DIR = AppConstants.ROOT_DIR + File.separator + "databaseBackups";


    public static final String TYPE_TEXT = "text";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_NUMBER_DECIMAL = "decimal";
    public static final String TYPE_COMPLEX_CALCULATION = "complex calculation";

    public static final String ANSWERS = "fpd_Answer__c";

    public static final String EMPTY_STRING = "";
    public static final String RESPONSE_SUCCESS = "0";
    public static final String RESOPNSE_ERROR = "1";

    public static final String SUBMISSION = "submission";
    public static final String RESPONSE_CODE = "responseCode";
    public static final String FARMER_ID = "farmerId";

    public static final int SYNC_STATUS_COMPLETE = 0;
    public static final int SYNC_STATUS_PARTIAL_SYNC = 1;
    public static final int SYNC_STATUS_NO_SYNC = -1;



    public static final String ID = "Id";

    public static final String NULL_STRING = "null";
    public static final String TYPE_LOCATION = "geolocation";
    public static final String TYPE_PHOTO = "photo";

    public static final String TYPE_SELECTABLE = "single select";
    public static final String TYPE_MULTI_SELECTABLE = "multi select";

    public static final String TYPE_CHECKBOX = "checkbox";
    public static final String TYPE_TIMEPICKER = "timePicker";
    public static final String TYPE_DATEPICKER = "date";

    public static final String TYPE_FORMULA = "formula";

    public static final String TYPE_MATH_FORMULA = "math formula";
    public static final String TYPE_LOGIC_FORMULA = "logic formula";


    public static final String SHOW = "false";
    public static final String HIDE = "true";


    public static final String FARMER_PROFILE = "farmer profile";
    public static final String ADOPTION_OBSERVATION_RESULTS = "adoption observation results";
    public static final String AGGREGATE_ECONOMIC_RESULTS = "agregate economic results";
    public static final String OTHER = "other";
    public static final String ADDITIONAL_INTERVENTION = "additional intervention";
    public static final String ADOPTION_OBSERVATIONS = "adoption observations";
    public static final String PRODUCTIVE_PROFILE = "productive profile";
    public static final String FARMING_ECONOMIC_PROFILE = "farming economic profile";
    public static final String SOCIO_ECONOMIC_PROFILE = "socioeconomic profile";
    public static final String FAMILY_MEMBERS = "family members";
    public static final String PLOT_RESULTS = "plot results";
    public static final String PLOT_INFORMATION = "plot information";

    public static final String AO_MONITORING = "ao monitoring";
    public static final String AO_MONITORING_RESULT = "ao monitoring result";
    public static final String MONITORING_PLOT_INFORMATION = "monitoring plot information";

    public static final String COMPETENCE_MONITORING = "competence monitoring";
    public static final String FAILURE_MONITORING = "failure monitoring";

    public static final String FDP_STATUS = "fdp status";





    public static final String DIAGNOSTIC = "diagnostic";
    public static final String MONITORING = "monitoring";



    public static final String TAG_TITLE_TEXT_VIEW = "titleTag";
    public static final String BUTTON_VIEW = "buttonTag";
    public static final String TAG_OTHER_TEXT_VIEW = "textTag";
    public static final String TAG_CALCULATION = "calculationTag";
    public static final String TAG_VIEW = "viewTag";
    public static final String TAG_RESULTS = "resultsTag";
    public static String IS_USER_SIGNED_IN = "isUserSignedIn";
    public static String IS_RETAILER_SIGNED_IN = "isUserSignedIn";
    public static String IS_GAME_CENTER_SIGNED_IN = "isUserSignedIn";
    public static String USERS_PHONE = "phoneNo";
    public static String USER_NAME = "userName";
    public static String USER_AGE = "userAge";
    public static String USER_EMAIL = "userEmail";
    public static String USER_UID = "userUid";
    public static String USER_TOKEN = "userToken";
    public static String USER_PHOTO_LOCAL_URL = "userProfilePhotoLocal";
    public static String USER_PHOTO_CLOUD_URL = "userProfilePhotoCloud";
    public static String IS_NIGHT_MODE = "isNightMode";
    public static int LAST_SELECTED_FRAGMENT = 1;
    public static int SYNC_OK = 1;
    public static int SYNC_NOT_OK = 0;

    public static final String NO_MONITORING_PLACE_HOLDER = "N/A - Please complete monitoring A0";
    //Token Constants
    public static int TOKEN_IF = 1;
    public static int TOKEN_VARIABLE = 2;
    public static int TOKEN_EQUAL_TO = 3;
    public static int TOKEN_BRAKET_CLOSED = 4;
    public static int TOKEN_BRACKET_OPEN = 5;
    public static int TOKEN_PLUS_MINUS = 6;
    public static int TOKEN_INT = 7;
    public static int TOKEN_MUL_DIV = 8;
    public static int TOKEN_EXP = 9;
    public static int TOKEN_MULTIPLIER = 10;
    public static int TOKEN_CHAR = 11;
    String complexCalculation = "IF(farm_weight_units_ghana == Kg,cocoa_production_ly_ghana,cocoa_production_ly_ghana*62.5)";










    private AppConstants(){}

}

package org.health.supplychain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Authentication;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.AuthenticationService;
import org.health.supplychain.service.IAuthenticationService;
import org.health.supplychain.service.OfficerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private boolean hasTimeMistmatch = false;
    private String currentServerTime;

    // UI references.
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private IAuthenticationService authenticationService;
    private IUserSessionService userSessionService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authenticationService = new AuthenticationService(getApplicationContext());

        // Set up the login form.
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.userName);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }







    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        Intent intent;

        //TODO: remove this part
        // remove this part of the code
//        intent = new Intent(getApplicationContext(), MenuGridActivity.class);
//        startActivity(intent);
//        finish();
        // remove this part of the code

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

//        addEmailsToAutoComplete(emails);
        addEmailsToAutoComplete(authenticationService.getAuthenticatedUsernameList());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mUserNameView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        WeakHashMap<String, String> authStatusMap;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                authStatusMap = authenticationService.authenticateUser(mUsername, mPassword);
                if(authStatusMap != null &&  authStatusMap.get(Constants.TIME_SYNC_STATUS) != null &&
                        authStatusMap.get(Constants.TIME_SYNC_STATUS).equals(Constants.TIME_SYNC_FAILURE)){
                    currentServerTime = authStatusMap.get(Constants.TIME_SYNC_SERVER_TIME);
                    hasTimeMistmatch = true;
                    return false;
                }
                else if(authStatusMap != null &&
                        authStatusMap.get(Constants.AUTHENTICATION_STATUS).equals(Constants.AUTHENTICATION_SUCCESS))
                    return true;
                else if(authStatusMap != null &&
                        authStatusMap.get(Constants.AUTHENTICATION_STATUS).equals(Constants.AUTHENTICATION_FAILURE))
                    return false;
                else
                    return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);




            if (success != null && success == true) {
                //save session information and redirect to main activity
                String sessionId = "";
                if(authStatusMap != null)
                    sessionId = authStatusMap.get(Constants.HEADER_JSESSION);

                Authentication authentication = authenticationService.getCurrentAuthentication(sessionId);
                if(authentication != null) {
                    userSessionService = new UserSessionService();
                    userSessionService.createOrUpdateUserSessionDetail(getApplicationContext(), authentication);
                }

                /******************/

                // TODO: save officer and basic setup information

                /******************/


//                Intent intent = new Intent(getApplicationContext(), MenuGridActivity.class);
                Intent intent = new Intent(getApplicationContext(), ChooseLocationActivity.class);
//                Intent intent = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);
                startActivity(intent);
                finish();

            } else {



                if(hasTimeMistmatch) {
                    showTimeMistmatchDetail();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    private boolean hasBasicOfflineSetting(){
//        boolean hasBasicOfflineSetting = true;
//        OfficerService officerService = new OfficerService();
//        UserSession userSession = userSessionService.getUserSessionDetail(getApplicationContext());
//        Officer officer = officerService.getOfficerById(userSession.getOfficerId(), userSession.getOrganizationId());
//        if(officer != null){
//            hasBasicOfflineSetting &= officer.getOrganizationCreditLogic() != 0;
//            hasBasicOfflineSetting &= (officer.getUsername() != null && !officer.getUsername().equals(""));
//            hasBasicOfflineSetting &= (officer.getEncryptedPassword() != null && !officer.getEncryptedPassword().equals(""));
//            return hasBasicOfflineSetting;
//
//        } {
//            return false;
//        }

        return true;
    }


    private void showTimeMistmatchDetail(){
        AlertDialog.Builder messageDialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        messageDialog.setTitle(R.string.time_sync_failure_title);
        messageDialog.setMessage(R.string.time_sync_server_time).setMessage(R.string.new_line).setMessage(currentServerTime);

//        messageDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
        messageDialog.show();
    }




    //Sample user
    private static final String FI_OFFICER_EMAIL = "fi";
    private static final String PC_OFFICER_EMAIL = "pc";
}


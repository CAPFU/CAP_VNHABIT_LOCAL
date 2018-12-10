package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class BaseActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void signInwithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                });
    }

    public void showMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void editHabitDetails(String habitId) {
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, habitId);
        startActivityForResult(intent, HabitActivity.REQUEST_UPDATE);
    }

    public void showStatics(View view) {
        Intent intent = new Intent(this, StaticsActivity.class);
        startActivity(intent);
        finish();
    }

    public void showProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void showNoteScreen(String habitId) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, habitId);
        startActivity(intent);
        finish();
    }

    public void showDetailsChart(String habitId) {
        Intent intent = new Intent(this, ReportDetailsActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, habitId);
        startActivity(intent);
        finish();
    }

    public void showOnCalendar(String habitId) {
        Intent intent = new Intent(this, ReportCalendarActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, habitId);
        startActivity(intent);
        finish();
    }

    public void finishThis(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public void showEmptyScreen() {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }
}

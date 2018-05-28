package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.test.jokeview.JokeViewActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Connected tests to check the button interaction and the AsyncTask to retrieve a joke from the backend
 * These tests behave differently if the emulator is online or offline
 * Either way, the 'startAppEngine' gradle task has to be executed before testing or use the
 * 'runServerAndTest' gradle task to automatically start and shut down the server
 */
public class EndpointsAsyncTaskTest {

    private boolean deviceIsConnected;

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void init(){
        deviceIsConnected = isDeviceConnected(activityTestRule.getActivity());
    }

    @Test
    public void runEndpointsTask_receivesNonEmptyStringViaCallback() {
        EndpointsAsyncTask.EndpointAsyncTaskCallback callback = new EndpointsAsyncTask.EndpointAsyncTaskCallback() {
            @Override
            public void onResultReceived(boolean successful, String result) {
                System.out.println("Device connection status: " + deviceIsConnected);
                if (deviceIsConnected){
                    assertThat(successful, is(true));
                    assertThat(result, is(not("")));
                }
                else{
                    assertThat(successful, is(false));
                    // receive error message
                    assertThat(result, is(not("")));
                    System.out.println("Error message: " + result);
                }
            }
        };
        new EndpointsAsyncTask(callback).execute();
    }

    @Test
    public void clickJokeButton_intentIsCalledOrErrorMessageIsShown(){
        intending(isInternal()).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
        onView(withId(R.id.tell_joke_b)).perform(ViewActions.click());
        if (deviceIsConnected){
            // launch intent
            intended(allOf(hasComponent(JokeViewActivity.class.getName()), hasExtraWithKey(JokeViewActivity.JOKE_KEY)));
        }
        else{
            // show toast with error message
            onView(withText(R.string.fetch_error))
                    .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));
        }
    }

    private boolean isDeviceConnected(Context context) {
        ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
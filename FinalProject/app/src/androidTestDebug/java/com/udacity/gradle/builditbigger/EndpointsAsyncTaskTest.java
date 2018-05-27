package com.udacity.gradle.builditbigger;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EndpointsAsyncTaskTest {

    @Test
    public void runEndpointsTask_receivesNonEmptyStringViaCallback() {
        EndpointsAsyncTask.EndpointAsyncTaskCallback callback1 = new EndpointsAsyncTask.EndpointAsyncTaskCallback() {
            @Override
            public void onResultReceived(boolean successful, String result) {
                assertThat(successful, is(true));
                assertThat(result, is(not("")));
            }
        };
        new EndpointsAsyncTask(callback1).execute();
    }
}
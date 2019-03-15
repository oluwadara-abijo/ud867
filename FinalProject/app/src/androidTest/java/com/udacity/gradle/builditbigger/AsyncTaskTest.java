package com.udacity.gradle.builditbigger;

import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest implements OnRetrieveJokeListener {

    private String mJoke = null;
    private CountDownLatch mLatch;

    @Test
    public void AsyncTest() {
        try {
            EndpointsAsyncTask.getInstance(this);
            mLatch = new CountDownLatch(1);
            mLatch.await(15, TimeUnit.SECONDS);

            //Check that joke is a non-empty string
            assertNotNull("Joke is null", mJoke);
            assertFalse("Joke string is empty", mJoke.isEmpty());
        } catch (Exception e) {
            fail("Joke retrieval exception: " + e.getMessage());
        }
    }

    @Override
    public void onRetrieveJokeFinish(@Nullable String result) {
        mJoke = result;
        mLatch.countDown();
    }

    @Override
    public void onRetrieveJokeStart() {
    }
}
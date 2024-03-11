package com.orenda.taimo.grade3tograde5;

/**
 * Created by Mateen Sheikh on 13/12/2017.
 */

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

//import com.firebase.jobdispatcher.JobParameters;
//import com.firebase.jobdispatcher.JobService;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
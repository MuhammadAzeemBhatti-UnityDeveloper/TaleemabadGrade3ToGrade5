package com.orenda.taimo.grade3tograde5;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import static com.facebook.FacebookSdk.getApplicationContext;


public class DownloadTask extends AsyncTask<String, String, String> {
    String URLString = null;
    String fileName = null;
    String size = null;
    private Context context;
    private final int TIMEOUT_CONNECTION = 5000;//5sec
    private final int TIMEOUT_SOCKET = 30000;//30sec
    private static final int MEGABYTE = 1024 * 1024;
    File audioFile;
    String filePath = "";
    boolean started = false;
    SimpleExoPlayer simpleExoPlayer;
    //private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private final String streamUrl  = "https://text-to-speech-output-bucket.s3-eu-west-1.amazonaws.com/QuestionBank/";

    public DownloadTask(Context context, String URL, String fileName) {
        this.context = context;
        this.URLString = URL;
        this.fileName = fileName;

    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            String urlS = streamUrl + URLString + ".mp3";
            URL url= new URL(urlS);
            long startTime = System.currentTimeMillis();
            URLConnection ucon = url.openConnection();

            //this timeout affects how long it takes for the app to realize there's a connection problem
            ucon.setReadTimeout(TIMEOUT_CONNECTION);
            ucon.setConnectTimeout(TIMEOUT_SOCKET);


          //  File myDirectory = new File(Environment.getExternalStorageDirectory(), "WebTestAudios");
            File myDirectory = new File(context.getFilesDir(), "TestAudios");
            //    File myDirectory = context.getDir("CV", Context.MODE_PRIVATE);

            if(!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            File gpxfile = new File(myDirectory,fileName+"temp");
            audioFile = gpxfile;
            if(!gpxfile.exists()){

                try {
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            filePath = gpxfile.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(gpxfile);
            InputStream is = ucon.getInputStream();

            byte[] buffer = new byte[4096];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }

            fos.close();
            is.close();
            File file1=new File(myDirectory,fileName);
            boolean check =gpxfile.renameTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    @Override
    protected void onPreExecute() {
        Log.wtf("-DownloadTask", "Started");
    }

    @Override
    protected void onPostExecute(String result) {
        Log.wtf("-DownloadTask", "-DownLoaded : " + result);
        // playOfflineAudio(Uri.parse(result));
        //    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+m +"/"+ fileName);
    }


    private void playOfflineAudio(Uri uri) {

        DataSpec dataSpec = new DataSpec(uri);
        final FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.open(dataSpec);
        } catch (FileDataSource.FileDataSourceException e) {
            e.printStackTrace();
        }

        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(
                context,
                null,
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
        );
        TrackSelector trackSelector = new DefaultTrackSelector();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                renderersFactory,
                trackSelector
        );

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return fileDataSource;
            }
        };
        final MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                factory, new DefaultExtractorsFactory(), null, null);


        simpleExoPlayer.prepare(audioSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_ENDED) {
                  //  simpleExoPlayer.prepare(audioSource);
                  //  simpleExoPlayer.setPlayWhenReady(true);
                    Log.wtf("Notif", " RESTARTED");
                }
            }
        });

    }

}
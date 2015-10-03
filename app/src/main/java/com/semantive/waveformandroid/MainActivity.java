package com.semantive.waveformandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.semantive.waveformandroid.waveform.WaveformFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CustomWaveformFragment())
                    .commit();
        }
    }

    public static class CustomWaveformFragment extends WaveformFragment {

        @Override
        protected String getFileName() {
            return "path to your audio file";
        }
    }
}

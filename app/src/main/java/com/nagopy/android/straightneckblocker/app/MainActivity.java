package com.nagopy.android.straightneckblocker.app;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.nagopy.android.straightneckblocker.App;
import com.nagopy.android.straightneckblocker.R;
import com.nagopy.android.straightneckblocker.databinding.ActivityMainBinding;
import com.nagopy.android.straightneckblocker.viewmodel.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainViewModel viewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getApplicationComponent().inject(this);

        viewModel.onCreate(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(viewModel);

        binding.ad.loadAd(new AdRequest.Builder()
                .addTestDevice("4EB260715A6D70807B32DAAC473002C9")
                .addTestDevice("367CE774B209A0E99A3D1674F2404408")
                .build()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.ad.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.ad.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
        binding.ad.destroy();
    }

    public void startLicenseActivity(MenuItem menuItem) {
        startActivity(new Intent(this, LicenseActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}

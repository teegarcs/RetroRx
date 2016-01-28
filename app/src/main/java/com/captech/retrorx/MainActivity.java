package com.captech.retrorx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String EXTRA_RX = "EXTRA_RX";
    private Button rxCall, retroCall;
    private TextView rxResponse, retroResponse;
    private ProgressBar progressBar;
    private NetworkService service;
    private boolean rxCallInWorks = false;
    private BranchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxCall = (Button)findViewById(R.id.rxCall);
        retroCall = (Button)findViewById(R.id.retroCall);
        rxResponse = (TextView)findViewById(R.id.rxResponse);
        retroResponse = (TextView)findViewById(R.id.retroResponse);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        rxCall.setOnClickListener(this);
        retroCall.setOnClickListener(this);
        service = ((RxApplication)getApplication()).getNetworkService();
        presenter = new BranchPresenter(this, service);
        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retroCall:
                presenter.loadRetroData();
                break;
            case R.id.rxCall:
                rxCallInWorks = true;
                presenter.loadRxData();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX, rxCallInWorks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rxCallInWorks)
            presenter.loadRxData();
    }


    protected void showRxResults(BranchResponse response){
        rxCallInWorks = false;
        rxResponse.setText(response.branchLocations.data.branch.get(0).branchName);
        rxResponse.setVisibility(View.VISIBLE);
        rxCall.setEnabled(true);
        retroCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    protected void showRxFailure(Throwable throwable){
        Log.d("TAG", throwable.toString());
        rxCallInWorks = false;
        rxResponse.setText("ERROR");
        rxResponse.setVisibility(View.VISIBLE);
        rxCall.setEnabled(true);
        retroCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    protected void showRetroResults(Response<BranchResponse> response){
        retroResponse.setText(response.body().branchLocations.data.branch.get(0).branchName);
        retroResponse.setVisibility(View.VISIBLE);
        retroCall.setEnabled(true);
        rxCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    protected void showRetroFailure(Throwable throwable){
        Log.d("TAG", throwable.toString());
        retroResponse.setText("ERROR");
        retroResponse.setVisibility(View.VISIBLE);
        retroCall.setEnabled(true);
        rxCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    protected void showRxInProcess(){
        rxResponse.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        retroCall.setEnabled(false);
        rxCall.setEnabled(false);
    }

    protected void showRetroInProcess(){
        retroResponse.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        retroCall.setEnabled(false);
        rxCall.setEnabled(false);
    }

}

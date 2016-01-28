package com.captech.retrorx;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by cteegarden on 1/28/16.
 */
@SuppressWarnings("unchecked")
public class BranchPresenter {

    private MainActivity view;
    private NetworkService service;
    private Subscription subscription;

    public BranchPresenter(MainActivity view, NetworkService service){
        this.view = view;
        this.service = service;
    }

    public void loadRxData(){
        view.showRxInProcess();
        Observable<BranchResponse> branchResponseObservable = (Observable<BranchResponse>)
                service.getPreparedObservable(service.getAPI().getBranchesObservable(), BranchResponse.class, true, true);
        subscription = branchResponseObservable.subscribe(new Observer<BranchResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(BranchResponse response) {
                view.showRxResults(response);
            }
        });
    }

    public void loadRetroData(){
        view.showRetroInProcess();
        Call<BranchResponse> call = service.getAPI().getBranches();
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Response<BranchResponse> response) {
                view.showRetroResults(response);
            }

            @Override
            public void onFailure(Throwable t) {
                view.showRetroFailure(t);
            }
        });
    }

    public void rxUnSubscribe(){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }


}

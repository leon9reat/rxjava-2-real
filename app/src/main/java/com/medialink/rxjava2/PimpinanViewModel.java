package com.medialink.rxjava2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.rxjava2.model.CrudRespon;
import com.medialink.rxjava2.model.Pimpinan;
import com.medialink.rxjava2.network.ApiClient;
import com.medialink.rxjava2.network.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PimpinanViewModel extends ViewModel {
    private static final String TAG = "PimpinanViewModel";
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;

    private MutableLiveData<List<Pimpinan>> mutaPimpinan;

    public LiveData<List<Pimpinan>> getPimpinan() {
        if (mutaPimpinan == null) {
            mutaPimpinan = new MutableLiveData<>();
            loadPimpinan();
        }
        return mutaPimpinan;
    }

    public void loadPimpinan() {
        apiService = ApiClient.getClient().create(ApiService.class);
        disposable.add(
                apiService.getAllPimpinan()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<List<Pimpinan>, List<Pimpinan>>() {
                            @Override
                            public List<Pimpinan> apply(List<Pimpinan> pimpinans) throws Exception {
                                Collections.sort(pimpinans, new Comparator<Pimpinan>() {
                                    @Override
                                    public int compare(Pimpinan o1, Pimpinan o2) {
                                        return o1.getIdPimpinan() - o2.getIdPimpinan();
                                    }
                                });
                                return pimpinans;
                            }
                        })
                        .subscribeWith(new DisposableSingleObserver<List<Pimpinan>>() {
                            @Override
                            public void onSuccess(List<Pimpinan> pimpinans) {
                                mutaPimpinan.postValue(pimpinans);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    public void createPimpinan(Pimpinan data) {
        disposable.add(
                apiService.createPimpinan(data.toHashMap())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CrudRespon>() {
                            @Override
                            public void onSuccess(CrudRespon crudRespon) {
                                Log.d(TAG, "onSuccess: insert data");

                                ArrayList<Pimpinan> list;
                                list = (ArrayList<Pimpinan>) mutaPimpinan.getValue();
                                list.add(data);

                                mutaPimpinan.postValue(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    public void updatePimpinan(Pimpinan data, final int position) {
        disposable.add(
                apiService.updatePimpinan(data.getIdPimpinan(), data.toHashMap())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "update pimpinan " + data.getIdPimpinan());

                                ArrayList<Pimpinan> list;
                                list = (ArrayList<Pimpinan>) mutaPimpinan.getValue();
                                list.get(position).setNmPimpinan(data.getNmPimpinan());
                                list.get(position).setJabatan(data.getJabatan());

                                mutaPimpinan.postValue(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    public void deletePimpinan(Pimpinan pimpinan, final int position) {
        disposable.add(
                apiService.deletePimpinan(pimpinan.getIdPimpinan())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "hapus data " + pimpinan.getIdPimpinan());

                                ArrayList<Pimpinan> temp;
                                temp = (ArrayList<Pimpinan>) mutaPimpinan.getValue();
                                temp.remove(position);
                                mutaPimpinan.postValue(temp);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}

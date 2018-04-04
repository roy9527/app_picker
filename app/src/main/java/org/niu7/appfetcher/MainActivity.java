package org.niu7.appfetcher;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void fetchInstalledApp() {
        try {
            List<ApplicationInfo> list = getPackageManager().getInstalledApplications(0);
            Observable.fromIterable(list)
                    .flatMap(new Function<ApplicationInfo, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(ApplicationInfo applicationInfo)
                                throws Exception {
                            return Observable.just(applicationInfo.sourceDir);
                        }
                    })
                .subscribeOn(Schedulers.io())
                .subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

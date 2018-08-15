package com.savics.savics;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.savics.savics.utils.AppSharedPreferences;
import com.savics.savics.utils.event.AndroidBusProvider;
import com.squareup.otto.Bus;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.Locale;

public class MainApplication extends Application {

  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_WRITE = 3;
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_CONNECTION_LOST = 6;
  public static final int MESSAGE_UNABLE_CONNECT = 7;


  public static final String DEVICE_NAME = "device_name";
  public static final String TOAST = "toast";

  private static final Bus BUS = new Bus();

  private static Context context;

  private static AppSharedPreferences mPrefs;

  private static final String TAG = MainApplication.class.getName();

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  @Override public void onCreate() {
    super.onCreate();
    AndroidBusProvider.getInstance().register(this);
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration
        .Builder()
        .deleteRealmIfMigrationNeeded()
        .build();
    Realm.setDefaultConfiguration(config);
    Fresco.initialize(this);
    context = getApplicationContext();




  }




  public static Realm getRealm(){
    return Realm.getDefaultInstance();
  }

  public static Locale getCurrentLocale() {
    Locale current = context.getResources().getConfiguration().locale;
    return current;
  }

  @Override public void onTerminate() {
    Log.d(TAG, "I have terminated");
    AndroidBusProvider.getInstance().unregister(this);
    super.onTerminate();

  }



  public static Bus getBus() {
    return BUS;
  }

  public static Context getContext() {
    return context;
  }

  public static AppSharedPreferences getPrefs() {
    return getPrefs(MainApplication.getContext());
  }

  public static AppSharedPreferences getPrefs(Context context) {
    if (mPrefs == null) {
      mPrefs = new AppSharedPreferences(context);
    }
    return mPrefs;
  }




}

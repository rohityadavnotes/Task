package com.task.ui.common;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.task.R;
import com.task.internet.NetworkStateChangeListener;
import com.task.internet.NetworkStateChangeReceiver;
import com.task.internet.RegisterAndUnregisterNetworkReceiver;
import com.task.ui.common.views.BaseActivityView;
import com.task.utils.LogcatUtil;
import com.task.utils.ToastUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public  abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements BaseActivityView, HasAndroidInjector {

    private RegisterAndUnregisterNetworkReceiver registerAndUnregisterNetworkReceiver;
    private static final String TAG = BaseActivity.class.getSimpleName();

    /***********************************************************************************************
     ***************************************** Properties ******************************************
     **********************************************************************************************/

    protected T binding;

    /***********************************************************************************************
     ****************************** Dagger 2 configuration *****************************************
     **********************************************************************************************/

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    /***********************************************************************************************
     ****************************** Activity abstract methods **************************************
     **********************************************************************************************/

    protected abstract void initializePresenter();

    protected abstract void initializeObject();

    protected abstract void initializeToolBar();

    protected abstract void onTextChangedListener();

    protected abstract void initializeEvent();

    /***********************************************************************************************
     ********************** Perform View Binding in Generic Way ************************************
     **********************************************************************************************/

    private void performViewBinding(){
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /***********************************************************************************************
     ******************************* Activity lifecycle methods ************************************
     **********************************************************************************************/

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        performViewBinding();
        initializePresenter();
        initializeObject();
        initializeToolBar();
        onTextChangedListener();
        initializeEvent();
        registerAndUnregisterNetworkReceiver= new RegisterAndUnregisterNetworkReceiver(BaseActivity.this);
        checkInternetConnection();
    }

    /***********************************************************************************************
     **************************************** Accessors methods ************************************
     **********************************************************************************************/
    @Override
    public void showToast(String message) {
        LogcatUtil.informationMessage(TAG, message);
        ToastUtil.makeCustomToast(this,message);
    }

    @Override
    public void showToast(int messageResId) {
        LogcatUtil.informationMessage(TAG, ""+messageResId);
        ToastUtil.makeCustomToast(this,messageResId);
    }

    private void checkInternetConnection() {
        NetworkStateChangeReceiver.setNetworkStateChangeListener(new NetworkStateChangeListener() {
            @Override
            public void networkAvailable() {
                Toast.makeText(getApplicationContext(), "Now you are connected to Internet!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkUnavailable() {
                Toast.makeText(getApplicationContext(), "You are not connected to Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        registerAndUnregisterNetworkReceiver.registerNetworkReceiver(BaseActivity.this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        registerAndUnregisterNetworkReceiver.unregisterNetworkReceiver(BaseActivity.this);
        super.onPause();
    }

    /***********************************************************************************************
     ***************************** Activity Open Close Animation ***********************************
     **********************************************************************************************/

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent != null && intent.getComponent() != null) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (intent != null && intent.getComponent() != null) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

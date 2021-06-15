package com.task.ui.common;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.task.ui.common.views.BaseFragmentView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends ViewBinding> extends MyAppFragment implements BaseFragmentView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    /***********************************************************************************************
     ***************************************** Properties ******************************************
     **********************************************************************************************/

    protected T binding;

    protected BaseActivity baseActivity;
    protected View rootView;

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

    private View performViewBinding(ViewGroup viewGroup){
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class,ViewGroup.class,boolean.class);
            binding = (T) method.invoke(null, getLayoutInflater(),viewGroup,false);
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
        assert binding != null;
        return binding.getRoot();
    }

    /***********************************************************************************************
     ******************************* Activity lifecycle methods ************************************
     **********************************************************************************************/

    @CallSuper
    @Override
    public void onAttach(@NonNull Context context) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            /*
             * Perform injection here for M (API 23) due to deprecation of onAttach(Activity)
             */
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.baseActivity = activity;
        }
    }

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            /*
             * Perform injection here before M, L (API 22) and below because onAttach(Context)
             * is not yet available at L.
             */
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            this.baseActivity = baseActivity;
        }
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        initializePresenter();
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = performViewBinding(container);
        return rootView;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeObject();
        initializeToolBar();
        onTextChangedListener();
        initializeEvent();
    }

    @CallSuper
    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************************************************
     **************************************** Accessors methods ************************************
     **********************************************************************************************/
    @Override
    public void showToast(String message) {
        getBaseActivity().showToast(message);
    }

    @Override
    public void showToast(int messageResId) {
        getBaseActivity().showToast(messageResId);
    }
}
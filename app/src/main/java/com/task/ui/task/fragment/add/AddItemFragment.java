package com.task.ui.task.fragment.add;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.task.data.remote.NetworkService;
import com.task.data.remote.oberver.ResponseObserver;
import com.task.databinding.FragmentAddItemBinding;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.model.AddItem;
import com.task.progressdialog.DotProgressDialog;
import com.task.ui.common.AppSection;
import com.task.ui.common.BaseFragment;
import com.task.ui.task.fragment.FragmentConstants;
import com.task.ui.task.fragment.add.views.AddItemView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends BaseFragment<FragmentAddItemBinding> implements AddItemView {

    public static final String TAG = AddItemFragment.class.getSimpleName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle
     * @return A new instance of fragment AddItemFragment.
     */
    public static AddItemFragment newInstance(Bundle bundle) {
        AddItemFragment fragment = new AddItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddItemFragment() {
        this.isRootSection = true;
        this.appSection = AppSection.ADD_ITEM;
    }

    /***********************************************************************************************
     ***************************************** Properties ******************************************
     **********************************************************************************************/

    private String bundleData;

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    NetworkService networkService;

    private String nameString="";
    private String categoryString="";
    private String rateString="";

    /***********************************************************************************************
     ******************************* Activity lifecycle methods ************************************
     **********************************************************************************************/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundleData = getArguments().getString("bundle_data");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTaskActivity().setActionBarTitle(FragmentConstants.ADD_ITEM);
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void initializeObject() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void onTextChangedListener() {
        binding.itemNameTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    binding.itemNameTextInputLayout.setErrorEnabled(true);
                    binding.itemNameTextInputLayout.setError("Please Enter Item Name");
                }
                if (text.length() > 0) {
                    binding.itemNameTextInputLayout.setError(null);
                    binding.itemNameTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = binding.itemNameTextInputEditText.getText().toString();
                if (value.length()<1) {
                    binding.itemNameTextInputLayout.setError("Please Enter Item Name");
                }
            }
        });

        binding.categoryTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    binding.categoryTextInputLayout.setErrorEnabled(true);
                    binding.categoryTextInputLayout.setError("Please Enter Category Name");
                }
                if (text.length() > 0) {
                    binding.categoryTextInputLayout.setError(null);
                    binding.categoryTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = binding.categoryTextInputEditText.getText().toString();
                if (value.length()<1) {
                    binding.categoryTextInputLayout.setError("Please Enter Category Name");
                }
            }
        });

        binding.rateTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    binding.rateTextInputLayout.setErrorEnabled(true);
                    binding.rateTextInputLayout.setError("Please Enter Rate");
                }
                if (text.length() > 0) {
                    binding.rateTextInputLayout.setError(null);
                    binding.rateTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = binding.rateTextInputEditText.getText().toString();
                if (value.length()<1) {
                    binding.rateTextInputLayout.setError("Please Enter Rate");
                }
            }
        });
    }


    @Override
    protected void initializeEvent() {

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString              = binding.itemNameTextInputEditText.getText().toString();
                categoryString              = binding.categoryTextInputEditText.getText().toString();
                rateString              = binding.rateTextInputEditText.getText().toString();

                String noSpaceStr = categoryString.replaceAll("\\s", ""); // using built in method
                if(validation())
                {
                   addItem(nameString,noSpaceStr,rateString);
                }
            }
        });

        binding.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FragmentConstants.TOOLBAR_HEADING,FragmentConstants.CATEGORY);
                getTaskActivity().navigateToSection(AppSection.CATEGORY, true,bundle);
            }
        });
    }

    public void addItem(String nameString, String categoryString, String rateString)
    {
        DotProgressDialog.getInstance().showProgress(getActivity(),false);

        Observable<Response<AddItem>> observableSingle = networkService.addItem(nameString,categoryString,rateString);
        observableSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<AddItem>() {
                    @Override
                    public void onResponse(Response<AddItem> response) {
                        DotProgressDialog.getInstance().hideProgress();
                        assert response.body() != null;
                        if(response.body().getResponseCode()==200)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString(FragmentConstants.TOOLBAR_HEADING,FragmentConstants.CATEGORY);
                            getTaskActivity().navigateToSection(AppSection.CATEGORY, true,bundle);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNetworkError(String errorMessage) {
                        DotProgressDialog.getInstance().hideProgress();
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onServerError(String errorMessage) {
                        DotProgressDialog.getInstance().hideProgress();
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean validation() {
        if (Objects.requireNonNull(binding.itemNameTextInputEditText.getText()).toString().length()< 1)
        {
            String message = "Please Enter Item Name";
            binding.itemNameTextInputLayout.setError(message);
            return false;
        }
        if (Objects.requireNonNull(binding.categoryTextInputEditText.getText()).toString().length()< 1)
        {
            String message = "Please Enter Category Name";
            binding.categoryTextInputLayout.setError(message);
            return false;
        }
        if (Objects.requireNonNull(binding.rateTextInputEditText.getText()).toString().length()< 1)
        {
            String message = "Please Enter Rate";
            binding.rateTextInputLayout.setError(message);
            return false;
        }
        return true;
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /***********************************************************************************************
     ****************************** Activity abstract methods **************************************
     **********************************************************************************************/

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
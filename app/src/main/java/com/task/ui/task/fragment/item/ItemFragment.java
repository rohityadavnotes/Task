package com.task.ui.task.fragment.item;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.task.data.remote.NetworkService;
import com.task.model.ItemList;
import com.task.ui.task.fragment.FragmentConstants;
import com.task.databinding.FragmentItemBinding;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.ui.common.AppSection;
import com.task.ui.common.BaseFragment;
import com.task.ui.task.fragment.item.adapter.ItemAdapter;
import com.task.ui.task.fragment.item.presenters.ItemPresenterImpl;
import com.task.ui.task.fragment.item.views.ItemView;
import com.task.utils.LogcatUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ItemFragment extends BaseFragment<FragmentItemBinding> implements ItemView {

    public static final String TAG = ItemFragment.class.getSimpleName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle
     * @return A new instance of fragment ItemFragment.
     */
    public static ItemFragment newInstance(Bundle bundle) {
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ItemFragment() {
        this.isRootSection = false;
        this.appSection = AppSection.ITEM;
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

    private ItemPresenterImpl<ItemView> presenter;

    private ArrayList<ItemList> itemArrayList;
    private ItemAdapter itemAdapter;

    /***********************************************************************************************
     **************************************** Accessors methods ************************************
     **********************************************************************************************/

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
            bundleData = getArguments().getString(FragmentConstants.CAT_ID);
            getTaskActivity().setActionBarTitle(getArguments().getString(FragmentConstants.TOOLBAR_HEADING));
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new ItemPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    protected void initializeObject() {
        itemArrayList = new ArrayList<>();

        binding.itemRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.itemRecyclerView.setLayoutManager(linearLayoutManager);
        binding.itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        binding.itemRecyclerView.addItemDecoration(itemDecoration);

        presenter.getItem( bundleData,networkService);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void onTextChangedListener() {

    }

    @Override
    protected void initializeEvent() {

    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
        LogcatUtil.debuggingMessage(TAG,"onDestroy() call");
    }

    /***********************************************************************************************
     ****************************** Activity abstract methods **************************************
     **********************************************************************************************/

    @Override
    public void showProgressBar() {
        binding.itemProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.itemProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(List<ItemList> itemLists) {
        itemArrayList.addAll(itemLists);
        itemAdapter = new ItemAdapter(getActivity(),itemArrayList);
        binding.itemRecyclerView.setAdapter(itemAdapter);
    }

    @Override
    public void onFailed(String errorMessage) {
        showToast(errorMessage);
    }
}
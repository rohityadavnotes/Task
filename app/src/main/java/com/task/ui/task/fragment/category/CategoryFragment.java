package com.task.ui.task.fragment.category;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.task.data.remote.NetworkService;
import com.task.model.CategoryList;
import com.task.ui.task.fragment.FragmentConstants;
import com.task.databinding.FragmentCategoryBinding;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.ui.common.AppSection;
import com.task.ui.common.BaseFragment;
import com.task.ui.common.adapter.RecyclerViewItemClickListener;
import com.task.ui.common.adapter.RecyclerViewTouchListener;
import com.task.ui.task.fragment.cart.CartFragment;
import com.task.ui.task.fragment.category.adapter.CategoryAdapter;
import com.task.ui.task.fragment.category.presenters.CategoryPresenterImpl;
import com.task.ui.task.fragment.category.views.CategoryView;
import com.task.utils.LogcatUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding> implements CategoryView {

    public static final String TAG = CartFragment.class.getSimpleName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle
     * @return A new instance of fragment CategoryFragment.
     */
    public static CategoryFragment newInstance(Bundle bundle) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public CategoryFragment() {
        this.isRootSection = false;
        this.appSection = AppSection.CATEGORY;
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

    private CategoryPresenterImpl<CategoryView> presenter;

    private ArrayList<CategoryList> categoryArrayList;
    private CategoryAdapter categoryAdapter;

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
        getTaskActivity().setActionBarTitle(FragmentConstants.CATEGORY);
    }

    @Override
    protected void initializePresenter() {
        presenter = new CategoryPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    protected void initializeObject() {
        categoryArrayList = new ArrayList<>();

        binding.categoryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.categoryRecyclerView.setLayoutManager(linearLayoutManager);
        binding.categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        binding.categoryRecyclerView.addItemDecoration(itemDecoration);

        presenter.getCategory(networkService);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void onTextChangedListener() {

    }

    @Override
    protected void initializeEvent() {
        binding.categoryRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(context, binding.categoryRecyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FragmentConstants.TOOLBAR_HEADING,categoryArrayList.get(position).getCategoryName());
                bundle.putString(FragmentConstants.CAT_ID, categoryArrayList.get(position).getId().toString());
                getTaskActivity().navigateToSection(AppSection.ITEM, true,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FragmentConstants.TOOLBAR_HEADING,categoryArrayList.get(position).getCategoryName());
                bundle.putString(FragmentConstants.CAT_ID, categoryArrayList.get(position).getId().toString());
                getTaskActivity().navigateToSection(AppSection.ITEM, true,bundle);
            }
        }));
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
        binding.categoryProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.categoryProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(List<CategoryList> categoryLists) {
        categoryArrayList.addAll(categoryLists);
        categoryAdapter = new CategoryAdapter(getActivity(),categoryArrayList);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onFailed(String errorMessage) {
        showToast(errorMessage);
    }
}
package com.task.ui.task.fragment.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.utils.UpdateTotalPriceListener;
import com.task.data.local.sqlite.CartHelper;
import com.task.ui.task.fragment.FragmentConstants;
import com.task.data.local.sqlite.Cart;
import com.task.databinding.FragmentCartBinding;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.ui.common.AppSection;
import com.task.ui.common.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;

import javax.inject.Inject;

public class CartFragment extends BaseFragment<FragmentCartBinding> implements UpdateTotalPriceListener {

    public static final String TAG = CartFragment.class.getSimpleName();

    public CartFragment() {
        this.isRootSection = true;
        this.appSection = AppSection.CART;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle
     * @return A new instance of fragment CartFragment.
     */
    public static CartFragment newInstance(Bundle bundle) {
        CartFragment fragment = new CartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /***********************************************************************************************
     ***************************************** Properties ******************************************
     **********************************************************************************************/

    private String bundleData;
    public static double totalAmount = 0.0;

    @Inject
    @ApplicationContext
    Context context;

    private ArrayList<Cart> cartArrayList;
    private CartAdapter cartAdapter;
    private CartHelper cartHelper;
    /***********************************************************************************************
     **************************************** Accessors methods ************************************
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
        getTaskActivity().setActionBarTitle(FragmentConstants.CART);
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected void initializeObject() {
        cartHelper = new CartHelper(context);
        cartArrayList = new ArrayList<>();

        binding.cartRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.cartRecyclerView.setLayoutManager(linearLayoutManager);
        binding.cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        binding.cartRecyclerView.addItemDecoration(itemDecoration);

        cartArrayList.addAll(cartHelper.getAllRowsFromFirstTable());

        if(cartArrayList.size() >0)
        {
            for (int j = 0; j < cartArrayList.size(); j++)
            {
                totalAmount = totalAmount + Double.valueOf(cartArrayList.get(j).getItemTotalPrice());
            }
        }

        binding.totalPriceTextView.setText(totalAmount+" \u20B9");
        cartAdapter = new CartAdapter(getActivity(),cartArrayList,this);
        binding.cartRecyclerView.setAdapter(cartAdapter);
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

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onUpdate(double newTotalAmount, String itemId) {
        binding.totalPriceTextView.setText(newTotalAmount+" \u20B9");
        totalAmount = newTotalAmount;
    }
}
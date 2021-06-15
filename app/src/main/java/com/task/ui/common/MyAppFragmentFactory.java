package com.task.ui.common;

import android.os.Bundle;

import com.task.ui.task.fragment.add.AddItemFragment;
import com.task.ui.task.fragment.cart.CartFragment;
import com.task.ui.task.fragment.category.CategoryFragment;
import com.task.ui.task.fragment.item.ItemFragment;

public class MyAppFragmentFactory {

    public static MyAppFragment getFragment(AppSection section, Bundle bundle) {
        switch (section) {
            case ADD_ITEM:
                return AddItemFragment.newInstance(bundle);
            case CATEGORY:
                return CategoryFragment.newInstance(bundle);
            case ITEM:
                return ItemFragment.newInstance(bundle);
            case CART:
                return CartFragment.newInstance(bundle);
            default:
                return null;
        }
    }
}
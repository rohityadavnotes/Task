package com.task.di.builder;

import com.task.ui.task.fragment.add.AddItemFragment;
import com.task.ui.task.fragment.cart.CartFragment;
import com.task.ui.task.fragment.category.CategoryFragment;
import com.task.ui.task.fragment.item.ItemFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract AddItemFragment bindAddItemFragment();

    @ContributesAndroidInjector
    abstract CategoryFragment bindCategoryFragment();

    @ContributesAndroidInjector
    abstract ItemFragment bindItemFragment();

    @ContributesAndroidInjector
    abstract CartFragment bindCartFragment();
}

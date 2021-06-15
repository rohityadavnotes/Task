package com.task.ui.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.task.customview.BadgeDrawable;
import com.task.data.local.sqlite.Cart;
import com.task.data.local.sqlite.CartHelper;
import com.task.ui.task.fragment.FragmentConstants;
import com.task.R;
import com.task.databinding.ActivityTaskBinding;
import com.task.ui.common.AppSection;
import com.task.ui.common.BaseActivity;
import com.task.ui.common.MyAppFragment;
import com.task.ui.common.MyAppFragmentFactory;
import com.task.ui.task.presenters.TaskPresenterImpl;
import com.task.ui.task.views.TaskView;
import com.task.utils.LogcatUtil;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends BaseActivity<ActivityTaskBinding> implements TaskView {

    private static final String TAG = TaskActivity.class.getSimpleName();

    private static final String INSTANCE_SELECTED_FRAGMENT = "selected_fragment_instance";
    private ActionBar actionBar;
    private int badgeCount = 0;
    private FragmentManager fragmentManager;
    private Menu menu;
    private CartHelper cartHelper;
    /***********************************************************************************************
     ***************************************** Properties ******************************************
     **********************************************************************************************/

    private TaskPresenterImpl<TaskView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        onFragmentBackStackChanged();
        if (savedInstanceState == null)
        {
            /**
             * App should start on the home section
             * notice we won't be adding it to the back stack. Pressing back
             * should exist the app
             */
            Bundle productCategoryBundle = new Bundle();
            productCategoryBundle.putString(FragmentConstants.TOOLBAR_HEADING,FragmentConstants.ADD_ITEM);
            navigateToSection(AppSection.ADD_ITEM, false,productCategoryBundle);
        }
        else
        {
            /**
             * The app has been recreated, we can obtain the saved state and restore the looks of the toolbar
             * notice we won't be adding it to the back stack. Pressing back
             * should exist the app
             */
            AppBarState savedAppBarState = (AppBarState) savedInstanceState.getSerializable(INSTANCE_SELECTED_FRAGMENT);
            if (savedAppBarState == AppBarState.USE_WITHOUT_BACK)
            {
                showRootNavigation();
            }
            else
            {
                showBackArrowNavigation();
            }
        }

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initializePresenter() {
        presenter = new TaskPresenterImpl<>();
        presenter.attachView(TaskActivity.this);
    }

    @Override
    protected void initializeObject() {
        fragmentManager     = getSupportFragmentManager();
        cartHelper = new CartHelper(TaskActivity.this);
        updateProductBadge(cartHelper.numberOfRows());
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

    private void setupToolbar() {
        setSupportActionBar(binding.toolBar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
    }

    public void setActionBarTitle(String title) {
        if (actionBar != null) {
            binding.toolBarTitleTextView.setText(title);
        }
    }

    /**
     * Display the hamburger icon on the application bar
     */
    public void showRootNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * Display the back arrow on the application bar
     */
    public void showBackArrowNavigation() {
        /**
         * Order of these calls IS important
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Navigates to a fragment within the app
     *
     * @param section    where to go
     * @param addToStack should this transaction be added to the back stack ?
     */
    public void navigateToSection(AppSection section, boolean addToStack, Bundle bundle) {

        /*
         * Obtain the fragment we want to navigate to
         */
        MyAppFragment fragment = MyAppFragmentFactory.getFragment(section,bundle);

        /*
         * If this is a root fragment, clear everything up to the home and add this one
         */
        assert fragment != null;
        if (fragment.getIsRootSection())
        {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        /*
         * Normal transaction stuff
         */
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToStack)
        {

            fragmentTransaction.addToBackStack(fragment.getFragmentTag());
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
        }

        fragmentTransaction.replace(R.id.container, fragment, fragment.getFragmentTag());
        fragmentTransaction.commit();
    }

    public void onFragmentBackStackChanged() {
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
        {
            @Override
            public void onBackStackChanged()
            {
                MyAppFragment currentFragment = getCurrentFragment();
                if (currentFragment != null) {

                    if (currentFragment.getIsRootSection())
                    {
                        showRootNavigation();
                    }
                    else
                    {
                        /*
                         * This is not a "root" fragment. Use back arrow navigation on the app bar
                         */
                        showBackArrowNavigation();
                    }
                }
            }
        });
    }

    /**
     * Get the current visible MyAppFragment.
     *
     * @return current visible MyAppFragment. null if the stack is empty or fragment is not MyAppFragment
     */
    private MyAppFragment getCurrentFragment() {
        List<Fragment> fragmentStack = getSupportFragmentManager().getFragments();
        if (fragmentStack == null || fragmentStack.size() == 0)
        {
            return null;
        }

        for (Fragment current : fragmentStack)
        {
            if (current != null && current.isVisible() && (current instanceof MyAppFragment)) return (MyAppFragment) current;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_bar_menu_items, menu);

        /*
         * product count
         */
        MenuItem menuItem = menu.findItem(R.id.add_to_cart_count_action);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        setBadgeCount(this, icon, badgeCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_to_cart_count_action)
        {
            Bundle shoppingCartBundle = new Bundle();
            shoppingCartBundle.putString(FragmentConstants.TOOLBAR_HEADING,FragmentConstants.CART);
            navigateToSection(AppSection.CART, true,shoppingCartBundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);

        if (reuse instanceof BadgeDrawable)
        {
            badge = (BadgeDrawable) reuse;
        }
        else
        {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public void updateProductBadge(int count) {
        badgeCount = count;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed()
    {
        int fragments = fragmentManager.getBackStackEntryCount();
        if(fragments > 0)
        {
            fragmentManager.popBackStack();
            LogcatUtil.errorMessage(TAG, "CURRENT FRAGMENT ENTRY COUNT : "+fragments);
        }
        /*
         * If already set root fragment then show exit alert dialog
         */
        else
        {
            LogcatUtil.errorMessage(TAG, "SHOW EXIT ALERT DIALOG");
            presenter.exitAlert(TaskActivity.this);
        }
    }

    @Override
    public void onYesExit() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        LogcatUtil.debuggingMessage(TAG,"onDestroy() call");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /*
         * The app will be recreated and the current toolbar will be null. It will be recreated
         * when the onCreate method is called. Store the current state to rebuild the toolbar correctly
         */
        AppBarState currentAppBarState = AppBarState.USE_WITHOUT_BACK;
        MyAppFragment currentFragment = getCurrentFragment();
        if (currentFragment != null)
        {
            currentAppBarState = currentFragment.getIsRootSection() ? AppBarState.USE_WITHOUT_BACK : AppBarState.USE_WITH_BACK;
        }
        /*
         * store this value on the state
         */
        outState.putSerializable(INSTANCE_SELECTED_FRAGMENT, currentAppBarState);
        super.onSaveInstanceState(outState);
    }

    private enum AppBarState {
        USE_WITHOUT_BACK,
        USE_WITH_BACK,
    }
}
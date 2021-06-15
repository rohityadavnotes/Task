package com.task.ui.common;

import androidx.fragment.app.Fragment;

import com.task.ui.task.TaskActivity;

public abstract class MyAppFragment extends Fragment {

    protected AppSection appSection;

    /**
     * get a unique string identifier for this fragment. Can be used as a key to add
     * into the back stack
     *
     * @return unique tag
     */
    public String getFragmentTag() {
        return this.appSection.toString();
    }

    /**
     * obtain the current instance of the activity holding this fragment
     *
     * @return main activity instance
     */
    public TaskActivity getTaskActivity() {
        return (TaskActivity) getActivity();
    }

    /**
     * this will indicate the navigation that the stack will be cleared before inserting a new fragment transaction
     */
    protected boolean isRootSection = false;

    public boolean getIsRootSection() {
        return this.isRootSection;
    }
}
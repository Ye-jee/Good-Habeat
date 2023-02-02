package com.example.goodhabeat_view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<SelectedMenuItemData> selected = new MutableLiveData<SelectedMenuItemData>();

    public void select(SelectedMenuItemData item) {
        selected.setValue(item);
    }

    public LiveData<SelectedMenuItemData> getSelected() {
        return selected;
    }
}
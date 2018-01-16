package com.solstice.codechallenge.ui.contactList;

import java.util.List;

public interface FindContactsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}

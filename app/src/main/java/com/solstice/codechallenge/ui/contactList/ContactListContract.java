package com.solstice.codechallenge.ui.contactList;

import com.solstice.codechallenge.ui.BasePresenter;
import com.solstice.codechallenge.ui.contactList.recyclerView.ListItem;


import java.util.List;

/**
 * Created by fbucich on 1/14/18.
 */
public interface ContactListContract {
    interface View {
        void showContacts(List<ListItem> consolidatedList);

        void showNoDataMessage();

        void showErrorMessage(String error);

        void showContactDetail(String id);

        void stopLoadingIndicator();
    }

    interface Presenter extends BasePresenter<View> {
        void loadContacts(boolean forceRemote);
    }
}

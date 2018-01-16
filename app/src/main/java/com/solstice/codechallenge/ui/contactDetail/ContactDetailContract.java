package com.solstice.codechallenge.ui.contactDetail;

import com.solstice.codechallenge.data.model.Contact;
import com.solstice.codechallenge.ui.BasePresenter;


/**
 * Created by fbucich on 1/14/18.
 */
public interface ContactDetailContract {
    interface View {
        void showContactDetail(Contact contact);

        void updateFavoriteActionIcon(Contact contact);
    }

    interface Presenter extends BasePresenter<View> {
        void loadContact(String id);

        void toggleFavorite(String id);
    }
}

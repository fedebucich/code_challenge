package com.solstice.codechallenge.ui.contactDetail;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solstice.codechallenge.R;
import com.solstice.codechallenge.data.model.Contact;
import com.solstice.codechallenge.ui.BaseActivity;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fbucich on 1/14/18.
 */
public class ContactDetailActivity extends BaseActivity implements ContactDetailContract.View {

    @Inject
    ContactDetailPresenter presenter;
    @BindView(R.id.detail_company)
    TextView companyText;
    @BindView(R.id.profile_big)
    ImageView profileImage;
    @BindView(R.id.detail_name)
    TextView nameText;
    @BindView(R.id.details_container)
    LinearLayout rowsContainer;

    private String intentExtraId;
    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        this.intentExtraId = getIntent().getStringExtra(getString(R.string.contact_id_extra));
        initializePresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentContact != null) {
            if (currentContact.isFavorite()) {
                menu.findItem(R.id.action_remove_favorite).setVisible(true);
            } else {
                menu.findItem(R.id.action_favorite).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.toggleFavorite(this.currentContact.getId());
        return true;
    }

    private void initializePresenter() {
        DaggerContactDetailComponent.builder()
                .contactDetailPresenterModule(new ContactDetailPresenterModule(this))
                .contactRepositoryComponent(getContactRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadContact(this.intentExtraId);
    }

    @Override
    public void updateFavoriteActionIcon(Contact contact) {
        this.currentContact = contact;
        invalidateOptionsMenu();
    }

    @Override
    public void showContactDetail(Contact contact) {
        this.currentContact = contact;
        companyText.setText(contact.getCompanyName());
        nameText.setText(contact.getName());
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.profile_big);
        Glide.with(profileImage).setDefaultRequestOptions(requestOptions).load(contact.getLargeImageURL()).into(profileImage);
        addDetailRows(contact);

    }

    private void addDetailRows(Contact contact) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_detail_row_item, rowsContainer, false);
        relativeLayout.removeAllViews();
        this.addRowToContainer(contact.getPhone().getHome(), getString(R.string.extra_info_detail), getString(R.string.title_phone));
        this.addRowToContainer(contact.getPhone().getMobile(), getString(R.string.extra_info_mobile), getString(R.string.title_phone));
        this.addRowToContainer(contact.getPhone().getWork(), getString(R.string.extra_info_work), getString(R.string.title_phone));
        this.addRowToContainer(contact.getAddress().getCity(), getString(R.string.empty_extra_info), getString(R.string.title_address));
        this.addRowToContainer(contact.getPhone().getWork(), getString(R.string.empty_extra_info), getString(R.string.title_birthdate));
        this.addRowToContainer(contact.getPhone().getWork(), getString(R.string.empty_extra_info), getString(R.string.title_email));
    }


    private void addRowToContainer(String value, String detail, String title) {
        if (value != null && !value.isEmpty()) {
            View item = getNewRowView();

            TextView valueText = item.findViewById(R.id.value_text);
            TextView detailText = item.findViewById(R.id.more_info_text);
            TextView titleText = item.findViewById(R.id.title_text);

            valueText.setText(value);
            detailText.setText(detail);
            titleText.setText(title);

            this.rowsContainer.addView(item);
        }

    }

    private View getNewRowView() {
        Context context = getBaseContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.activity_detail_row_item, rowsContainer, false);
    }
}

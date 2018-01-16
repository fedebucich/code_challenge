package com.solstice.codechallenge.ui.contactList.recyclerView;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solstice.codechallenge.R;
import com.solstice.codechallenge.data.model.Contact;
import com.solstice.codechallenge.ui.BaseRecyclerViewAdapter;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fbucich on 1/14/18.
 */
public class ContactListAdapter extends BaseRecyclerViewAdapter<ContactListAdapter.ContactViewHolder> {
    private List<ListItem> contacts;

    public ContactListAdapter(List<ListItem> newContacts) {
        this.contacts = newContacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case ListItem.TYPE_CONTACT:
                View contactView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_contact, parent, false);
                return new ContactViewHolder(contactView);


            case ListItem.TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_header, parent, false);
                return new HeaderViewHolder(headerView);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_HEADER:
                populateHeaderView((HeaderViewHolder) viewHolder, i);
                break;

            case ListItem.TYPE_CONTACT:
                populateContactView((ContactViewHolder) viewHolder, i);
                break;
        }

    }

    private void populateContactView(ContactViewHolder viewHolder, int i) {
        ContactViewHolder vh = viewHolder;
        Contact contact = ((ContactListItem) contacts.get(i)).getContact();

        vh.nameText.setText(contact.getName());
        vh.companyText.setText(contact.getCompanyName());
        vh.favoriteImage.setVisibility(getFavoriteVisibility(contact.isFavorite()));
        setProfilePicture(vh, contact);
    }

    private void setProfilePicture(ContactViewHolder vh, Contact contact) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.small_profile);
        Glide.with(vh.profileImage).setDefaultRequestOptions(requestOptions)
                .load(contact.getSmallImageURL()).into(vh.profileImage);
    }

    private int getFavoriteVisibility(boolean isFavorite) {
        return isFavorite ? View.VISIBLE : View.INVISIBLE;
    }

    private void populateHeaderView(HeaderViewHolder viewHolder, int i) {
        ContactListHeader dateItem = (ContactListHeader) contacts.get(i);
        viewHolder.headerText.setText(dateItem.getHeader());
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void replaceData(List<ListItem> consolidatedList) {
        this.contacts.clear();
        this.contacts.addAll(consolidatedList);
        notifyDataSetChanged();
    }

    public Contact getItem(int position) {
        if (position < 0 || position >= contacts.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        ListItem l = contacts.get(position);
        if (l instanceof ContactListItem) {
            return ((ContactListItem) l).getContact();
        }

        return null;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name)
        TextView nameText;
        @BindView(R.id.text_company)
        TextView companyText;
        @BindView(R.id.image_profile)
        ImageView profileImage;
        @BindView(R.id.image_favorite)
        ImageView favoriteImage;

        private ContactViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_header)
        TextView headerText;

        private HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

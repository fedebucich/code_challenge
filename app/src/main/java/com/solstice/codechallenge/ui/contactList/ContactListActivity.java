package com.solstice.codechallenge.ui.contactList;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.solstice.codechallenge.R;

import com.solstice.codechallenge.ui.BaseActivity;
import com.solstice.codechallenge.ui.RecyclerViewListener;
import com.solstice.codechallenge.ui.contactDetail.ContactDetailActivity;

import com.solstice.codechallenge.ui.contactList.recyclerView.ContactListAdapter;
import com.solstice.codechallenge.ui.contactList.recyclerView.ListItem;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fbucich on 1/14/18.
 */
public class ContactListActivity extends BaseActivity implements ContactListContract.View {

    private ContactListAdapter adapter;
    @Inject
    ContactListPresenter presenter;
    @BindView(R.id.recycler_contact)
    RecyclerView contactRecyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);
        initializePresenter();
        setupWidgets();
    }

    private void initializePresenter() {
        DaggerContactListComponent.builder()
                .contactListPresenterModule(new ContactListPresenterModule(this))
                .contactRepositoryComponent(getContactRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadContacts(false);
    }

    private void setupWidgets() {
        setUpRecyclerView();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadContacts(true);
            }
        });

    }

    private void setUpRecyclerView() {

        adapter = new ContactListAdapter(new ArrayList<ListItem>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(contactRecyclerView.getContext(),
               DividerItemDecoration.VERTICAL );
        contactRecyclerView.addItemDecoration(dividerItemDecoration);
        contactRecyclerView.setLayoutManager(layoutManager);
        contactRecyclerView.setAdapter(adapter);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new RecyclerViewListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                showContactDetail(adapter.getItem(position).getId());
            }
        });
    }

    @Override
    public void showContacts(List<ListItem> groupedContacts) {
        progressBar.setVisibility(View.GONE);
        adapter.replaceData(groupedContacts);
    }

    @Override
    public void showNoDataMessage() {
        showNotification("No results found");
    }

    @Override
    public void showErrorMessage(String error) {
        showNotification(error);
    }

    @Override
    public void stopLoadingIndicator() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showContactDetail(String id) {
        Intent i = new Intent(this, ContactDetailActivity.class);
        i.putExtra(getString(R.string.contact_id_extra), id);
        startActivity(i);
    }

    private void showNotification(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}

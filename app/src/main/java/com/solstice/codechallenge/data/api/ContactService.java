package com.solstice.codechallenge.data.api;

import com.solstice.codechallenge.data.model.Contact;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by fbucich on 1/14/18.
 */
public interface ContactService {

    @GET("contacts.json")
    Flowable<List<Contact>> getContacts();
}

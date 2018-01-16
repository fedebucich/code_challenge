package com.solstice.codechallenge.repository;

import com.solstice.codechallenge.data.model.Contact;
import com.solstice.codechallenge.data.repository.ContactsDataSourceImpl;
import com.solstice.codechallenge.data.repository.ContactsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verifyZeroInteractions;

public class ContactsRepositoryTest {
    private static final Contact contact1 = new Contact();
    private static final Contact contact2 = new Contact();
    private static final Contact contact3 = new Contact();
    private static final List<Contact> contacts = Arrays.asList(contact1, contact2, contact3);

    @Mock
    private ContactsDataSourceImpl dataSource;

    private ContactsRepository repository;

    private TestSubscriber<List<Contact>> contactsTestSubscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        repository = new ContactsRepository(dataSource);

        contactsTestSubscriber = new TestSubscriber<>();
    }

    @Test
    public void loadContacts_ShouldReturnCache_IfItIsAvailable() {
        // Given
        repository.contactsCache.addAll(contacts);

        // When
        repository.loadContacts(false).subscribe(contactsTestSubscriber);

        // Then
        // No interaction with remote source
        verifyZeroInteractions(dataSource);

    }


    @Test
    public void getContact_ShouldReturnFromCache() {
        // Given
        contact1.setId("1");
        contact2.setId("2");
        contact3.setId("3");
        repository.contactsCache.addAll(contacts);

        // When
        repository.loadContact("1");

        // Then
        // No interaction with local storage or remote source
        verifyZeroInteractions(dataSource);
    }
}

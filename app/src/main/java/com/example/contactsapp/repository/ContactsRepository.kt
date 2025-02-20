package com.example.contactsapp.repository

import android.content.Context
import android.provider.ContactsContract
import com.example.contactsapp.data.Contact
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
//    private fun getContactList(): List<Contact> {
//        val contactList = mutableListOf<Contact>()
//
//        context.contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null, null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
//        )?.use {
//
//        }
//
//    }
}
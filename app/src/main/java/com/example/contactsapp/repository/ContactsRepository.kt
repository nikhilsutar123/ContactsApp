package com.example.contactsapp.repository

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactsapp.data.Contact
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getContacts():List<Contact> = coroutineScope {
        async(Dispatchers.IO){
            getContactList()
        }.await()
    }
    private fun getContactList(): List<Contact> {
        val contactList = mutableListOf<Contact>()

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )?.use { contactCursor ->
            val idIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
            val thumbnailUriIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)

            while (contactCursor.moveToNext()) {
                val id = contactCursor.getString(idIndex)
                val name = contactCursor.getString(nameIndex)
                val number = contactCursor.getString(numberIndex)
                val photoUri = contactCursor.getString(photoIndex)
                val thumbnailUri = contactCursor.getString(thumbnailUriIndex)

                contactList.add(
                    Contact(
                        id,
                        name,
                        number,
                        photoUri,
                        thumbnailUri
                    )
                )
            }
        }
        return contactList;
    }
}
package com.example.contactsapp.data

import java.util.Collections

data class ContactUiState(
    val loading: Boolean = true,
    val contacts: GroupedContacts = Collections.emptyMap()
)

typealias GroupedContacts = Map<String, List<Contact>>

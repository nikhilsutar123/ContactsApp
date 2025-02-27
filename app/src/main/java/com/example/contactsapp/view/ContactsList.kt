package com.example.contactsapp.view

import android.content.Intent
import android.net.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Uri
import coil3.compose.rememberAsyncImagePainter
import com.example.contactsapp.R
import com.example.contactsapp.data.Contact
import com.example.contactsapp.data.GroupedContacts
import java.net.URI
import java.util.Collections

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    contacts: GroupedContacts = Collections.emptyMap()
) {
    LazyColumn(modifier) {
        contacts.map { contactMap ->
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
                    Text(
                        text = contactMap.key,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            items(contactMap.value.size) { index ->
                ContactListItem(contact = contactMap.value[index])
            }
        }
    }
}

@Composable
fun ContactListItem(contact: Contact) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = contact.thumbnail, error = painterResource(
                    id = R.drawable.profile_user
                )
            ), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.weight(1f, true)) {
            Text(
                text = contact.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            )
            Text(
                text = contact.phoneNumber,
                style = TextStyle(
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            )
        }

        IconButton(onClick = {
            val intent =
                Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:${contact.phoneNumber}"))
            context.startActivity(intent)
        }) {
            Image(
                imageVector = Icons.Filled.Call,
                contentDescription = "",
                modifier = Modifier.padding(8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        IconButton(onClick = {
            val intent =
                Intent(Intent.ACTION_VIEW, android.net.Uri.parse("sms:" + contact.phoneNumber))
            context.startActivity(intent)
        }) {
            Image(
                painterResource(id = R.drawable.chatting),
                contentDescription = "",
                modifier = Modifier.padding(8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
}
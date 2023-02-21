package com.appdevcourse.bengaliappv2.view.screens.reviewScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.screens.inputitems.textInputField
import com.appdevcourse.bengaliappv2.ui.theme.BengaliAppV2Theme
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground

data class Message(val author: String, val body: String)


@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.pic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}


@Composable
fun MessageBox() {

    var textState by remember { mutableStateOf("") }

    Box(
        Modifier
            .background(WhiteBackground)
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
            //.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            textInputField(
                value =  textState,
                maxLine = 10,
                onValueChange = {
                    textState = it
                },
                displayText = "Type here",
                iconVector = Icons.Default.Send,
                imeAction = ImeAction.None,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(start = 5.dp, end=10.dp, bottom = 5.dp)
            )

//            Spacer(modifier = Modifier.size(12.dp))
//
            FloatingActionButton(
                modifier = Modifier.
                padding(top = 5.dp, end = 5.dp),
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}


@Composable
fun Conversation(messages: List<Message>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .background(WhiteBackground)
                .fillMaxWidth()
                .fillMaxHeight(.85f)
                .padding(top = 8.dp, bottom = 2.dp)
        ) {
            messages.map { item { MessageCard(it) } }
            //item {MessageBox()}
        }
        MessageBox()
    }

}


@Composable
fun ReviewScreen(){
    Conversation(messages = SampleData.conversationSample)
}

@Preview
@Composable
fun PreviewConversation() {
    BengaliAppV2Theme() {
//        Conversation(SampleData.conversationSample)
        ReviewScreen()
    }
}

package com.appdevcourse.bengaliappv2.view.screens

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.common.ResultState
import com.appdevcourse.bengaliappv2.data.model.UserModel
import com.appdevcourse.bengaliappv2.screens.inputitems.introTextToShow
import com.appdevcourse.bengaliappv2.screens.inputitems.passwordInputField
import com.appdevcourse.bengaliappv2.screens.inputitems.submissionButton
import com.appdevcourse.bengaliappv2.screens.inputitems.textInputField
import com.appdevcourse.bengaliappv2.view.screens.utils.RatingBarItem
import com.appdevcourse.bengaliappv2.view.screens.utils.RoundedImageHeader
import com.appdevcourse.bengaliappv2.ui.theme.BackgroundColor
import com.appdevcourse.bengaliappv2.ui.theme.InputBoxShape
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground
import com.appdevcourse.bengaliappv2.view.screens.utils.showMsg
import com.appdevcourse.bengaliappv2.viewmodel.LocalDataStoreViewModel
import com.appdevcourse.bengaliappv2.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current
    val localDatastoreViewModel = LocalDataStoreViewModel (mContext)
    var res = viewModel.res.value
    val scope = rememberCoroutineScope()
    val isDialog = remember { mutableStateOf(false) }
    var hasAccount = false;
    var userDataToLocal:List<String> = ArrayList<String>(6)
    val userName = remember  { mutableStateOf("") }
    var userFullName by remember { mutableStateOf("") }
    var userNumber by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

//    if (res.item.isNotEmpty()){
//       res->EachRow(
//        itemState = tes.item!!
//       )
//    }

    var rating by remember {
        mutableStateOf(0)
    }

    val mCalendar = Calendar.getInstance()
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    var userDOB by remember { mutableStateOf("") }

    val userDOBPickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYears: Int, mMonths: Int, mDayOfMonth: Int ->
            userDOB = "$mDayOfMonth/${mMonths + 1}/$mYears"
        }, mYear, mMonth, mDay
    )

    if(res.item.isNotEmpty()){

        hasAccount = true;
        userName.value = res.item[0].item?.userName!!
        userFullName = res.item[0].item?.fullName!!
        userEmail = res.item[0].item?.email!!
        userNumber = res.item[0].item?.phone!!
        userDOB = res.item[0].item?.dateOfBirth!!
        userPassword = res.item[0].item?.password!!
        viewModel.setData(res.item[0])

        userDataToLocal = arrayListOf(
            res.item[0].item?.userName!!,
            res.item[0].item?.fullName!!,
            res.item[0].item?.email!!,
            res.item[0].item?.phone!!,
            res.item[0].item?.dateOfBirth!!,
            res.item[0].item?.imageURL!!,
        );

        try{
            //Log.d("localdata",userDataToLocal.toString())
            localDatastoreViewModel.saveToLocal(userDataToLocal)
        }catch (e:Exception){
            Log.e("Exception In storing", e.toString())
        }
//        val saveData:List<String> = arrayListOf(
//            res.item[0].item?.userName!!
//        )
//        localDatastoreViewModel.saveToLocal(saveData)
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(WhiteBackground)
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 55.dp)
    ) {

        item {
            RoundedImageHeader()
        }

        item { introTextToShow(stringResource(id = R.string.update_your_profile)) }

        item {
            RatingBarItem( modifier = Modifier,rating=0) { rating = it }
        }
        item {
            textInputField(
                value = userName.value,
                onValueChange = {
                    userName.value = it
                },
                displayText = stringResource(id = R.string.user_name),
                icon = R.drawable.ic_user,
            )
        }
        item {
        textInputField(
            value = userFullName,
            onValueChange = {
                userFullName = it
                            },
            displayText = stringResource(id = R.string.user_full_name),
            icon = R.drawable.ic_user,
        )
        }
        item {
        textInputField(
            value = userEmail,
            onValueChange = {
                userEmail = it
                            },
            displayText = stringResource(id = R.string.email_address),
            icon = R.drawable.ic_email_outline,
            keyboardType = KeyboardType.Email
        )
        }

        item {
        textInputField(
            value = userNumber,
            onValueChange = { userNumber = it },
            displayText = stringResource(id = R.string.mobile_number),
            iconVector = Icons.Outlined.Phone,
            keyboardType = KeyboardType.Phone
        )
        }


        item {
            val dateInteractionSource = remember {
                MutableInteractionSource()
            }

            if (dateInteractionSource.collectIsPressedAsState().value) {
                userDOBPickerDialog.show()
            }

            OutlinedTextField(
                value = userDOB,
                onValueChange = {
                                userDOB = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 2.dp)
                    .clickable { userDOBPickerDialog.show() },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                    cursorColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                interactionSource = dateInteractionSource,
                readOnly = false,
                shape = InputBoxShape.medium,
                singleLine = true,
                leadingIcon = {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.EditCalendar,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(6.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(24.dp)
                                .background(BackgroundColor)
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.DOB),
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
//                fontFamily = Poppins
                )
            )

        }

        item {
            passwordInputField(
                value = userPassword,
                onValueChange = { userPassword = it },
                displayText = stringResource(id = R.string.password),
            )
        }

        item {
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp)
            )
        }

        item {
        submissionButton(
            displayText = stringResource(id = R.string.update_profile),
            onClick = {
//                Log.e("Name: ",userName.value)
//                Log.d("UserName: ",userFullName)
//                Log.e("Email: ",userEmail)
//                Log.e("Number: ",userNumber)
//                Log.e("DOB: ",userDOB)
//                Log.e("Pass: ",userPassword)
//                Log.e("Rating: ",rating.toString())

                scope.launch(Dispatchers.Main) {
                    if(!hasAccount) {
                        viewModel.insert(
                            UserModel.UserItems(
                                userName.value,
                                userFullName,
                                userEmail,
                                userNumber,
                                userDOB,
                                userPassword,
                                "hello123"
                            )
                        ).collect {
                            when (it) {
                                is ResultState.Success -> {
                                    mContext.showMsg(
                                        msg = it.data
                                    )
                                    hasAccount = true;
                                }
                                is ResultState.Failure -> {
                                    mContext.showMsg(
                                        msg = it.msg.toString()
                                    )
                                    //isDialog.value = false
                                }
                                ResultState.Loading -> {
                                    isDialog.value = true
                                }
                            }
                        }
                    }else {
                        viewModel.update(
                            UserModel(
                                item = UserModel.UserItems(
                                    userName.value,
                                    userFullName,
                                    userEmail,
                                    userNumber,
                                    userDOB,
                                    userPassword,
                                    "hello123"
                                ),
                                key = viewModel.updateRes.value.key
                            )
                        ).collect {
                            when (it) {
                                is ResultState.Success -> {
                                    mContext.showMsg(
                                        msg = it.data
                                    )
                                }
                                is ResultState.Failure -> {
                                    mContext.showMsg(
                                        msg = it.msg.toString()
                                    )
                                    //isDialog.value = false
                                }
                                ResultState.Loading -> {
                                    isDialog.value = true
                                }
                            }
                        }
                    }

                }

            })
        }
        item {
        Spacer(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
        )
        }

    }

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(res.error)
        }
    }

}



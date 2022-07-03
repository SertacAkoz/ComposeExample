package com.sertac.smartclock.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.sertac.smartclock.R
import com.sertac.smartclock.SmartClockScreen
import com.sertac.smartclock.model.RecordListItem
import com.sertac.smartclock.ui.theme.SmartClockTheme
import com.sertac.smartclock.viewmodel.ListViewModel

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {

        Text(
            text = "List",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.fillMaxWidth().padding(2.dp)
        )


        Column {
//            Row(
//                modifier = Modifier
//                    .wrapContentSize(Alignment.Center),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painterResource(id = R.drawable.medical),
//                    contentDescription = "Step icon",
//                    modifier = Modifier
//                        .size(50.dp),
//                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
//                )
//
//                Text(
//                    text = "Step Counts",
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colors.onSurface,
//                    textAlign = TextAlign.Center,
//                    fontSize = 25.sp
//                )
//            }

            RecordList()
        }
    }
}

@Composable
fun RecordList(
    viewModel: ListViewModel = hiltViewModel()
) {
    val recordList by remember { viewModel.recordList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    RecordListView(recordList = recordList)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }

        if (errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.getRecordList()
            }
        }
    }
}

@Composable
fun RecordListView(recordList:List<RecordListItem>) {
    LazyColumn(contentPadding = PaddingValues(5.dp),verticalArrangement = Arrangement.spacedBy(10.dp)){
        items(recordList){ record ->
            RecordRow(recordListItem = record)
        }
    }
}

@Composable
fun RecordRow(recordListItem:RecordListItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary, shape = CircleShape)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.medical),
                contentDescription = "Step icon",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
           Column {
               Text(text = recordListItem.recordName,
                   style = MaterialTheme.typography.h4,
                   modifier = Modifier.padding(2.dp),
                   fontWeight = FontWeight.Bold,
                   color = MaterialTheme.colors.onPrimary
               )

               Text(text = recordListItem.recordValue,
                   style = MaterialTheme.typography.h5,
                   modifier = Modifier.padding(2.dp),
                   color = MaterialTheme.colors.onPrimary
               )
           }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    SmartClockTheme {
        RecordRow(recordListItem = RecordListItem(0,"Name", "1200","1200"))
    }
}
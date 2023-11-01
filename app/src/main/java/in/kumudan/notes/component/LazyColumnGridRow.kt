package `in`.kumudan.notes.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.kumudan.notes.Util.fromDate
import `in`.kumudan.notes.Util.isToday
import `in`.kumudan.notes.Util.onlyTimeFromDate
import `in`.kumudan.notes.data.NoteDataSource
import `in`.kumudan.notes.model.Note
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun notesGrid(note: Note, onItemClick: (String) -> Unit = {}, onLongPress: (Note) -> Unit = {}) {
    OutlinedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .combinedClickable(onClick = {
                onItemClick(note.id.toString())
            },
                onLongClick = {
                    onLongPress(note)
                }),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
            )
            Divider()
            Text(
                text = note.notes,
                textAlign = TextAlign.Justify,
                maxLines = 8,
                style = MaterialTheme.typography.bodySmall
            )
            Divider()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text = ""
                if (isToday(note.entryDate.time)) {
                    text = onlyTimeFromDate(note.entryDate.time)
                } else {
                    text = fromDate(note.entryDate.time)
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(
                    modifier = Modifier
                        .padding(1.dp)
                        .weight(1f),
                )
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More")
            }

        }
    }
}

@Preview
@Composable
fun noteGridPreview() {
    notesGrid(NoteDataSource().LoadNotes()[0])
}
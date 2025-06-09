package com.jin.honey.feature.orderdetail.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.FoodSearchBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxDividerColor
import com.jin.honey.ui.theme.OrderDetailDeleteIconColor
import com.jin.honey.ui.theme.OrderDetailMenuClearTextColor
import com.jin.honey.ui.theme.OrderDetailRequirementCheckedColor
import com.jin.honey.ui.theme.OrderDetailRequirementHintColor


@Composable
fun OrderDetailRequirements(
    content: String,
    riderContent: String,
    checked: Boolean,
    modifier: Modifier,
    onContentChanged: (newContent: String) -> Unit,
    onCheckedChanged: (newChecked: Boolean) -> Unit,
    onShowRiderRequirement: (showBottomSheet: Boolean) -> Unit,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            Text(
                text = stringResource(R.string.order_detail_requirements),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 14.dp, bottom = 10.dp)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 10.dp),
            ) {
                BasicTextField(
                    value = content,
                    onValueChange = { onContentChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
//                            .focusRequester(focusRequester)
                        .onFocusChanged { },
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) {
                            Text(
                                text = stringResource(R.string.order_detail_requirements_hint),
                                color = OrderDetailRequirementHintColor,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCheckedChanged(!checked) }) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { onCheckedChanged(it) },
                    colors = CheckboxDefaults.colors(checkedColor = OrderDetailRequirementCheckedColor)
                )
                Text(
                    text = stringResource(R.string.order_detail_requirements_recycle),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_environment),
                    contentDescription = stringResource(R.string.order_detail_requirements_recycle_icon_desc),
                    modifier = Modifier.size(12.dp),
                    tint = Color.Unspecified
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.order_detail_rider_requirements),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onShowRiderRequirement(true) }
                ) {
                    Text(
                        text = if (riderContent.isEmpty()) stringResource(R.string.order_detail_rider_requirements_nothing) else "목록보기",
                        fontSize = 14.sp
                    )
                    Icon(
                        Icons.Default.ArrowForwardIos,
                        contentDescription = stringResource(R.string.order_detail_change_rider_requirements_icon_desc),
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(12.dp)
                    )
                }
            }
            if (riderContent.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 14.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                ) {
                    Text(riderContent, fontSize = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiderRequirementBottomSheet(
    riderRequirementContent: String,
    onShowBottomSheet: (state: Boolean) -> Unit,
    onSelectedRiderRequire: (String) -> Unit
) {
    val requires = listOf(
        stringResource(R.string.order_detail_rider_option_bell_ok),
        stringResource(R.string.order_detail_rider_option_knock_ok),
        stringResource(R.string.order_detail_rider_option_both_no),
        stringResource(R.string.order_detail_rider_option_nothing),
        stringResource(R.string.order_detail_rider_option_receive_in_person),
        stringResource(R.string.order_detail_rider_option_call),
        stringResource(R.string.order_detail_rider_enter_directly),
    )
    ModalBottomSheet(
        onDismissRequest = { onShowBottomSheet(false) },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        BottomSheetContent(requires, riderRequirementContent, onSelectedRiderRequire, onShowBottomSheet)
    }
}

@Composable
private fun BottomSheetContent(
    requires: List<String>,
    riderRequirementContent: String,
    onSelectedRiderRequire: (String) -> Unit,
    onShowBottomSheet: (state: Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
                .size(width = 40.dp, height = 4.dp)
                .background(Color.LightGray, RoundedCornerShape(2.dp))
        )

        Text(
            text = stringResource(R.string.order_detail_rider_requirements),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 20.dp),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(requires.size) {
                val requireLabel = requires[it]
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectedRiderRequire(requireLabel)
                        onShowBottomSheet(false)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (requireLabel == riderRequirementContent) {
                        Text(requireLabel, color = Color.Black, fontWeight = FontWeight.Bold)
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = stringResource(R.string.order_detail_rider_select_require_icon_desc),
                            modifier = Modifier.size(28.dp)
                        )
                    } else {
                        Text(requireLabel, color = OrderDetailMenuClearTextColor)
                    }
                }
            }
        }
        HorizontalDivider(color = OrderDetailBoxDividerColor, modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = stringResource(R.string.order_detail_rider_description),
            fontSize = 12.sp,
            color = OrderDetailDeleteIconColor
        )
    }
}

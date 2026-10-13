package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab
import com.cybersec.blackwallpass.ui.theme.LocalAppColors

@Composable
internal fun AuthTabs(
	selectedTab: AuthTab,
	modifier: Modifier = Modifier,
	onTabChange: (AuthTab) -> Unit
) {
	val colors = LocalAppColors.current

	Row(
		modifier = modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(12.dp))
			.background(colors.gray900)
			.border(
				width = 1.dp,
				color = colors.gray700,
				shape = RoundedCornerShape(12.dp)
			)
			.padding(4.dp)
	) {
		AuthTabButton(
			text = stringResource(R.string.auth_tab_login),
			isActive = selectedTab == AuthTab.LOGIN,
			modifier = Modifier.weight(1f),
			onClick = { onTabChange(AuthTab.LOGIN) }
		)
		AuthTabButton(
			text = stringResource(R.string.auth_tab_signup),
			isActive = selectedTab == AuthTab.SIGNUP,
			modifier = Modifier.weight(1f),
			onClick = { onTabChange(AuthTab.SIGNUP) }
		)
	}
}
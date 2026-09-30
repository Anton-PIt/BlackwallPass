package com.cybersec.blackwallpass.ui.common

//Режимы отображения прогресса на экране
sealed interface ProgressMode {
	//Прогресс не показывается
	object None : ProgressMode

	//Прогресс встроен в кнопку
	object Filled : ProgressMode

	//Прогресс поверх всего экрана
	object Overlay : ProgressMode
}
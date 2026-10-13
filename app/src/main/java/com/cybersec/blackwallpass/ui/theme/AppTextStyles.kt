package com.cybersec.blackwallpass.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTextStyles(
	//Логотип BLACKWALL//PASS
	val appLogoTitle: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp,
		lineHeight = 28.sp,
		letterSpacing = 4.sp
	),
	//Подпись под лого
	val logoCaption: TextStyle = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
		letterSpacing = 1.1.sp
	),
	//Вкладки ВОЙТИ / РЕГИСТРАЦИЯ
	val tabLabel: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.SemiBold,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 1.44.sp
	),
	//Подписи полей: EMAIL АДРЕС, МАСТЕР-ПАРОЛЬ
	val fieldLabel: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp,
		letterSpacing = 1.2.sp
	),
	//Текст в поле email
	val fieldValueDefault: TextStyle = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	),
	//Текст в поле пароля
	val fieldValueMono: TextStyle = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	),
	// Текст ошибки под полем
	val errorText: TextStyle = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 14.sp,
	),
	//Заголовок секции БИОМЕТРИЯ
	val biometricSectionTitle: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
		letterSpacing = 1.1.sp
	),
	//Описание под БИОМЕТРИЯ
	val biometricDescription: TextStyle = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp
	),
	//Подписи кнопок биометрии
	val biometricOptionLabel: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp,
		letterSpacing = 0.8.sp
	),
	//Ссылка ЗАБЫЛИ КЛЮЧ?
	val forgotKeyLink: TextStyle = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
	),
	//Главная кнопка
	val primaryButton: TextStyle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 2.1.sp
	),
	//Текст внизу: СКВОЗНОЕ ШИФРОВАНИЕ
	val encryptionInfo: TextStyle = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp
	),
	// Текст снекбара
	val snackbarText: TextStyle = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
		lineHeight = 20.sp
	)
)
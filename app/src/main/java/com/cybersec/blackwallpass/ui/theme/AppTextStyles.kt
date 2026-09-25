package com.cybersec.blackwallpass.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {
	//Логотип BLACKWALL//PASS
	val appLogoTitle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp,
		lineHeight = 28.sp,
		letterSpacing = 4.sp
	)

	//Подпись под лого
	val logoCaption = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
		letterSpacing = 1.1.sp
	)

	//Вкладки ВОЙТИ / РЕГИСТРАЦИЯ
	val tabLabel = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.SemiBold,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 1.44.sp
	)

	//Подписи полей: EMAIL АДРЕС, МАСТЕР-ПАРОЛЬ
	val fieldLabel = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp,
		letterSpacing = 1.2.sp
	)

	//Текст в поле email
	val fieldValueDefault = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	)

	//Текст в поле пароля
	val fieldValueMono = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	)

	//Заголовок секции БИОМЕТРИЯ
	val biometricSectionTitle = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
		letterSpacing = 1.1.sp
	)

	//Описание под БИОМЕТРИЯ
	val biometricDescription = TextStyle(
		fontFamily = InterFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp
	)

	//Подписи кнопок биометрии
	val biometricOptionLabel = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp,
		letterSpacing = 0.8.sp
	)

	//Ссылка ЗАБЫЛИ КЛЮЧ?
	val forgotKeyLink = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 11.sp,
		lineHeight = 16.5.sp,
	)

	//Главная кнопка
	val primaryButton = TextStyle(
		fontFamily = OrbitronFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 2.1.sp
	)

	//Текст внизу: СКВОЗНОЕ ШИФРОВАНИЕ
	val encryptionInfo = TextStyle(
		fontFamily = JetBrainsMonoFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 10.sp,
		lineHeight = 15.sp
	)
}
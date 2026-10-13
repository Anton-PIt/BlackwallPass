package com.cybersec.blackwallpass.ui.common

import androidx.annotation.CallSuper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cybersec.blackwallpass.ui.common.event.OneTimeEventWrapper
import com.cybersec.blackwallpass.ui.common.event.UiEvent
import com.cybersec.blackwallpass.ui.common.progress.DefaultProgressController
import com.cybersec.blackwallpass.ui.common.progress.ProgressMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Базовая ViewModel для всех экранов приложения
open class AppViewModel<T> : ViewModel() {
	//Прогресс
	private val progressController by lazy { DefaultProgressController(viewModelScope) }
	val progressState: State<ProgressMode> get() = progressController.progressState

	//Одноразовые ивенты
	private val _events = MutableStateFlow(OneTimeEventWrapper<T>(null))
	val events: StateFlow<OneTimeEventWrapper<T>> get() = _events

	//Снекбар
	private val _snackbarState = mutableStateOf(OneTimeEventWrapper<UiEvent.SnackbarEvent>(null))
	val snackbarState: State<OneTimeEventWrapper<UiEvent.SnackbarEvent>> get() = _snackbarState

	private var isFirstStart: Boolean = true

	@CallSuper
	open fun onStart() {
		if (isFirstStart) {
			isFirstStart = false
			onFirstStart()
		}
	}

	protected open fun onFirstStart() {}

	//Отправка события навигации
	protected fun sendUiEvent(event: T) = viewModelScope.launch {
		_events.emit(OneTimeEventWrapper(event))
	}

	//Показать снекбар с сообщением
	protected fun showMessage(messageId: Int? = null, messageString: String? = null) = viewModelScope.launch {
		_snackbarState.value = OneTimeEventWrapper(UiEvent.SnackbarEvent(messageId = messageId, messageString = messageString))
	}

	protected fun <T : Job> T.withProgress(
		mode: ProgressMode = ProgressMode.Filled,
		delayMs: Long = 0L
	): T = progressController.run { withProgress(mode, delayMs) }

	protected fun <T> Flow<T>.withProgress(
		mode: ProgressMode = ProgressMode.Filled,
		delayMs: Long = 0L
	): Flow<T> = progressController.run { withProgress(mode, delayMs) }
}
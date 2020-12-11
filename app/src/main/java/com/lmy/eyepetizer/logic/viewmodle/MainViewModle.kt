package com.lmy.eyepetizer.logic.viewmodle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lmy.eyepetizer.logic.bean.MessageBean
import com.lmy.eyepetizer.logic.repository.MessageRepository
import com.lmy.helloweather.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * @author
 * @功能:
 * @Creat 12/10/20 5:57 PM
 * @Compony 465008238@qq.com
 */
class MainViewModle : BaseViewModel() {
    private val _uiState = MutableLiveData<MainModel>()
    val uiState: LiveData<MainModel>
        get() = _uiState

    val name="ssssss"
    fun getMessageList() {
        viewModelScope.launch {
            var parms = mutableMapOf<String, String>()
            parms["udid"] = "4136b48a6ed35731"
            parms["vc"] = "6030012"
            parms["vn"] = "6.3.01"
            parms["size"] = "1080X2232"
            parms["deviceModel"] = "16s Pro"
            parms["first_channel"] = "meizu"
            parms["last_channel"] = "meizu"
            parms["system_version_code"] = "29"
            checkResult(MessageRepository.getMessageList(parms), {
                emitUiState(messageList = it)
            }) {
            }
        }
    }

    private fun emitUiState(
        messageList: List<MessageBean>?

    ) {
        val uiModel = MainModel(messageList)
        _uiState.postValue(uiModel)
    }
}

data class MainModel(
    val messageList: List<MessageBean>?
)



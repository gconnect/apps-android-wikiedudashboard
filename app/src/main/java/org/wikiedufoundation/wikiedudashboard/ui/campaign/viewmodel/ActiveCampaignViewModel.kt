package org.wikiedufoundation.wikiedudashboard.ui.campaign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.wikiedufoundation.wikiedudashboard.ui.campaign.data.CampaignListData
import org.wikiedufoundation.wikiedudashboard.ui.campaign.repository.ActiveCampaignRepository

/**
 * Class extends AndroidViewModel and requires application as a parameter.
 */
// Since now the view model make all it's function on the first call, and all the calls are wrapped
// inside the init block, your view model constructor arguments are now immutable as well. You don't
// need to expose then.
// Now the view model class work by itself. Some declarative programming. It's better then the imperative ;)
class ActiveCampaignViewModel(
        activeCampaignRepository: ActiveCampaignRepository,
        cookies: String
) : ViewModel() {

    private val _showMsg: MutableLiveData<Throwable?> = MutableLiveData()
    val showMsg: MutableLiveData<Throwable?> get() = _showMsg

    private val _data: MutableLiveData<List<CampaignListData>> = MutableLiveData()

    // Please, avoid optional when it's possible. It's one more test rule that you'll to implement
    // true / false / null
    private val _progressbar = MutableLiveData<Boolean?>()
    val progressbar: LiveData<Boolean?> get() = _progressbar

    init {
        _showMsg.value = null
        _progressbar.value = true

        // Since it's just one request, and it happen's when the user first come into this page
        // it's not a problem this INIT lambda
        viewModelScope.launch { _data.postValue(activeCampaignRepository.allCampaignList()) }

        // You can use try / catch here, but for the example, this one will be ok
        viewModelScope.launch {
            // Since you're using the MutableLiveData, instead of working in the main thread to add
            // values to it (_progressBar.value = ...), if you use the POST method, you'll do it
            // in another thread ( _progressbar.postValue(true) ).
            _progressbar.postValue(true)
            activeCampaignRepository.getCampaignList(cookies)
            _progressbar.value = false
        }

    }

    /**  The implementation of insert() is completely hidden from the UI.
     *  We don't want insert to block the main thread, so we're launching a new
     *  coroutine. ViewModels have a coroutine scope based on their lifecycle called
     *  viewModelScope which we can use here.
     **/


    /*fun fetchCampaignList(cookies: String) {
        viewModelScope.launch {
            try {
                _progressbar.value = true
                activeCampaignRepository.getCampaignList(cookies)
                _progressbar.value = false

            } catch (e: RuntimeException) {
                Timber.e(e.message.toString())
            }
        }

    }*/

    // Very important: all the getters from mutable / mediator should return a LiveData.
    // That's because your getter should not be able to modify / immutable
    val data: LiveData<List<CampaignListData>>
        get() = _data

}
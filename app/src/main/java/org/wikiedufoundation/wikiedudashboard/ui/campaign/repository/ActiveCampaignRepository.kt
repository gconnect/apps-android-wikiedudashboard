package org.wikiedufoundation.wikiedudashboard.ui.campaign.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.wikiedufoundation.wikiedudashboard.data.network.WikiEduDashboardApi
import org.wikiedufoundation.wikiedudashboard.ui.campaign.dao.ActiveCampaignDao
import org.wikiedufoundation.wikiedudashboard.ui.campaign.data.CampaignListData

/**Declares the DAO as a private property in the constructor. Pass in the DAO
 *instead of the whole database, because you only need access to the DAO*
 * */
class ActiveCampaignRepository(private val wikiEduDashboardApi: WikiEduDashboardApi,
                               private val activeCampaignDao: ActiveCampaignDao) {

    // In terms of android, use global variable only when it's needed. The class will look more concise
    // and your code will be clearer. This class has just a few lines, but can always be smaller.
    // When you use global variable, you give the idea for the newcomers that this variable is fullfiled
    // in one part of the code, and used in another. That was not the case.

    /** Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     * */
    suspend fun allCampaignList() = withContext(Dispatchers.IO) { activeCampaignDao.getAllCampaign() }


    /** The suspend modifier tells the compiler that this must be called from a
     *  coroutine or another suspend function.
     **/
    suspend fun getCampaignList(cookies: String) = withContext(Dispatchers.IO) {
        //If you use coroutine deferred, you are saying that the call is async, although you here you're
        //making it sync. So, to keep simple, and keep a good code readability just make your repo call suspend
        val request = wikiEduDashboardApi.getExploreCampaigns(cookies)
        val campaignList: List<CampaignListData> = request.campaigns
        activeCampaignDao.insertCampaign(campaignList)
    }

}
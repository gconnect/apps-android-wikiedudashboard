package org.wikiedufoundation.wikiedudashboard.ui.campaign.repository

import org.wikiedufoundation.wikiedudashboard.ui.campaign.data.CampaignListData

/**Declares the DAO as a private property in the constructor. Pass in the DAO
 *instead of the whole database, because you only need access to the DAO*
 * */
interface ActiveCampaignRepository {

    // Allow me explain why use this: whenever you change layer it's a good approach to make use of
    // this interface pattern. This way, you can control what the lawyer below can access.
    // Just like I did in the LiveData case at the view model.
    // This approach is often called CONTRACT between layers

    /** Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     * */
    // I change this implementation just to show that's possible to do it with coroutine as well
    // Although, to be OBSERVABLE with coroutine it's necessary make use of FLOW / CHANNELS
    // Just rollback this to LiveData.
    // I'll have to study a little bit more about the CHANNELS / FLOW to be able to choose the
    // best approach for this particular case / every case
    suspend fun allCampaignList(): List<CampaignListData>


    /** The suspend modifier tells the compiler that this must be called from a
     *  coroutine or another suspend function.
     **/
    suspend fun getCampaignList(cookies: String)

}
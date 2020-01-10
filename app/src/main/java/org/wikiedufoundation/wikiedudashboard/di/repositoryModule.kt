package org.wikiedufoundation.wikiedudashboard.di

import org.koin.dsl.module
import org.wikiedufoundation.wikiedudashboard.data.network.WikiEduDashboardApi
import org.wikiedufoundation.wikiedudashboard.ui.campaign.dao.CourseListDao
import org.wikiedufoundation.wikiedudashboard.ui.campaign.repository.ActiveCampaignRepository
import org.wikiedufoundation.wikiedudashboard.ui.campaign.repository.ActiveCampaignRepositoryImpl
import org.wikiedufoundation.wikiedudashboard.ui.campaign.repository.CourseListRepository

val repositoryModule = module {

    single<ActiveCampaignRepository> { ActiveCampaignRepositoryImpl(get(), get()) }
    single { provideCourseListRepository(get(), get()) }

}

fun provideCourseListRepository(api: WikiEduDashboardApi, courseListDao: CourseListDao)
        : CourseListRepository = CourseListRepository(api, courseListDao)

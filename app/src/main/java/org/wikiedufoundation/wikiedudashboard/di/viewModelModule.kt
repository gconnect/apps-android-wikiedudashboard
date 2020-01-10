package org.wikiedufoundation.wikiedudashboard.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.wikiedufoundation.wikiedudashboard.ui.campaign.viewmodel.ActiveCampaignViewModel
import org.wikiedufoundation.wikiedudashboard.ui.campaign.viewmodel.CourseListViewModel

val viewModelModule = module {
    viewModel { (cookies: String) -> ActiveCampaignViewModel(get(), cookies) }
    viewModel { CourseListViewModel(get()) }
}
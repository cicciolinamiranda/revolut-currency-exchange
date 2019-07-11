package com.adamstyrc.currencyrateconverter.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adamstyrc.currencyrateconverter.viewmodel.CurrencyRateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RevolutViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyRateViewModel::class)
    abstract fun bindCurrencyRateViewModel(viewModel: CurrencyRateViewModel): ViewModel
}

package com.jmzd.ghazal.storeappmvvm.utils.di

import androidx.fragment.app.Fragment
import com.jmzd.ghazal.storeappmvvm.data.models.SimpleResponse
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySetAddressForShipping
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySubmitAddress
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import com.jmzd.ghazal.storeappmvvm.data.models.comments.BodySendComment
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.BodyCoupon
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import com.jmzd.ghazal.storeappmvvm.ui.detail.adapters.PagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun provideFragment(fragment: Fragment) = PagerAdapter(fragment.parentFragmentManager, fragment.lifecycle)

    @Provides
    fun bodyLogin() = BodyLogin()

    @Provides
    fun bodyUpdateProfile() = BodyUpdateProfile()

    @Provides
    fun bodyIncreaseWallet() = BodyIncreaseWallet()

    @Provides
    fun bodySubmitAddress() = BodySubmitAddress()

    @Provides
    fun bodyAddToCart() = BodyAddToCart()

    @Provides
    fun bodySendComment() = BodySendComment()

    @Provides
    fun bodySetAddressForShipping() = BodySetAddressForShipping()

    @Provides
    fun bodyCoupon() = BodyCoupon()



}
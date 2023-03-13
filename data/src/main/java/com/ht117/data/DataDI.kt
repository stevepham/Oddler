package com.ht117.data

import com.ht117.data.repo.IProductRepo
import com.ht117.data.repo.ProductRepoImpl
import com.ht117.data.source.remote.ProductRemote
import com.ht117.data.source.remote.Remote
import org.koin.dsl.module

val dataModule = module {
    single { Remote.getClient() }
    single { ProductRemote(get()) }

    single<IProductRepo> { ProductRepoImpl(get()) }
}

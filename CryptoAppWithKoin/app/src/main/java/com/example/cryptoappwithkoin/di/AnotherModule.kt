package com.example.cryptoappwithkoin.di

import com.example.cryptoappwithkoin.view.ListFragment
import org.koin.dsl.module

val anotherModule = module {

    scope<ListFragment> {
        scoped {
            "Hello Kotlin"
        }
    }
}
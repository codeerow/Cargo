package com.spirit.cargo.presentation.core

import io.reactivex.rxjava3.subjects.BehaviorSubject

fun <T> BehaviorSubject<T>.requireValue(): T = requireNotNull(value)
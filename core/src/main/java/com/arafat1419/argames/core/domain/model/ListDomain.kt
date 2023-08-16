package com.arafat1419.argames.core.domain.model

data class ListDomain<T>(
	val count: String? = null,
	val next: String? = null,
	val previous: String? = null,
	val results: List<T>? = null,
)
package com.mva.luceneexample.entity

data class NamedEntity(
    val name: String,
    val records: List<NamedEntityRecord>
)

data class NamedEntityRecord(
    val id: Int,
    val synonyms: List<String>,
    val value: String
)
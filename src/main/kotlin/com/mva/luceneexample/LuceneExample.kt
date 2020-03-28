package com.mva.luceneexample

import com.mva.luceneexample.entity.LuceneInMemoryIndexedEntity
import com.mva.luceneexample.entity.NamedEntity
import com.mva.luceneexample.entity.NamedEntityRecordsLoader
import org.apache.lucene.analysis.ru.RussianAnalyzer
import java.io.File

fun main() {
    // 1. Create named entity with alternative names and values
    val entityData = NamedEntity(
        name = "stations",
        records = NamedEntityRecordsLoader.load(file = File("src/main/resources/example-entities/russian-stations.csv"))
    )

    // 2. Index entity in Lucene
    val entity = LuceneInMemoryIndexedEntity(entityData, analyzer = RussianAnalyzer())

    // 3. Search with or without debug prints
    entity.search("мне нужно уехать в нижневартовск, помогите спасите", showDebugInfo = true)
}
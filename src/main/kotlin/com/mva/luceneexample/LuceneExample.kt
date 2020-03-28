package com.mva.luceneexample

import com.mva.luceneexample.entity.LuceneInMemoryIndexedEntity
import com.mva.luceneexample.entity.NamedEntity
import com.mva.luceneexample.entity.NamedEntityRecordsLoader
import org.apache.lucene.analysis.ru.RussianAnalyzer
import java.io.File

fun main() {
    // 1. Create named entity with alternative names and values
    val entity = NamedEntity(
        name = "stations",
        records = NamedEntityRecordsLoader.load(file = File("src/main/resources/example-entities/russian-stations.csv"))
    )

    // 2. Index entity in Lucene
    val lucene = LuceneInMemoryIndexedEntity(entity, analyzer = RussianAnalyzer())

    // 3. Create searcher. It's possible to use different analyzer
    val searcher = lucene.buildSearcher()

    // 4. Search with or without debug prints
    searcher.search("мне нужно уехать в нижневартовск, помогите спасите", showDebugInfo = true)
}
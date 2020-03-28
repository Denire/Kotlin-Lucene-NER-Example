package com.mva.luceneexample.entity

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory

class LuceneInMemoryIndexedEntity(
    entity: NamedEntity,
    private val ramDirectory: Directory = RAMDirectory(),
    private val analyzer: Analyzer = StandardAnalyzer()
) {
    private val indexWriterConfig = IndexWriterConfig(analyzer)
    private val writer = IndexWriter(ramDirectory, indexWriterConfig)

    init {
        indexEntity(entity)
    }

    private fun indexEntity(e: NamedEntity) {
        e.records.forEach { record ->
            record.synonyms.forEach { synonym ->
                indexDocument(record.id.toString(), synonym, record.value)
            }
        }
    }

    private fun indexDocument(id: String, synonym: String, value: String) {
        val document = Document()
        document.add(TextField(ENTITY_RECORD_ID_FIELD, id, Field.Store.YES))
        document.add(TextField(SYNONYM_FIELD, synonym, Field.Store.YES))
        document.add(TextField(RECORD_VALUE_FIELD, value, Field.Store.YES))
        writer.addDocument(document)
    }

    fun buildSearcher(customAnalyzer: Analyzer = analyzer): Searcher {
        writer.close()
        return Searcher(ramDirectory, customAnalyzer)
    }

    class Searcher(ramDirectory: Directory, analyzer: Analyzer) {
        private val indexReader = DirectoryReader.open(ramDirectory)
        private val searcher = IndexSearcher(indexReader)
        private val queryParser = QueryParser(SYNONYM_FIELD, analyzer)


        fun search(queryString: String, showDebugInfo: Boolean = false): List<Document> {
            val query = queryParser.parse(queryString)
            val topDocs = searcher.search(query, 20)
            return topDocs.scoreDocs.map { foundDocument ->
                val document = searcher.doc(foundDocument.doc)
                if (showDebugInfo) {
                    println("Score ${foundDocument.score} for document: $document")
                }
                document
            }
        }
    }

    companion object {
        private const val SYNONYM_FIELD: String = "content"
        private const val ENTITY_RECORD_ID_FIELD: String = "id"
        private const val RECORD_VALUE_FIELD: String = "value"
    }
}

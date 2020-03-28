package com.mva.luceneexample.entity

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.RuntimeException
import java.util.*

/**
 * Loads entity with list of synonyms and values from csv with format like
 * 1;moscow, moskva, moscva;{city: "Moscow", popularity: "9kk"}
 * 2;spb, saint petersburg, saintp, saint-petersburg;{city: "Saint-Petersburg", popularity: "6kk"}
 *
 * reference: src/main/resources/example-entities/russian-stations.csv
 * */
object NamedEntityRecordsLoader {
    fun load(file: File): List<NamedEntityRecord> {
        val records = read(file.inputStream())
        return parse(records)
    }

    private fun read(file: InputStream): CSVParser {
        val reader = InputStreamReader(file)
        val csvFormat = CSVFormat.DEFAULT
            .withDelimiter(';')
            .withIgnoreEmptyLines(true)
            .withIgnoreSurroundingSpaces()
            .withQuote(null)

        return csvFormat.parse(reader)
    }

    private fun parse(records: CSVParser) = records.map { csvRecord ->
        if (csvRecord.size() != 3) throw RuntimeException("CSV Dictionary should have fields like ID;synonyms;value")

        val entityId = csvRecord[0].toInt()
        val synonyms = csvRecord[1].split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        val value = csvRecord[2]
        NamedEntityRecord(entityId, synonyms, value)
    }
}

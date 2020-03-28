# Kotlin-Lucene-NER-Example
Example application to demonstrate how to create **Named Entity** with **Apache Lucene** and perform basic **Named Entity Extraction**.


Create named entity with alternative names and values
   
    val entity = NamedEntity(
            name = "stations",
            records = NamedEntityRecordsLoader.load(file = File("src/main/resources/example-entities/russian-stations.csv"))
        )
        
Index entity in Lucene

    val lucene = LuceneInMemoryIndexedEntity(entity)
    
Create searcher. It's possible to use different analyzer

    val searcher = lucene.buildSearcher()
    
Search with or without debug prints
    
    searcher.search("нижневартовск", showDebugInfo = true)

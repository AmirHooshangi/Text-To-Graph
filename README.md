Text-To-Graph (In Scala and Akka)
======

# Graph representation of a Text using Neo4j.
This is useful if you need to perform Graph mining on a corpus. 
This implementation uses POS(Part of Speech Tagging) to extract words, and represents the corresponding Graph, based on semantic relatedness of the words (based on [wordnet](https://wordnet.princeton.edu/)).
Written in Scala and Akka (for distributed processing)

TODO: downloading OpenNLP trained data and put in the sbt resource directory. 

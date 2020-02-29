package edu.stanford.nlp.util;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

public class MainMain {
  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,coref,quote");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    System.out.println("init "+(Duration.between(start, Instant.now())));
    Thread.sleep(10*60*1000);
  }
}

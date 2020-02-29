package edu.stanford.nlp.util;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public final class StringDedup {
  private final ConcurrentHashMap<String,String> map;

  private StringDedup() {
    map = new ConcurrentHashMap<>();
  }

  public String dedup(String x) {
    if(x == null) {
      return null;
    }
    String dedup = map.putIfAbsent(x,x);
    return dedup != null ? dedup : x;
  }

  public String[] dedupInplace(String[] arr) {
    for(int i = 0; i<arr.length; i++) {
      arr[i] = dedup(arr[i]);
    }
    return arr;
  }

  public void dedupInplace(Map<String, String> map) {
    HashMap<String, String> copy = new HashMap<>(map.size());
    for(Map.Entry<String, String> e: map.entrySet()) {
      copy.put(
          StringDedup.INST.dedup(e.getKey()),
          StringDedup.INST.dedup(e.getValue())
      );
    }
    map.clear();
    map.putAll(copy);
    copy.clear();
  }

  public List<String> dedupInplace(List<String> arr) {
    for(int i = 0; i<arr.size(); i++) {
      arr.set(i, dedup(arr.get(i)));
    }
    return arr;
  }

  public void reset() {
    this.map.clear();
  }

  public static StringDedup INST = new StringDedup();


  public static void main(String[] args) throws InterruptedException {
    Instant start = Instant.now();
    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,coref,quote");
    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
    //props.setProperty("ner.statisticalOnly", "true");
    //props.setProperty("coref.algorithm", "neural");
    // build pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    System.out.println("init "+(Duration.between(start, Instant.now())));
    Thread.sleep(10*60*1000);
  }
}

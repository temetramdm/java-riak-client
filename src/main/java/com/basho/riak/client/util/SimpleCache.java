package com.basho.riak.client.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by gmedina on 12/01/17.
 *
 * @author gmedina
 */
public class SimpleCache<K, V> extends ConcurrentHashMap<K, V> {
  static final int CONCURRENCY_LEVEL = Runtime.getRuntime().availableProcessors() * 2;

  final Function<K, V> loadingFunction;

  public SimpleCache(Function<K, V> loadingFunction) {
    super(CONCURRENCY_LEVEL, 0.7f, CONCURRENCY_LEVEL);
    this.loadingFunction = loadingFunction;
  }

  public V computeIfAbsent(K key) {
        /*
         * We could computeIfAbsent directly but for CPUs < 32 pre-scanning is faster.
         */
    final V value = get(key);
    return value != null ? value : computeIfAbsent(key, loadingFunction);
  }
}

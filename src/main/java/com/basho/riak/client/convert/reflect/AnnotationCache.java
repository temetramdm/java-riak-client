/*
 * This file is provided to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.basho.riak.client.convert.reflect;

import com.basho.riak.client.util.SimpleCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * TODO: consider class reloading and re-scanning
 * @author russell
 * @param <T>
 * 
 */
public class AnnotationCache {
    @SuppressWarnings("rawtypes")
    private final SimpleCache<Class, Future<AnnotationInfo>> cache = new SimpleCache<>(clazz -> {
        final FutureTask<AnnotationInfo> scanner = new FutureTask<>(new AnnotationScanner(clazz));
        scanner.run();
        return scanner;
    });

    /**
     * @param <T>
     * @param name
     * @return
     */
    public <T> AnnotationInfo get(Class<T> clazz) {
        try {
            return cache.computeIfAbsent(clazz).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            cache.remove(clazz);
            throw new RuntimeException(e.getCause());
        }
    }

}

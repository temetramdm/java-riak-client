/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.basho.riak.client.convert.reflect;

import com.basho.riak.client.convert.RiakIndex;

import java.lang.reflect.Method;

/**
 *
 * @author gmedina
 */
public class RiakIndexMethod
{

    private final Method method;
    private final String indexName;
    private final Class<?> type;

    /**
     * The method that is to be wrapped
     *
     * @param method
     */
    public RiakIndexMethod(final Method method)
    {
        this.method = method;
        this.indexName = method.getAnnotation(RiakIndex.class).name();
        this.type = method.getReturnType();
    }

    /**
     * @return the method
     */
    public Method getMethod()
    {
        return method;
    }

    /**
     * @return the indexName
     */
    public String getIndexName()
    {
        return indexName;
    }

    /**
     * @return the type
     */
    public Class<?> getType()
    {
        return type;
    }
}

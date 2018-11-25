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
package com.basho.riak.client.convert;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakLink;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.cap.VClock;
import com.basho.riak.client.convert.reflect.AnnotationHelper;
import com.basho.riak.client.convert.reflect.AnnotationInfo;
import com.basho.riak.client.query.indexes.RiakIndexes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static com.basho.riak.client.convert.KeyUtil.getKey;
import static com.basho.riak.client.convert.ObjectMapperSelector.DEFAULT_SELECTOR;

/**
 * Converts a RiakObject's value to an instance of T. T must have a field
 * annotated with {@link RiakKey} or you must construct the converter with a key to use. RiakObject's value *must* be a JSON string.
 * <p>
 * <p>
 * At present user meta data and {@link RiakLink}s are not converted. This means
 * they are essentially lost in translation.
 * </p>
 *
 * @author russell
 */
public class JSONConverter<T> implements Converter<T>
{
  private final ObjectMapperSelector selector;
  private final Class<T> clazz;
  private final AnnotationInfo annotationInfo;

  /**
   * Create a JSONConverter for creating instances of <code>clazz</code> from
   * JSON and instances of {@link IRiakObject} with a JSON payload from
   * instances of <code>clazz</code>
   *
   * @param clazz the type to convert to/from
   */
  public JSONConverter(Class<T> clazz)
  {
    this(DEFAULT_SELECTOR, clazz);
  }

  /**
   * Create a JSONConverter for creating instances of <code>clazz</code> from
   * JSON and instances of {@link IRiakObject} with a JSON payload from
   * instances of <code>clazz</code>
   *
   * @param selector a custom object mapper
   * @param clazz    the type to convert to/from
   */
  public JSONConverter(ObjectMapperSelector selector, Class<T> clazz)
  {
    this.selector = selector;
    this.clazz = clazz;
    annotationInfo = AnnotationHelper.getInstance().get(clazz);
  }

  /**
   * Converts <code>domainObject</code> to a JSON string and sets that as the
   * payload of a {@link IRiakObject}. Also set the <code>content-type</code>
   * to <code>application/json;charset=UTF-8</code>
   *
   * @param bucket       bucket
   * @param domainObject to be converted
   * @param vclock       the vector clock from Riak
   */
  public IRiakObject fromDomain(String bucket, T domainObject, VClock vclock) throws ConversionException
  {
    try {
      String key = getKey(domainObject);

      final byte[] value = selector.writingMapper().writeValueAsBytes(domainObject);
      Map<String, String> usermetaData = annotationInfo.getUsermetaData(domainObject);
      RiakIndexes indexes = annotationInfo.getIndexes(domainObject);
      Collection<RiakLink> links = annotationInfo.getLinks(domainObject);
      return RiakObjectBuilder.newBuilder(bucket, key)
        .withValue(value)
        .withVClock(vclock)
        .withUsermeta(usermetaData)
        .withIndexes(indexes)
        .withLinks(links)
        .withContentType(selector.contentType())
        .build();
    } catch (IOException e) {
      throw new ConversionException(e);
    }
  }

  /**
   * Converts the <code>value</code> of <code>riakObject</code> to an instance
   * of <code>T</code>.
   *
   * @param riakObject the {@link IRiakObject} to convert to instance of
   *                   <code>T</code>. NOTE: <code>riakObject.getValue()</code> must be a
   *                   JSON string. The charset from
   *                   <code>riakObject.getContentType()</code> is used.
   */
  public T toDomain(final IRiakObject riakObject) throws ConversionException
  {
    if (riakObject == null) {
      return null;
    } else if (riakObject.isDeleted()) {
      try {
        final T domainObject = clazz.newInstance();
        annotationInfo.setRiakTombstone(domainObject, true);
        annotationInfo.setRiakVClock(domainObject, riakObject.getVClock());
        annotationInfo.setRiakKey(domainObject, riakObject.getKey());
        return domainObject;
      } catch (InstantiationException ex) {
        throw new ConversionException("POJO does not provide no-arg constructor", ex);
      } catch (IllegalAccessException ex) {
        throw new ConversionException(ex);
      }
    } else {
      try {
        final T domainObject = selector
          .readingMapper(riakObject.getContentType())
          .readValue(riakObject.getValue(), clazz);
        annotationInfo.setRiakKey(domainObject, riakObject.getKey());
        annotationInfo.setRiakVClock(domainObject, riakObject.getVClock());
        annotationInfo.setUsermetaData(riakObject.getMeta(), domainObject);
        annotationInfo.setIndexes(new RiakIndexes(riakObject.allBinIndexes(), riakObject.allIntIndexesV2()), domainObject);
        annotationInfo.setLinks(riakObject.getLinks(), domainObject);
        return domainObject;
      } catch (IOException e) {
        throw new ConversionException(e);
      }
    }
  }

  /**
   * Returns the {@link ObjectMapper} being used.
   * This is a convenience method to allow changing its behavior.
   *
   * @return The Object Mapper selector
   */
  public ObjectMapperSelector getObjectMapperSelector()
  {
    return selector;
  }
}

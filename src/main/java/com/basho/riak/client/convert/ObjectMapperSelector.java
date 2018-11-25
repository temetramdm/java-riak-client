package com.basho.riak.client.convert;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.basho.riak.client.http.util.Constants.CTYPE_JSON_UTF8;

/**
 * @author Guido Medina, created by gmedina on 25/11/18.
 */
public interface ObjectMapperSelector
{
  Module RIAK_JACKSON_MODULE = new RiakJacksonModule();
  ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(RIAK_JACKSON_MODULE);

  ObjectMapperSelector DEFAULT_SELECTOR = new ObjectMapperSelector()
  {
    @Override
    public ObjectMapper readingMapper(String contentType)
    {
      return OBJECT_MAPPER;
    }

    @Override
    public ObjectMapper writingMapper()
    {
      return OBJECT_MAPPER;
    }

    @Override
    public String contentType()
    {
      return CTYPE_JSON_UTF8;
    }
  };

  /**
   * Returns the reading ObjectMapper associated with this content-type.
   *
   * @param contentType content-type
   * @return the ObjectMapper associated with this content-type.
   */
  ObjectMapper readingMapper(String contentType);

  /**
   * Returns the writing ObjectMapper for this selector.
   *
   * @return the writing ObjectMapper for this selector.
   */
  ObjectMapper writingMapper();

  /**
   * Returns the content-type for writing with this selector.
   *
   * @return the content-type for writing with this selector.
   */
  String contentType();
}

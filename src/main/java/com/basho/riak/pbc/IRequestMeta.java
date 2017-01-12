package com.basho.riak.pbc;

import com.google.protobuf.ByteString;

/**
 * PBC model of request meta data (<code>w, dw, content-type, returnBody?</code>)
 */
public interface IRequestMeta {

	void preparePut(com.basho.riak.protobuf.RiakKvPB.RpbPutReq.Builder builder);

	IRequestMeta returnBody(boolean ret);

	IRequestMeta w(int w);

	IRequestMeta dw(int dw);

	IRequestMeta contentType(String contentType);

	ByteString getContentType();

}
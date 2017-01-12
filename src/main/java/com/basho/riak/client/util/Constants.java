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
package com.basho.riak.client.util;

/**
 * @deprecated with the addition of a protocol buffers client in 0.14 all the
 *             existing REST client code should be in client.http.* this class
 *             has therefore been moved. Please use
 *             com.basho.riak.client.http.util.Constants
 *             instead.
 *             <p>WARNING: This class will be REMOVED in the next version.</p>
 * @see com.basho.riak.client.http.util.Constants
 */
@Deprecated
public interface Constants {

    // Default URL path prefixes Riak HTTP interface
    String RIAK_URL_PREFIX = "/riak";

    // JSON fields used by Riak
    String FL_NAME = "name";
    String FL_KEYS = "keys";
    String FL_SCHEMA = "props";
    String FL_SCHEMA_ALLOW_MULT = "allow_mult";
    String FL_SCHEMA_CHASHFUN = "chash_keyfun";
    String FL_SCHEMA_CHASHFUN_MOD = "mod";
    String FL_SCHEMA_CHASHFUN_FUN = "fun";
    String FL_SCHEMA_LINKFUN = "linkfun";
    String FL_SCHEMA_LINKFUN_MOD = "mod";
    String FL_SCHEMA_LINKFUN_FUN = "fun";
    String FL_SCHEMA_NVAL = "n_val";

    // Header directives used by Riak
    String LINK_TAG = "riaktag";

    // HTTP headers used in Riak
    String HDR_ACCEPT = "accept";
    String HDR_CLIENT_ID = "x-riak-clientid";
    String HDR_CONNECTION = "connection";
    String HDR_CONTENT_LENGTH = "content-length";
    String HDR_CONTENT_TYPE = "content-type";
    String HDR_ETAG = "etag";
    String HDR_IF_MATCH = "if-match";
    String HDR_IF_MODIFIED_SINCE = "if-modified-since";
    String HDR_IF_UNMODIFIED_SINCE = "if-unmodified-since";
    String HDR_IF_NONE_MATCH = "if-none-match";
    String HDR_LAST_MODIFIED = "last-modified";
    String HDR_LINK = "link";
    String HDR_LOCATION = "location";
    String HDR_VCLOCK = "x-riak-vclock";
    // Declared twice because of Erlang has bizarre HTTP header case handling.
    // If a header name is 21 chars or shorteer, it is auto-capitalized between
    // dashes. Otherwise, it is passed as is. Therefore, we just make sure this
    // headers prefix is correctly capitalized in requests.
    String HDR_USERMETA_PREFIX = "x-riak-meta-";
    String HDR_USERMETA_REQ_PREFIX = "X-Riak-Meta-";

    // Content types used in Riak
    String CTYPE_ANY = "*/*";
    String CTYPE_JSON = "application/json";
    String CTYPE_OCTET_STREAM = "application/octet-stream";
    String CTYPE_MULTIPART_MIXED = "multipart/mixed";
    String CTYPE_TEXT = "text/plain";

    // Default r, w, and dw values to use when not specified
    Integer DEFAULT_R = 2;
    Integer DEFAULT_W = null;
    Integer DEFAULT_DW = null;
    
    // Values for the "keys" query parameter
    String NO_KEYS = "false";
    String INCLUDE_KEYS = "true";
    String STREAM_KEYS = "stream";

    // Query parameters used in Riak
    String QP_RETURN_BODY = "returnbody";
    String QP_R = "r";
    String QP_W = "w";
    String QP_DW = "dw";
    String QP_RW = "rw";
    String QP_KEYS = "keys";
    String QP_BUCKETS = "buckets";

    // HTTP method names
    String HTTP_HEAD_METHOD = "HEAD";
    String HTTP_GET_METHOD = "GET";
    String HTTP_PUT_METHOD = "PUT";
    String HTTP_DELETE_METHOD = "DELETE";

    // Riak magic numbers
    int RIAK_CLIENT_ID_LENGTH = 4;

    // List bucket operation parameters
    String LIST_BUCKETS = "true";
}

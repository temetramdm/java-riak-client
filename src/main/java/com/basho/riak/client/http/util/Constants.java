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
package com.basho.riak.client.http.util;

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
    String FL_SCHEMA_FUN_MOD = "mod";
    String FL_SCHEMA_FUN_FUN = "fun";
    String FL_SCHEMA_FUN_NAME = "name";
    String FL_SCHEMA_LAST_WRITE_WINS = "last_write_wins";
    String FL_SCHEMA_BACKEND = "backend";
    String FL_SCHEMA_SMALL_VCLOCK = "small_vclock";
    String FL_SCHEMA_BIG_VCLOCK = "big_vclock";
    String FL_SCHEMA_YOUNG_VCLOCK = "young_vclock";
    String FL_SCHEMA_OLD_VCLOCK = "old_vclock";
    String FL_SCHEMA_R = "r";
    String FL_SCHEMA_W = "w";
    String FL_SCHEMA_DW = "dw";
    String FL_SCHEMA_RW = "rw";
    String FL_SCHEMA_PR = "pr";
    String FL_SCHEMA_PW = "pw";
    String FL_SCHEMA_BASIC_QUORUM = "basic_quorum";
    String FL_SCHEMA_NOT_FOUND_OK = "notfound_ok";
    String FL_SCHEMA_POSTCOMMIT = "postcommit";
    String FL_SCHEMA_PRECOMMIT = "precommit";
    String FL_SCHEMA_SEARCH = "search";
    String FL_BUCKETS = "buckets";

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
    String HDR_DELETED = "x-riak-deleted";
    // Declared twice because of Erlang has bizarre HTTP header case handling.
    // If a header name is 21 chars or shorteer, it is auto-capitalized between
    // dashes. Otherwise, it is passed as is. Therefore, we just make sure this
    // headers prefix is correctly capitalized in requests.
    String HDR_USERMETA_PREFIX = "x-riak-meta-";
    String HDR_USERMETA_REQ_PREFIX = "X-Riak-Meta-";
    String HDR_SEC_INDEX_REQ_PREFIX = "X-Riak-Index-";
    String HDR_SEC_INDEX_PREFIX = "x-riak-index-";

    // Content types used in Riak
    String CTYPE_ANY = "*/*";
    String CTYPE_JSON = "application/json";
    String CTYPE_JSON_UTF8 = "application/json; charset=UTF-8";
    String CTYPE_OCTET_STREAM = "application/octet-stream";
    String CTYPE_MULTIPART_MIXED = "multipart/mixed";
    String CTYPE_TEXT = "text/plain";
    String CTYPE_TEXT_UTF8 = "text/plain; charset=UTF-8";

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
    String QP_PR = "pr";
    String QP_PW = "pw";
    String QP_NOT_FOUND_OK = "notfound_ok";
    String QP_BASIC_QUORUM = "basic_quorum";
    String QP_ASIS = "asis";
    String QP_TIMEOUT = "timeout";

    // HTTP method names
    String HTTP_HEAD_METHOD = "HEAD";
    String HTTP_GET_METHOD = "GET";
    String HTTP_PUT_METHOD = "PUT";
    String HTTP_DELETE_METHOD = "DELETE";

    // Riak magic numbers
    int RIAK_CLIENT_ID_LENGTH = 4;

    // List bucket operation parameters
    String LIST_BUCKETS = "true";
    String STREAM_BUCKETS = "stream";
    
    // 2i features
    String QP_INDEX_STREAM = "stream";
    String QP_INDEX_MAX_RESULTS = "max_results";
    String QP_INDEX_RETURN_TERMS = "return_terms";
    String QP_INDEX_CONTINUATION = "continuation";
    
    // Counters
    String QP_RETURNVALUE = "returnvalue";
}

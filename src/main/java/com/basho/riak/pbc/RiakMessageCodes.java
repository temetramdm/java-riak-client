/**
 * This file is part of riak-java-pb-client 
 *
 * Copyright (c) 2010 by Trifork
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.basho.riak.pbc;

/**
 * PBC API message code constants.
 */
interface RiakMessageCodes {
	int MSG_ErrorResp = 0;
	int MSG_PingReq = 1;
	int MSG_PingResp = 2;
	int MSG_GetClientIdReq = 3;
	int MSG_GetClientIdResp = 4;
	int MSG_SetClientIdReq = 5;
	int MSG_SetClientIdResp = 6;
	int MSG_GetServerInfoReq = 7;
	int MSG_GetServerInfoResp = 8;
	int MSG_GetReq = 9;
	int MSG_GetResp = 10;
	int MSG_PutReq = 11;
	int MSG_PutResp = 12;
	int MSG_DelReq = 13;
	int MSG_DelResp = 14;
	int MSG_ListBucketsReq = 15;
	int MSG_ListBucketsResp = 16;
	int MSG_ListKeysReq = 17;
	int MSG_ListKeysResp = 18;
	int MSG_GetBucketReq = 19;
	int MSG_GetBucketResp = 20;
	int MSG_SetBucketReq = 21;
	int MSG_SetBucketResp = 22;
	int MSG_MapRedReq = 23;
	int MSG_MapRedResp = 24;
	int MSG_IndexReq = 25;
	int MSG_IndexResp = 26;
    int MSG_SearchQueryReq = 27;
    int MSG_SearchQueryResp = 28;
    int MSG_ResetBucketReq = 29;
    int MSG_ResetBucketResp = 30;
    int MSG_CounterUpdateReq = 50;
    int MSG_CounterUpdateResp = 51;
    int MSG_CounterGetReq = 52;
    int MSG_CounterGetResp = 53;

}

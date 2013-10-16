/*
 * Copyright 2013 Basho Technologies Inc
 *
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
package com.basho.riak.client.operations.crdt;

import com.basho.riak.client.query.crdt.ops.CounterOp;
import com.basho.riak.client.query.crdt.ops.CrdtOp;

public class CounterMutation extends CrdtMutation
{

    private long delta = 0;

    CounterMutation()
    {
    }

    CounterMutation(long delta)
    {
        this.delta = delta;
    }

    public static CounterMutation newCounter()
    {
        return new CounterMutation();
    }

    public static CounterMutation increment()
    {
        return new CounterMutation(1);
    }

    public static CounterMutation decrement()
    {
        return new CounterMutation(-1);
    }

    public CounterMutation increment(long delta)
    {
        this.delta += delta;
        return this;
    }

    public long getDelta()
    {
        return delta;
    }

    @Override
    public CrdtOp getOp()
    {
        return new CounterOp(delta);
    }
}

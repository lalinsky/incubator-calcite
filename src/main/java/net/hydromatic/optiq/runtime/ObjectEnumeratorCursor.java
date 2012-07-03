/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.optiq.runtime;

import net.hydromatic.linq4j.Enumerator;

/**
 * Implementation of {@link net.hydromatic.optiq.runtime.Cursor} on top of an
 * {@link net.hydromatic.linq4j.Enumerator} that
 * returns an array of {@link Object} for each row.
 *
 * @author jhyde
 */
public class ObjectEnumeratorCursor extends AbstractCursor {
    private final Enumerator<Object> enumerator;

    /**
     * Creates an ObjectEnumeratorCursor.
     *
     * @param enumerator Enumerator
     */
    public ObjectEnumeratorCursor(Enumerator<Object> enumerator) {
        this.enumerator = enumerator;
    }

    @Override
    protected Getter createGetter(int ordinal, boolean[] wasNull) {
        return new ObjectEnumeratorGetter(ordinal, wasNull);
    }

    @Override
    public boolean next() {
        return enumerator.moveNext();
    }

    class ObjectEnumeratorGetter implements Getter {
        protected final boolean[] wasNull;

        public ObjectEnumeratorGetter(int field, boolean[] wasNull) {
            assert field == 0;
            this.wasNull = wasNull;
        }

        public Object getObject() {
            Object o = enumerator.current();
            wasNull[0] = (o == null);
            return o;
        }

        public boolean wasNull() {
            return wasNull[0];
        }
    }
}

// End ObjectEnumeratorCursor.java

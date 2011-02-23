/*
    Copyright 2008 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package com.jenkov.db.impl.mapping.method;

import java.io.Reader;

/**
 * @author Jakob Jenkov,  Jenkov Development
 */
public class CharacterStream {
    protected Reader    reader = null;
    protected int       length = 0;

    public CharacterStream(Reader reader) {
        this.reader = reader;
    }

    public CharacterStream(Reader reader, int length) {
        this.reader = reader;
        this.length = length;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof CharacterStream)) return false;

        CharacterStream otherStream = (CharacterStream) obj;
        if(getLength() != otherStream.getLength()) return false;

        return getReader().equals(otherStream.getReader());
    }
}

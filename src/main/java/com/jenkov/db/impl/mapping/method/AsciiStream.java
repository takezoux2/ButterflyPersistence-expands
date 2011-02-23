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



/**
 * @author Jakob Jenkov,  Jenkov Development
 */
package com.jenkov.db.impl.mapping.method;

import java.io.InputStream;

public class AsciiStream {

    protected InputStream   inputStream = null;
    protected int           length = 0;

    public AsciiStream(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public AsciiStream(InputStream inputStream, int length) {
        this.inputStream = inputStream;
        this.length = length;
    }

    public InputStream getInputStream(){
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int getLength(){
        return this.length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public boolean equals(Object obj) {
        if (obj == null                  ) return false;
        if (!(obj instanceof AsciiStream)) return false;

        AsciiStream otherStream = (AsciiStream) obj;
        if(getLength() != otherStream.getLength()) return false;

        return getInputStream().equals(otherStream.getInputStream());
    }
}

/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.conversion;

import org.mapstruct.ap.model.Type;

public class ReverseConversion implements Conversion {

    private Conversion conversion;

    public static ReverseConversion reverse(Conversion conversion) {
        return new ReverseConversion( conversion );
    }

    private ReverseConversion(Conversion conversion) {
        this.conversion = conversion;
    }

    @Override
    public String to(String sourcePropertyAccessor, Type type) {
        return conversion.from( sourcePropertyAccessor, type );
    }

    @Override
    public String from(String targetPropertyAccessor, Type type) {
        return conversion.to( targetPropertyAccessor, type );
    }
}

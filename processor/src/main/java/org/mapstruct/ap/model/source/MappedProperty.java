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
package org.mapstruct.ap.model.source;

import org.mapstruct.ap.model.Type;

public class MappedProperty {

    private final String sourceName;
    private final String sourceReadAccessorName;
    private final String sourceWriteAccessorName;
    private final Type sourceType;
    private final String targetName;
    private final String targetReadAccessorName;
    private final String targetWriteAccessorName;
    private final Type targetType;

    public MappedProperty(String sourceName, String sourceReadAccessorName, String sourceWriteAccessorName,
                          Type sourceType, String targetName, String targetReadAccessorName, String targetWriteAccessorName,
                          Type targetType) {
        this.sourceName = sourceName;
        this.sourceReadAccessorName = sourceReadAccessorName;
        this.sourceWriteAccessorName = sourceWriteAccessorName;
        this.sourceType = sourceType;
        this.targetName = targetName;
        this.targetReadAccessorName = targetReadAccessorName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetType = targetType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceReadAccessorName() {
        return sourceReadAccessorName;
    }

    public String getSourceWriteAccessorName() {
        return sourceWriteAccessorName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetReadAccessorName() {
        return targetReadAccessorName;
    }

    public String getTargetWriteAccessorName() {
        return targetWriteAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    @Override
    public String toString() {
        return sourceType + " " + sourceName + " <=> " + targetType + " " + targetName;
    }
}

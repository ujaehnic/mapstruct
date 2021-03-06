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

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.util.TypeUtil;

import static org.mapstruct.ap.conversion.ReverseConversion.reverse;

public class Conversions {

    private TypeUtil typeUtil;
    private final Map<Key, Conversion> conversions = new HashMap<Conversions.Key, Conversion>();
    private final DeclaredType enumType;
    private final DeclaredType stringType;

    public Conversions(Elements elementUtils, Types typeUtils, TypeUtil typeUtil) {
        this.typeUtil = typeUtil;

        this.enumType = typeUtils.getDeclaredType( elementUtils.getTypeElement( Enum.class.getCanonicalName() ) );
        this.stringType = typeUtils.getDeclaredType( elementUtils.getTypeElement( String.class.getCanonicalName() ) );

        registerNativeTypeConversion( byte.class, Byte.class );
        registerNativeTypeConversion( byte.class, short.class );
        registerNativeTypeConversion( byte.class, Short.class );
        registerNativeTypeConversion( byte.class, int.class );
        registerNativeTypeConversion( byte.class, Integer.class );
        registerNativeTypeConversion( byte.class, long.class );
        registerNativeTypeConversion( byte.class, Long.class );
        registerNativeTypeConversion( byte.class, float.class );
        registerNativeTypeConversion( byte.class, Float.class );
        registerNativeTypeConversion( byte.class, double.class );
        registerNativeTypeConversion( byte.class, Double.class );

        registerNativeTypeConversion( Byte.class, short.class );
        registerNativeTypeConversion( Byte.class, Short.class );
        registerNativeTypeConversion( Byte.class, int.class );
        registerNativeTypeConversion( Byte.class, Integer.class );
        registerNativeTypeConversion( Byte.class, long.class );
        registerNativeTypeConversion( Byte.class, Long.class );
        registerNativeTypeConversion( Byte.class, float.class );
        registerNativeTypeConversion( Byte.class, Float.class );
        registerNativeTypeConversion( Byte.class, double.class );
        registerNativeTypeConversion( Byte.class, Double.class );

        registerNativeTypeConversion( short.class, Short.class );
        registerNativeTypeConversion( short.class, int.class );
        registerNativeTypeConversion( short.class, Integer.class );
        registerNativeTypeConversion( short.class, long.class );
        registerNativeTypeConversion( short.class, Long.class );
        registerNativeTypeConversion( short.class, float.class );
        registerNativeTypeConversion( short.class, Float.class );
        registerNativeTypeConversion( short.class, double.class );
        registerNativeTypeConversion( short.class, Double.class );

        registerNativeTypeConversion( Short.class, int.class );
        registerNativeTypeConversion( Short.class, Integer.class );
        registerNativeTypeConversion( Short.class, long.class );
        registerNativeTypeConversion( Short.class, Long.class );
        registerNativeTypeConversion( Short.class, float.class );
        registerNativeTypeConversion( Short.class, Float.class );
        registerNativeTypeConversion( Short.class, double.class );
        registerNativeTypeConversion( Short.class, Double.class );

        registerNativeTypeConversion( int.class, Integer.class );
        registerNativeTypeConversion( int.class, long.class );
        registerNativeTypeConversion( int.class, Long.class );
        registerNativeTypeConversion( int.class, float.class );
        registerNativeTypeConversion( int.class, Float.class );
        registerNativeTypeConversion( int.class, double.class );
        registerNativeTypeConversion( int.class, Double.class );

        registerNativeTypeConversion( Integer.class, long.class );
        registerNativeTypeConversion( Integer.class, Long.class );
        registerNativeTypeConversion( Integer.class, float.class );
        registerNativeTypeConversion( Integer.class, Float.class );
        registerNativeTypeConversion( Integer.class, double.class );
        registerNativeTypeConversion( Integer.class, Double.class );

        registerNativeTypeConversion( long.class, Long.class );
        registerNativeTypeConversion( long.class, float.class );
        registerNativeTypeConversion( long.class, Float.class );
        registerNativeTypeConversion( long.class, double.class );
        registerNativeTypeConversion( long.class, Double.class );

        registerNativeTypeConversion( Long.class, float.class );
        registerNativeTypeConversion( Long.class, Float.class );
        registerNativeTypeConversion( Long.class, double.class );
        registerNativeTypeConversion( Long.class, Double.class );

        registerNativeTypeConversion( float.class, Float.class );
        registerNativeTypeConversion( float.class, double.class );
        registerNativeTypeConversion( float.class, Double.class );

        registerNativeTypeConversion( Float.class, double.class );
        registerNativeTypeConversion( Float.class, Double.class );

        registerNativeTypeConversion( double.class, Double.class );

        registerNativeTypeConversion( boolean.class, Boolean.class );
        registerNativeTypeConversion( char.class, Character.class );

        registerToStringConversion( byte.class );
        registerToStringConversion( Byte.class );
        registerToStringConversion( short.class );
        registerToStringConversion( Short.class );
        registerToStringConversion( int.class );
        registerToStringConversion( Integer.class );
        registerToStringConversion( long.class );
        registerToStringConversion( Long.class );
        registerToStringConversion( float.class );
        registerToStringConversion( Float.class );
        registerToStringConversion( double.class );
        registerToStringConversion( Double.class );
        registerToStringConversion( boolean.class );
        registerToStringConversion( Boolean.class );
        register( char.class, String.class, new CharToStringConversion() );
        register( Character.class, String.class, new CharWrapperToStringConversion() );

        register( Enum.class, String.class, new EnumStringConversion() );
    }

    private void registerNativeTypeConversion(Class<?> sourceType, Class<?> targetType) {
        if ( sourceType.isPrimitive() && targetType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToPrimitiveConversion( sourceType ) );
        }
        else if ( sourceType.isPrimitive() && !targetType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToWrapperConversion( sourceType, targetType ) );
        }
        else if ( !sourceType.isPrimitive() && targetType.isPrimitive() ) {
            register( sourceType, targetType, reverse( new PrimitiveToWrapperConversion( targetType, sourceType ) ) );
        }
        else {
            register( sourceType, targetType, new WrapperToWrapperConversion( sourceType, targetType ) );
        }
    }

    private void registerToStringConversion(Class<?> sourceType) {
        if ( sourceType.isPrimitive() ) {
            register( sourceType, String.class, new PrimitiveToStringConversion( sourceType ) );
        }
        else {
            register( sourceType, String.class, new WrapperToStringConversion( sourceType ) );
        }
    }

    private void register(Class<?> sourceType, Class<?> targetType, Conversion conversion) {
        conversions.put( Key.forClasses( sourceType, targetType ), conversion );
        conversions.put( Key.forClasses( targetType, sourceType ), reverse( conversion ) );
    }

    public Conversion getConversion(Type sourceType, Type targetType) {
        if ( sourceType.isEnumType() && targetType.equals( typeUtil.getType( stringType ) ) ) {
            sourceType = typeUtil.getType( enumType );
        }
        else if ( targetType.isEnumType() && sourceType.equals( typeUtil.getType( stringType ) ) ) {
            targetType = typeUtil.getType( enumType );
        }

        return conversions.get( new Key( sourceType, targetType ) );
    }

    private static class Key {
        private final Type sourceType;
        private final Type targetType;

        private static Key forClasses(Class<?> sourceType, Class<?> targetType) {
            return new Key( Type.forClass( sourceType ), Type.forClass( targetType ) );
        }

        private Key(Type sourceType, Type targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public String toString() {
            return "Key [sourceType=" + sourceType + ", targetType="
                + targetType + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
            result = prime * result
                + ( ( targetType == null ) ? 0 : targetType.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            Key other = (Key) obj;
            if ( sourceType == null ) {
                if ( other.sourceType != null ) {
                    return false;
                }
            }
            else if ( !sourceType.equals( other.sourceType ) ) {
                return false;
            }
            if ( targetType == null ) {
                if ( other.targetType != null ) {
                    return false;
                }
            }
            else if ( !targetType.equals( other.targetType ) ) {
                return false;
            }
            return true;
        }
    }
}

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
package org.mapstruct.ap.util;

import java.beans.Introspector;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

/**
 * Provides functionality around {@link ExecutableElement}s.
 *
 * @author Gunnar Morling
 */
public class Executables {

    public static boolean isGetterMethod(ExecutableElement method) {
        return isNonBooleanGetterMethod( method ) || isBooleanGetterMethod( method );
    }

    private static boolean isNonBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return method.getParameters().isEmpty() &&
            name.startsWith( "get" ) &&
            name.length() > 3 &&
            method.getReturnType().getKind() != TypeKind.VOID;
    }

    private static boolean isBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return method.getParameters().isEmpty() &&
            name.startsWith( "is" ) &&
            name.length() > 2 &&
            method.getReturnType().getKind() == TypeKind.BOOLEAN;
    }

    public static boolean isSetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        if ( name.startsWith( "set" ) && name.length() > 3 && method.getParameters()
            .size() == 1 && method.getReturnType().getKind() == TypeKind.VOID ) {
            return true;
        }

        return false;
    }

    public static String getPropertyName(ExecutableElement getterOrSetterMethod) {
        if ( isNonBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }
        else if ( isBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 2 )
            );
        }
        else if ( isSetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }

        throw new IllegalArgumentException( "Executable " + getterOrSetterMethod + " is not getter or setter method." );
    }

    public static ExecutableElement getCorrespondingSetterMethod(Element element, ExecutableElement getterMethod) {
        String propertyName = getPropertyName( getterMethod );

        for ( ExecutableElement setterMethod : Filters.setterMethodsIn( element.getEnclosedElements() ) ) {
            if ( getPropertyName( setterMethod ).equals( propertyName ) ) {
                return setterMethod;
            }
        }

        return null;
    }

    public static ExecutableElement getCorrespondingGetterMethod(Element element, ExecutableElement setterMethod) {
        String propertyName = getPropertyName( setterMethod );

        for ( ExecutableElement getterMethod : Filters.getterMethodsIn( element.getEnclosedElements() ) ) {
            if ( getPropertyName( getterMethod ).equals( propertyName ) ) {
                return getterMethod;
            }
        }

        return null;
    }
}

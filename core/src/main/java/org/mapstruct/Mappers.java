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
package org.mapstruct;

/**
 * Factory for getting mapper instances.
 *
 * @author Gunnar Morling
 */
public class Mappers {

    private final static String IMPLEMENTATION_SUFFIX = "Impl";

    /**
     * Returns an instance of the given mapper type.
     *
     * @param clazz The type of the mapper to return.
     *
     * @return An instance of the given mapper type.
     */
    public static <T> T getMapper(Class<T> clazz) {
        try {

            // Check that
            // - clazz is an interface
            // - the implementation type implements clazz
            // - clazz is annotated with @Mapper
            //
            // Use privileged action
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            if ( classLoader == null ) {
                classLoader = Mappers.class.getClassLoader();
            }

            @SuppressWarnings("unchecked")
            T mapper = (T) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX ).newInstance();

            return mapper;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}

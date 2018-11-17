/*
 * This file is provided to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.basho.riak.client.convert.reflect;

import com.basho.riak.client.cap.VClock;
import com.basho.riak.client.convert.*;
import com.basho.riak.client.util.SimpleCache;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Singleton that wraps a cache of Class -> AnnotatioInfo and provides
 * convenience methods for getting and setting Riak annotated field values
 * 
 * @author russell
 * 
 */
public class AnnotationHelper {

    private static final AnnotationHelper INSTANCE = new AnnotationHelper();

    private SimpleCache<Class, AnnotationInfo> annotationCache = new SimpleCache<>(AnnotationHelper::annotationInfo);

    private static AnnotationInfo annotationInfo(Class clazz) {
        Field riakKeyField = null;
        Method riakKeyGetterMethod = null;
        Method riakKeySetterMethod = null;
        Field riakVClockField = null;
        Field riakTombstoneField = null;
        Field usermetaMapField = null;
        Field linksField = null;
        List<UsermetaField> usermetaItemFields = new ArrayList<>();
        List<RiakIndexField> indexFields = new ArrayList<>();
        List<RiakIndexMethod> indexMethods = new ArrayList<>();

        Class currentClass = clazz;
        while(currentClass != Object.class) {

            final Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {

                if (riakKeyField == null && field.isAnnotationPresent(RiakKey.class)) {

                    riakKeyField = ClassUtil.checkAndFixAccess(field);
                }

                if (riakVClockField == null && field.isAnnotationPresent(RiakVClock.class)) {

                    // restrict the field type to byte[] or VClock
                    if (!(field.getType().isArray() &&
                       field.getType().getComponentType().equals(byte.class)) &&
                       !field.getType().isAssignableFrom(VClock.class)
                    ) {
                        throw new IllegalArgumentException(field.getType().toString());
                    }

                    riakVClockField = ClassUtil.checkAndFixAccess(field);
                }

                if (riakTombstoneField == null && field.isAnnotationPresent(RiakTombstone.class)) {

                    // restrict the field to boolean
                    if (!field.getType().equals(Boolean.TYPE)) {
                        throw new IllegalArgumentException(field.getType().toString());
                    }
                    riakTombstoneField = ClassUtil.checkAndFixAccess(field);
                }

                if (field.isAnnotationPresent(RiakUsermeta.class)) {
                    RiakUsermeta a = field.getAnnotation(RiakUsermeta.class);
                    String key = a.key();

                    if (!"".equals(key)) {
                        usermetaItemFields.add(new UsermetaField(ClassUtil.checkAndFixAccess(field)));
                    } else if (usermetaMapField == null) {
                        usermetaMapField = ClassUtil.checkAndFixAccess(field);
                    }

                }

                if(field.isAnnotationPresent(RiakIndex.class)) {
                    indexFields.add(new RiakIndexField(ClassUtil.checkAndFixAccess(field)));
                }

                if (linksField == null && field.isAnnotationPresent(RiakLinks.class)) {
                    linksField = ClassUtil.checkAndFixAccess(field);
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RiakIndex.class)) {
                if (method == null || method.getAnnotation(RiakIndex.class) == null
                   || "".equals(method.getAnnotation(RiakIndex.class).name())
                   || (!method.getReturnType().equals(String.class)
                   && !method.getReturnType().equals(Integer.class)
                   && !method.getReturnType().equals(int.class))
                   && !method.getReturnType().equals(Long.class)
                   && !method.getReturnType().equals(long.class)
                   && !Set.class.isAssignableFrom(method.getReturnType())) {
                    continue;
                }
                if (Set.class.isAssignableFrom(method.getReturnType())) {
                    // Verify it's a Set<String> or Set<Integer>
                    final Type t = method.getGenericReturnType();
                    if (t instanceof ParameterizedType) {
                        final Class<?> genericType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
                        if (!genericType.equals(String.class) && !genericType.equals(Integer.class) && !genericType.equals(Long.class)) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                indexMethods.add(new RiakIndexMethod(ClassUtil.checkAndFixAccess(method)));
            }
            else if (method.isAnnotationPresent(RiakKey.class))
            {
                if (method.getReturnType().equals(String.class)) {
                    riakKeyGetterMethod = ClassUtil.checkAndFixAccess(method);
                } else if (method.getReturnType().equals(Void.TYPE) &&
                   (method.getParameterTypes().length == 1 &&
                      method.getParameterTypes()[0].equals(String.class))) {
                    riakKeySetterMethod = ClassUtil.checkAndFixAccess(method);
                }
            }
        }

        return new AnnotationInfo(riakKeyField, riakKeyGetterMethod, riakKeySetterMethod,
           usermetaItemFields, usermetaMapField,
           indexFields, indexMethods, linksField, riakVClockField,
           riakTombstoneField);
    }

    private AnnotationHelper() {}

    public static AnnotationHelper getInstance() {
        return INSTANCE;
    }

    public AnnotationInfo get(Class clazz) {
        return annotationCache.computeIfAbsent(clazz);
    }

    public <T> String getRiakKey(T obj) {
        return annotationCache.computeIfAbsent(obj.getClass()).getRiakKey(obj);
    }

    public <T> T setRiakKey(T obj, String key) {
        annotationCache.computeIfAbsent(obj.getClass()).setRiakKey(obj, key);
        return obj;
    }

    public <T> T setRiakVClock(T obj, VClock vclock) {
        final AnnotationInfo annotationInfo = annotationCache.computeIfAbsent(obj.getClass());
        annotationInfo.setRiakVClock(obj, vclock);
        return obj;
    }

    public <T> VClock getRiakVClock(T obj) {
        final AnnotationInfo annotationInfo = annotationCache.computeIfAbsent(obj.getClass());
        return annotationInfo.getRiakVClock(obj);
    }
}

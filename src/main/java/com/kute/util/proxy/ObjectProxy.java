package com.kute.util.proxy;

import com.kute.domain.User;
import com.sun.istack.internal.NotNull;
import javassist.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by kute on 2018/07/25 21:23
 */
public class ObjectProxy {
    public static final String PERMISSION_FILED_NAME = "perms";

    public static final String PERMISSION_SET = "setPerms";

    public static final String PERMISSION_GET = "getPerms";

    private static Map<String, Class<?>> cached = new ConcurrentHashMap<>();

    public static Object getObjectProxy(@NotNull Object delegate) throws Exception {
        String className = delegate.getClass().getName();
        Class<?> clazz = cached.get(className);
        if (clazz == null) {
            ClassPool mPool = ClassPool.getDefault();
            CtClass mCtc = mPool.makeClass(ObjectProxy.class.getPackage().getName() + "." + delegate.getClass().getSimpleName() + "JavaassistProxy");
            mCtc.setSuperclass(mPool.get(delegate.getClass().getName()));
            mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
            CtClass mapClass = mPool.get(Map.class.getName());
            CtField permissionField = new CtField(mapClass, PERMISSION_FILED_NAME, mCtc);
            permissionField.setModifiers(Modifier.PRIVATE);
            mCtc.addField(permissionField);
            CtMethod setMethod = CtNewMethod.setter(PERMISSION_SET, permissionField);
            CtMethod getMethod = CtNewMethod.getter(PERMISSION_GET, permissionField);
            mCtc.addMethod(setMethod);
            mCtc.addMethod(getMethod);
            clazz = mCtc.toClass(ClassUtils.getDefaultClassLoader(), null);
            cached.put(className, clazz);
        }
        Object proxy = clazz.newInstance();
        
        if (delegate instanceof Map) {
            ((Map) proxy).putAll((Map) delegate);
        } else if (delegate instanceof Collection) {
            ((Collection) proxy).addAll((Collection) delegate);
        } else {
            BeanUtils.copyProperties(proxy, delegate);
        }

//        BeanUtils.setProperty(proxy, "fieldName", "fieldValue");
        return proxy;
    }

    public static void main(String[] args) {

        try{
            User user = new User("kute");
            Object object = ObjectProxy.getObjectProxy(user);
            boolean l = object instanceof  User;
            if(l) {
                User newUser = (User) object;
                System.out.println(newUser);
            }
        } catch(Exception e){
        e.printStackTrace();
        }

    }
}

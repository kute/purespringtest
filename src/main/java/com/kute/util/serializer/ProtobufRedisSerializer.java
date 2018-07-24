package com.kute.util.serializer;

import com.google.protobuf.GeneratedMessage;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Method;

/**
 * created by kute on 2018/07/23 09:40
 * <p>
 * protocol buffer serializer
 */
public class ProtobufRedisSerializer<T> implements RedisSerializer<T> {
    private static final byte[] EMPTY_ARRAY = new byte[0];

    private static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    private Class<T> type;

    public ProtobufRedisSerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return ProtobufRedisSerializer.EMPTY_ARRAY;
        }
        try {
            GeneratedMessage gm = (GeneratedMessage) t;
            return gm.toByteArray();
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ProtobufRedisSerializer.isEmpty(bytes)) {
            return null;
        }
        try {
            Method method = type.getMethod("parseFrom", new Class[]{bytes.getClass()});
            return (T) method.invoke(type, new Object[]{bytes});
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}

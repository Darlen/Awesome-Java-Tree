package tree.kafka.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Description:  Redis 帮助类
 *
 * @Author tree
 * @Date 2017/8/13 23:14
 * @Version 1.0
 */
@Component
public class RedisUtils {

    @Autowired
    protected RedisTemplate<?,?> redisTemplate;

    /**
     * 设置redisTemplate
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     * <br>------------------------------<br>
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public byte[] get(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.get(key);
            }
        });
    }

    public String get(final String key) {
        return (String)this.redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                byte[] value = redis.get(key.getBytes());
                return value == null?null:new String(value);
            }
        });
    }

//    public <T> T get(final byte[] key, final RedisTransferCallback<T> callback) {
//        return this.redisTemplate.execute(new RedisCallback() {
//            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] value = redis.get(key);
//                return value == null?null:callback.transfer(value);
//            }
//        });
//    }
//
//    public <T> T getObject4Json(final byte[] key, final Class<T> clazz) {
//        return this.redisTemplate.execute(new RedisCallback() {
//            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] value = redis.get(key);
//                return value == null?null: JSON.parseObject(new String(value), clazz);
//            }
//        });
//    }
//
//    public <T> List<T> getList4Json(final byte[] key, final Class<T> clazz) {
//        return (List)this.redisTemplate.execute(new RedisCallback() {
//            public List<T> doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] value = redis.get(key);
//                return value == null?null:JsonUtils.toList(new String(value), clazz);
//            }
//        });
//    }
//
//    public <T> Set<T> getSet4Json(final byte[] key, final Class<T> clazz) {
//        return (Set)this.redisTemplate.execute(new RedisCallback() {
//            public Set<T> doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] value = redis.get(key);
//                return value == null?null:JsonUtils.toSet(new String(value), clazz);
//            }
//        });
//    }
//
//    public <K, V> Map<K, V> getMap4Json(final byte[] key, final Class<K> keyClass, final Class<V> valueClass) {
//        return (Map)this.redisTemplate.execute(new RedisCallback() {
//            public Map<K, V> doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] value = redis.get(key);
//                return value == null?null:JsonUtils.toMap(new String(value), keyClass, valueClass);
//            }
//        });
//    }

    public byte[] getSet(final byte[] key, final byte[] value) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getSet(key, value);
            }
        });
    }

    public List<byte[]> mGet(final byte[]... keys) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.mGet(keys);
            }
        });
    }

    public void set(final byte[] key, final byte[] value) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.set(key, value);
                return null;
            }
        });
    }

//    public <T> void set4Json(final byte[] key, final T value) {
//        this.redisTemplate.execute(new RedisCallback() {
//            public Void doInRedis(RedisConnection redis) throws DataAccessException {
//                byte[] jsonValue = JsonUtils.toJson(value).getBytes();
//                redis.set(key, jsonValue);
//                return null;
//            }
//        });
//    }

    public Boolean setNX(final byte[] key, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.setNX(key, value);
            }
        });
    }

    public void setEx(final byte[] key, final long seconds, final byte[] value) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setEx(key, seconds, value);
                return null;
            }
        });
    }

    public void pSetEx(final byte[] key, final long milliseconds, final byte[] value) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pSetEx(key, milliseconds, value);
                return null;
            }
        });
    }

    public void mSet(final Map<byte[], byte[]> tuple) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.mSet(tuple);
                return null;
            }
        });
    }

    public Boolean mSetNX(final Map<byte[], byte[]> tuple) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.mSetNX(tuple);
            }
        });
    }

    public Long incr(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incr(key);
            }
        });
    }

    public Long incrBy(final byte[] key, final long value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incrBy(key, value);
            }
        });
    }

    public Double incrBy(final byte[] key, final double value) {
        return (Double)this.redisTemplate.execute(new RedisCallback() {
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incrBy(key, value);
            }
        });
    }

    public Long decr(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.decr(key);
            }
        });
    }

    public Long decrBy(final byte[] key, final long value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.decrBy(key, value);
            }
        });
    }

    public Long append(final byte[] key, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.append(key, value);
            }
        });
    }

    public byte[] getRange(final byte[] key, final long begin, final long end) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getRange(key, begin, end);
            }
        });
    }

    public void setRange(final byte[] key, final byte[] value, final long offset) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setRange(key, value, offset);
                return null;
            }
        });
    }

    public Boolean getBit(final byte[] key, final long offset) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getBit(key, offset);
            }
        });
    }

    public Boolean setBit(final byte[] key, final long offset, final boolean value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.setBit(key, offset, value);
            }
        });
    }

    public Long bitCount(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitCount(key);
            }
        });
    }

    public Long bitCount(final byte[] key, final long begin, final long end) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitCount(key, begin, end);
            }
        });
    }

    public Long bitOp(final RedisStringCommands.BitOperation op, final byte[] destination, final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitOp(op, destination, keys);
            }
        });
    }

    public Long strLen(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.strLen(key);
            }
        });
    }

    public Boolean expire(final byte[] key, final int seconds) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expire(key, (long)seconds);
            }
        });
    }

    public Object execute(final String command, final byte[]... args) {
        return this.redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.execute(command, args);
            }
        });
    }

    public Boolean exists(final byte[] key) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.exists(key);
            }
        });
    }

    public Long del(final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.del(keys);
            }
        });
    }

    public DataType type(final byte[] key) {
        return (DataType)this.redisTemplate.execute(new RedisCallback() {
            public DataType doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.type(key);
            }
        });
    }

    public Set<byte[]> keys(final byte[] pattern) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.keys(pattern);
            }
        });
    }

    public Cursor<byte[]> scan(final ScanOptions options) {
        return (Cursor)this.redisTemplate.execute(new RedisCallback() {
            public Cursor<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scan(options);
            }
        });
    }

    public byte[] randomKey() {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.randomKey();
            }
        });
    }

    public void rename(final byte[] oldName, final byte[] newName) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.rename(oldName, newName);
                return null;
            }
        });
    }

    public Boolean renameNX(final byte[] oldName, final byte[] newName) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.renameNX(oldName, newName);
            }
        });
    }

    public Boolean expire(final byte[] key, final long seconds) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expire(key, seconds);
            }
        });
    }

    public Boolean pExpire(final byte[] key, final long millis) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pExpire(key, millis);
            }
        });
    }

    public Boolean expireAt(final byte[] key, final long unixTime) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expireAt(key, unixTime);
            }
        });
    }

    public Boolean pExpireAt(final byte[] key, final long unixTimeInMillis) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pExpireAt(key, unixTimeInMillis);
            }
        });
    }

    public Boolean persist(final byte[] key) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.persist(key);
            }
        });
    }

    public Boolean move(final byte[] key, final int dbIndex) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.move(key, dbIndex);
            }
        });
    }

    public Long ttl(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.ttl(key);
            }
        });
    }

    public Long pTtl(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pTtl(key);
            }
        });
    }

    public List<byte[]> sort(final byte[] key, final SortParameters params) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sort(key, params);
            }
        });
    }

    public Long sort(final byte[] key, final SortParameters params, final byte[] storeKey) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sort(key, params, storeKey);
            }
        });
    }

    public byte[] dump(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.dump(key);
            }
        });
    }

    public void restore(final byte[] key, final long ttlInMillis, final byte[] serializedValue) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.restore(key, ttlInMillis, serializedValue);
                return null;
            }
        });
    }

    public Long rPush(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPush(key, values);
            }
        });
    }

    public Long lPush(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPush(key, values);
            }
        });
    }

    public Long rPushX(final byte[] key, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPushX(key, value);
            }
        });
    }

    public Long lPushX(final byte[] key, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPushX(key, value);
            }
        });
    }

    public Long lLen(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lLen(key);
            }
        });
    }

    public List<byte[]> lRange(final byte[] key, final long begin, final long end) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lRange(key, begin, end);
            }
        });
    }

    public void lTrim(final byte[] key, final long begin, final long end) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.lTrim(key, begin, end);
                return null;
            }
        });
    }

    public byte[] lIndex(final byte[] key, final long index) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lIndex(key, index);
            }
        });
    }

    public Long lInsert(final byte[] key, final RedisListCommands.Position where, final byte[] pivot, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lInsert(key, where, pivot, value);
            }
        });
    }

    public void lSet(final byte[] key, final long index, final byte[] value) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.lSet(key, index, value);
                return null;
            }
        });
    }

    public Long lRem(final byte[] key, final long count, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lRem(key, count, value);
            }
        });
    }

    public byte[] lPop(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPop(key);
            }
        });
    }

    public byte[] rPop(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPop(key);
            }
        });
    }

    public List<byte[]> bLPop(final int timeout, final byte[]... keys) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bLPop(timeout, keys);
            }
        });
    }

    public List<byte[]> bRPop(final int timeout, final byte[]... keys) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bRPop(timeout, keys);
            }
        });
    }

    public byte[] rPopLPush(final byte[] srcKey, final byte[] dstKey) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPopLPush(srcKey, dstKey);
            }
        });
    }

    public byte[] bRPopLPush(final int timeout, final byte[] srcKey, final byte[] dstKey) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bRPopLPush(timeout, srcKey, dstKey);
            }
        });
    }

    public Long sAdd(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sAdd(key, values);
            }
        });
    }

    public Long sRem(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRem(key, values);
            }
        });
    }

    public byte[] sPop(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sPop(key);
            }
        });
    }

    public Boolean sMove(final byte[] srcKey, final byte[] destKey, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sMove(srcKey, destKey, value);
            }
        });
    }

    public Long sCard(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sCard(key);
            }
        });
    }

    public Boolean sIsMember(final byte[] key, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sIsMember(key, value);
            }
        });
    }

    public Set<byte[]> sInter(final byte[]... keys) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sInter(keys);
            }
        });
    }

    public Long sInterStore(final byte[] destKey, final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sInterStore(destKey, keys);
            }
        });
    }

    public Set<byte[]> sUnion(final byte[]... keys) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sUnion(keys);
            }
        });
    }

    public Long sUnionStore(final byte[] destKey, final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sUnionStore(destKey, keys);
            }
        });
    }

    public Set<byte[]> sDiff(final byte[]... keys) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sDiff(keys);
            }
        });
    }

    public Long sDiffStore(final byte[] destKey, final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sDiffStore(destKey, keys);
            }
        });
    }

    public Set<byte[]> sMembers(final byte[] key) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sMembers(key);
            }
        });
    }

    public byte[] sRandMember(final byte[] key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRandMember(key);
            }
        });
    }

    public List<byte[]> sRandMember(final byte[] key, final long count) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRandMember(key, count);
            }
        });
    }

    public Cursor<byte[]> sScan(final byte[] key, final ScanOptions options) {
        return (Cursor)this.redisTemplate.execute(new RedisCallback() {
            public Cursor<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sScan(key, options);
            }
        });
    }

    public Boolean zAdd(final byte[] key, final double score, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zAdd(key, score, value);
            }
        });
    }

    public Long zAdd(final byte[] key, final Set<RedisZSetCommands.Tuple> tuples) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zAdd(key, tuples);
            }
        });
    }

    public Long zRem(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRem(key, values);
            }
        });
    }

    public Double zIncrBy(final byte[] key, final double increment, final byte[] value) {
        return (Double)this.redisTemplate.execute(new RedisCallback() {
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zIncrBy(key, increment, value);
            }
        });
    }

    public Long zRank(final byte[] key, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRank(key, value);
            }
        });
    }

    public Long zRevRank(final byte[] key, final byte[] value) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRank(key, value);
            }
        });
    }

    public Set<byte[]> zRange(final byte[] key, final long begin, final long end) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRange(key, begin, end);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRangeWithScores(final byte[] key, final long begin, final long end) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeWithScores(key, begin, end);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final double min, final double max) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRangeByScoreWithScores(final byte[] key, final RedisZSetCommands.Range range) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, range);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRangeByScoreWithScores(final byte[] key, final double min, final double max) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, min, max);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final double min, final double max, final long offset, final long count) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max, offset, count);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRangeByScoreWithScores(final byte[] key, final double min, final double max, final long offset, final long count) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRangeByScoreWithScores(final byte[] key, final RedisZSetCommands.Range range, final RedisZSetCommands.Limit limit) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, range, limit);
            }
        });
    }

    public Set<byte[]> zRevRange(final byte[] key, final long begin, final long end) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRange(key, begin, end);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRevRangeWithScores(final byte[] key, final long begin, final long end) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeWithScores(key, begin, end);
            }
        });
    }

    public Set<byte[]> zRevRangeByScore(final byte[] key, final double min, final double max) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    public Set<byte[]> zRevRangeByScore(final byte[] key, final RedisZSetCommands.Range range) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, range);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRevRangeByScoreWithScores(final byte[] key, final double min, final double max) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, min, max);
            }
        });
    }

    public Set<byte[]> zRevRangeByScore(final byte[] key, final double min, final double max, final long offset, final long count) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, min, max, offset, count);
            }
        });
    }

    public Set<byte[]> zRevRangeByScore(final byte[] key, final RedisZSetCommands.Range range, final RedisZSetCommands.Limit limit) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, range, limit);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRevRangeByScoreWithScores(final byte[] key, final double min, final double max, final long offset, final long count) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRevRangeByScoreWithScores(final byte[] key, final RedisZSetCommands.Range range) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, range);
            }
        });
    }

    public Set<RedisZSetCommands.Tuple> zRevRangeByScoreWithScores(final byte[] key, final RedisZSetCommands.Range range, final RedisZSetCommands.Limit limit) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, range, limit);
            }
        });
    }

    public Long zCount(final byte[] key, final double min, final double max) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCount(key, min, max);
            }
        });
    }

    public Long zCount(final byte[] key, final RedisZSetCommands.Range range) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCount(key, range);
            }
        });
    }

    public Long zCard(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCard(key);
            }
        });
    }

    public Double zScore(final byte[] key, final byte[] value) {
        return (Double)this.redisTemplate.execute(new RedisCallback() {
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zScore(key, value);
            }
        });
    }

    public Long zRemRange(final byte[] key, final long begin, final long end) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRange(key, begin, end);
            }
        });
    }

    public Long zRemRangeByScore(final byte[] key, final double min, final double max) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRangeByScore(key, min, max);
            }
        });
    }

    public Long zRemRangeByScore(final byte[] key, final RedisZSetCommands.Range range) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRangeByScore(key, range);
            }
        });
    }

    public Long zUnionStore(final byte[] destKey, final byte[]... sets) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zUnionStore(destKey, sets);
            }
        });
    }

    public Long zUnionStore(final byte[] destKey, final RedisZSetCommands.Aggregate aggregate, final int[] weights, final byte[]... sets) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zUnionStore(destKey, aggregate, weights, sets);
            }
        });
    }

    public Long zInterStore(final byte[] destKey, final byte[]... sets) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zInterStore(destKey, sets);
            }
        });
    }

    public Long zInterStore(final byte[] destKey, final RedisZSetCommands.Aggregate aggregate, final int[] weights, final byte[]... sets) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zInterStore(destKey, aggregate, weights, sets);
            }
        });
    }

    public Cursor<RedisZSetCommands.Tuple> zScan(final byte[] key, final ScanOptions options) {
        return (Cursor)this.redisTemplate.execute(new RedisCallback() {
            public Cursor<RedisZSetCommands.Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zScan(key, options);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final String min, final String max) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final RedisZSetCommands.Range range) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, range);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final String min, final String max, final long offset, final long count) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max, offset, count);
            }
        });
    }

    public Set<byte[]> zRangeByScore(final byte[] key, final RedisZSetCommands.Range range, final RedisZSetCommands.Limit limit) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, range, limit);
            }
        });
    }

    public Set<byte[]> zRangeByLex(final byte[] key) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key);
            }
        });
    }

    public Set<byte[]> zRangeByLex(final byte[] key, final RedisZSetCommands.Range range) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key, range);
            }
        });
    }

    public Set<byte[]> zRangeByLex(final byte[] key, final RedisZSetCommands.Range range, final RedisZSetCommands.Limit limit) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key, range, limit);
            }
        });
    }

    public Boolean hSet(final byte[] key, final byte[] field, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hSet(key, field, value);
            }
        });
    }

    public Boolean hSetNX(final byte[] key, final byte[] field, final byte[] value) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hSetNX(key, field, value);
            }
        });
    }

    public byte[] hGet(final byte[] key, final byte[] field) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hGet(key, field);
            }
        });
    }

    public List<byte[]> hMGet(final byte[] key, final byte[]... fields) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hMGet(key, fields);
            }
        });
    }

    public void hMSet(final byte[] key, final Map<byte[], byte[]> hashes) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.hMSet(key, hashes);
                return null;
            }
        });
    }

    public Long hIncrBy(final byte[] key, final byte[] field, final long delta) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hIncrBy(key, field, delta);
            }
        });
    }

    public Double hIncrBy(final byte[] key, final byte[] field, final double delta) {
        return (Double)this.redisTemplate.execute(new RedisCallback() {
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hIncrBy(key, field, delta);
            }
        });
    }

    public Boolean hExists(final byte[] key, final byte[] field) {
        return (Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hExists(key, field);
            }
        });
    }

    public Long hDel(final byte[] key, final byte[]... fields) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hDel(key, fields);
            }
        });
    }

    public Long hLen(final byte[] key) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hLen(key);
            }
        });
    }

    public Set<byte[]> hKeys(final byte[] key) {
        return (Set)this.redisTemplate.execute(new RedisCallback() {
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hKeys(key);
            }
        });
    }

    public List<byte[]> hVals(final byte[] key) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hVals(key);
            }
        });
    }

    public Map<byte[], byte[]> hGetAll(final byte[] key) {
        return (Map)this.redisTemplate.execute(new RedisCallback() {
            public Map<byte[], byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hGetAll(key);
            }
        });
    }

    public Cursor<Map.Entry<byte[], byte[]>> hScan(final byte[] key, final ScanOptions options) {
        return (Cursor)this.redisTemplate.execute(new RedisCallback() {
            public Cursor<Map.Entry<byte[], byte[]>> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hScan(key, options);
            }
        });
    }

    public void multi() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.multi();
                return null;
            }
        });
    }

    public List<Object> exec() {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<Object> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.exec();
            }
        });
    }

    public void discard() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.discard();
                return null;
            }
        });
    }

    public void watch(final byte[]... keys) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.watch(keys);
                return null;
            }
        });
    }

    public void unwatch() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.unwatch();
                return null;
            }
        });
    }

    public boolean isSubscribed() {
        return ((Boolean)this.redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return Boolean.valueOf(redis.isSubscribed());
            }
        })).booleanValue();
    }

    public Subscription getSubscription() {
        return (Subscription)this.redisTemplate.execute(new RedisCallback() {
            public Subscription doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getSubscription();
            }
        });
    }

    public Long publish(final byte[] channel, final byte[] message) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.publish(channel, message);
            }
        });
    }

    public void subscribe(final MessageListener listener, final byte[]... channels) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.subscribe(listener, channels);
                return null;
            }
        });
    }

    public void pSubscribe(final MessageListener listener, final byte[]... patterns) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pSubscribe(listener, patterns);
                return null;
            }
        });
    }

    public void select(final int dbIndex) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.select(dbIndex);
                return null;
            }
        });
    }

    public byte[] echo(final byte[] message) {
        return (byte[])this.redisTemplate.execute(new RedisCallback() {
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.echo(message);
            }
        });
    }

    public String ping() {
        return (String)this.redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.ping();
            }
        });
    }

    /** @deprecated */
    @Deprecated
    public void bgWriteAof() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgWriteAof();
                return null;
            }
        });
    }

    public void bgReWriteAof() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgReWriteAof();
                return null;
            }
        });
    }

    public void bgSave() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgSave();
                return null;
            }
        });
    }

    public Long lastSave() {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lastSave();
            }
        });
    }

    public void save() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.save();
                return null;
            }
        });
    }

    public Long dbSize() {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.dbSize();
            }
        });
    }

    public void flushDb() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.flushDb();
                return null;
            }
        });
    }

    public void flushAll() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.flushAll();
                return null;
            }
        });
    }

    public Properties info() {
        return (Properties)this.redisTemplate.execute(new RedisCallback() {
            public Properties doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.info();
            }
        });
    }

    public Properties info(final String section) {
        return (Properties)this.redisTemplate.execute(new RedisCallback() {
            public Properties doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.info(section);
            }
        });
    }

    public void shutdown() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.shutdown();
                return null;
            }
        });
    }

    public void shutdown(final RedisServerCommands.ShutdownOption option) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.shutdown(option);
                return null;
            }
        });
    }

    public List<String> getConfig(final String pattern) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<String> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getConfig(pattern);
            }
        });
    }

    public void setConfig(final String param, final String value) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setConfig(param, value);
                return null;
            }
        });
    }

    public void resetConfigStats() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.resetConfigStats();
                return null;
            }
        });
    }

    public Long time() {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.time();
            }
        });
    }

    public void killClient(final String host, final int port) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.killClient(host, port);
                return null;
            }
        });
    }

    public void setClientName(final byte[] name) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setClientName(name);
                return null;
            }
        });
    }

    public String getClientName() {
        return (String)this.redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getClientName();
            }
        });
    }

    public List<RedisClientInfo> getClientList() {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<RedisClientInfo> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getClientList();
            }
        });
    }

    public void slaveOf(final String host, final int port) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.slaveOf(host, port);
                return null;
            }
        });
    }

    public void slaveOfNoOne() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.slaveOfNoOne();
                return null;
            }
        });
    }

    public void migrate(final byte[] key, final RedisNode target, final int dbIndex, final RedisServerCommands.MigrateOption option) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.migrate(key, target, dbIndex, option);
                return null;
            }
        });
    }

    public void migrate(final byte[] key, final RedisNode target, final int dbIndex, final RedisServerCommands.MigrateOption option, final long timeout) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.migrate(key, target, dbIndex, option, timeout);
                return null;
            }
        });
    }

    public void scriptFlush() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.scriptFlush();
                return null;
            }
        });
    }

    public void scriptKill() {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.scriptKill();
                return null;
            }
        });
    }

    public String scriptLoad(final byte[] script) {
        return (String)this.redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scriptLoad(script);
            }
        });
    }

    public List<Boolean> scriptExists(final String... scriptShas) {
        return (List)this.redisTemplate.execute(new RedisCallback() {
            public List<Boolean> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scriptExists(scriptShas);
            }
        });
    }

//    public <T> T eval(final byte[] script, final ReturnType returnType, final int numKeys, final byte[]... keysAndArgs) {
//        return this.redisTemplate.execute(new RedisCallback() {
//            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                return redis.eval(script, returnType, numKeys, keysAndArgs);
//            }
//        });
//    }
//
//    public <T> T evalSha(final String scriptSha, final ReturnType returnType, final int numKeys, final byte[]... keysAndArgs) {
//        return this.redisTemplate.execute(new RedisCallback() {
//            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                return redis.evalSha(scriptSha, returnType, numKeys, keysAndArgs);
//            }
//        });
//    }
//
//    public <T> T evalSha(final byte[] scriptSha, final ReturnType returnType, final int numKeys, final byte[]... keysAndArgs) {
//        return this.redisTemplate.execute(new RedisCallback() {
//            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                return redis.evalSha(scriptSha, returnType, numKeys, keysAndArgs);
//            }
//        });
//    }

    public Long pfAdd(final byte[] key, final byte[]... values) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pfAdd(key, values);
            }
        });
    }

    public Long pfCount(final byte[]... keys) {
        return (Long)this.redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pfCount(keys);
            }
        });
    }

    public void pfMerge(final byte[] destinationKey, final byte[]... sourceKeys) {
        this.redisTemplate.execute(new RedisCallback() {
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pfMerge(destinationKey, sourceKeys);
                return null;
            }
        });
    }


}

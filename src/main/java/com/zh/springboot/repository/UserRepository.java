package com.zh.springboot.repository;

import com.zh.springboot.entity.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户仓库
 * {@link User}
 * {@link Repository}
 */
@Repository
public class UserRepository {
    /**
     * 采用内存型的存储模型
     */
    private ConcurrentHashMap<Integer, User> repository = new ConcurrentHashMap<>();

    /**
     * id自增器
     */
    private final static AtomicInteger idGenerator = new AtomicInteger();


    /**
     * 保存用户对象
     * @param user {@link User} 对象
     * @return 保存成功返回<code>true</code>
     *         否则返回<code>false</code>
     */
    public boolean saveUser(User user) {
        boolean result = false;

        // ID从1开始
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        Object obj = repository.put(id, user);

        result = (obj == null);
        return result;
    }
}

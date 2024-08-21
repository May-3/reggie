package org.example.common;

/**
 * ClassName: BaseContext
 * Package: com.reggie.common
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/8 10:16
 * @Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

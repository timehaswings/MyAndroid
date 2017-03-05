// RemoteAidl.aidl
package com.weylen.remoteservicedemo;

// Declare any non-default types here with import statements

interface RemoteAidl {

    /**
    *  此方法是系统自动生成的一个方法，这个方法并有任何的实际意义，则是告诉aidl文件里面
    *  直接使用的类型，除了这些类型之外，其他任意的类型都需要引包，当使用自定义类型的时候
    *  还需要加上标志：in(客服端设置，需要链接服务的进程) out(服务端设置，提供服务的进程) inout(都可以设置)
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    /*
    * 设置名字
     */
    void setName(String name);

    /*
    * 获取名字
    */
    String getName();
}

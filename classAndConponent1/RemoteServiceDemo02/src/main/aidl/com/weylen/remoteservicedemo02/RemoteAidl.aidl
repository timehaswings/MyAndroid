// RemoteAidl.aidl
package com.weylen.remoteservicedemo02;

// Declare any non-default types here with import statements
import com.weylen.remoteservicedemo02.Person;

interface RemoteAidl {


    void setPerson(in Person p);
}

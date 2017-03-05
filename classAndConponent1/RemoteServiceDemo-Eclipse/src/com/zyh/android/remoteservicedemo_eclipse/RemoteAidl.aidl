// ÉùÃ÷°ü
package com.zyh.android.remoteservicedemo_eclipse;

import com.zyh.android.remoteservicedemo_eclipse.Person;

interface RemoteAidl{
	
	void setName(String name);
	
	String getName();
	
	void setPerson(in Person p);
}
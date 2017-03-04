/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Develop\\eclipse_space_0307\\RemoteServiceDemo-Eclipse\\src\\com\\zyh\\android\\remoteservicedemo_eclipse\\RemoteAidl.aidl
 */
package com.zyh.android.remoteservicedemo_eclipse;
public interface RemoteAidl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zyh.android.remoteservicedemo_eclipse.RemoteAidl
{
private static final java.lang.String DESCRIPTOR = "com.zyh.android.remoteservicedemo_eclipse.RemoteAidl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zyh.android.remoteservicedemo_eclipse.RemoteAidl interface,
 * generating a proxy if needed.
 */
public static com.zyh.android.remoteservicedemo_eclipse.RemoteAidl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zyh.android.remoteservicedemo_eclipse.RemoteAidl))) {
return ((com.zyh.android.remoteservicedemo_eclipse.RemoteAidl)iin);
}
return new com.zyh.android.remoteservicedemo_eclipse.RemoteAidl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_setName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setName(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setPerson:
{
data.enforceInterface(DESCRIPTOR);
com.zyh.android.remoteservicedemo_eclipse.Person _arg0;
if ((0!=data.readInt())) {
_arg0 = com.zyh.android.remoteservicedemo_eclipse.Person.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setPerson(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.zyh.android.remoteservicedemo_eclipse.RemoteAidl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void setName(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_setName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setPerson(com.zyh.android.remoteservicedemo_eclipse.Person p) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((p!=null)) {
_data.writeInt(1);
p.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPerson, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_setName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setPerson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void setName(java.lang.String name) throws android.os.RemoteException;
public java.lang.String getName() throws android.os.RemoteException;
public void setPerson(com.zyh.android.remoteservicedemo_eclipse.Person p) throws android.os.RemoteException;
}

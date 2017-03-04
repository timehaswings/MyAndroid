package com.weylen.contactsdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ContentProvider 实际上属于4大组建之一。 内容提供器
    // 主要功能是提供数据。
    // 1.系统应用提供数据给我们使用。(联系人，短信，音频，视频。。)
    // 2.自定义Provider将本应用里面的数据提供其他应用使用。这种操作在一般的app里面都不会用到。
    // ContentResolver 远程调用数据库操作对象，类似于SQLiteDatabase对象。

    // 查询是一个耗时的操作。所以都应该放在异步线程。

    // Uri 统一资源标示符 http content(表示的是ContentProvider专属的标示符) file表示文件标示符 文件的绝对地址
    // 表示的是一个资源的地址 file://mnt/sdcard/...
    // Url 统一资源定位符 http://localhost:80/file/path

    // Uri.withAppendedPath 在指定的uri后面追加一个路径
    // content://com.android.contacts/raw_contacts

    // 在6.0系统里面需要的权限都要手动申请。

    private static final int REQUEST_CONTACTS = 1;

    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    private ProgressDialog progressDialog;

    private ListView listView;
    private ContactsAdapter adapter;


    // 1.检查是否不拥有权限，如果没有权限则请求权限 用户返回请求结果：拒绝则不能进行操作，允许则可以操作。
    // 2.如果拥有权限，则直接操作。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ContactsAdapter(this, null);
        listView.setAdapter(adapter);
        // 列表的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 弹出PopupMenu
                showPopupMenu(view, position);
            }
        });

        // 音频的Uri
//        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    /**
     * 新增联系人的点击事件
     * @param view
     */
    public void insertContactsClick(View view){
        ContentResolver resolver = getContentResolver();
        // 1.需要现在raw_contacts表增加一条空的数据，得到这条数据的raw_contact_id值。
        ContentValues values = new ContentValues();
        // 返回的是新数据的id对应的uri值
        Uri newlyUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        Log.d("zhou", "insertContactsClick: 新数据的Uri:"+newlyUri);
        long rawContactsId = ContentUris.parseId(newlyUri);
        // 2.拿到id值之后再到data表插入对应的数据即可。
        // 插入姓名
        values.clear();
        values.put(ContactsContract.Data.DATA1, "傻逼1号"); // 姓名
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactsId);// rawContactId
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);
        // 插入电话号码
        values.clear();
        values.put(ContactsContract.Data.DATA1, "8888888888"); // 姓名
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactsId);// rawContactId
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);
    }

    /**
     * 弹出menu菜单
     */
    private void showPopupMenu(View anchorView,final int position){
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        deleteContacts(position);
                        break;
                    case R.id.update:
                        updateContact(position);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * 删除联系人操作
     * @param position
     */
    private void deleteContacts(int position){
        // 获取此条数据的rawContactId值
        int rawContactId = adapter.getData().get(position).getRawContactId();
        int deleteCount = getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
                ContactsContract.RawContacts._ID + " = ?",
                new String[]{String.valueOf(rawContactId)});
        if (deleteCount > 0){
            Toast.makeText(MainActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
            // 删除列表数据
            adapter.deleteData(position);
        }
    }

    /**
     * 更新联系人
     * @param position
     */
    public void updateContact(int position){
        ContactsBean contactsBean = adapter.getData().get(position);
        String newName = contactsBean.getName()+"-"+position;
        // 封装修改的数据
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.DATA1, newName);
        int updateNum = getContentResolver().update(ContactsContract.Data.CONTENT_URI, contentValues,
                ContactsContract.Data.MIMETYPE + " = ? and "+ ContactsContract.Data.RAW_CONTACT_ID
                    +" = ?", new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                String.valueOf(contactsBean.getRawContactId())});
        if (updateNum > 0){
            Toast.makeText(MainActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
            // 更新列表数据
            contactsBean.setName(newName);
            adapter.updateData(contactsBean, position);
        }
    }

    /**
     * 检查联系人权限
     */
    public void checkContactsPermission() {
        // 先检查app是否不拥有此权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            requestContactsPermissions();

        } else {
            // 查询数据
            new QueryContacts().start();
        }
    }

    public void queryContactsClick(View view){
        checkContactsPermission();
    }

    /**
     * 查询联系人线程
     */
    private class QueryContacts extends Thread{
        @Override
        public void run() {
            final List<ContactsBean> data = new ArrayList<>();

            ContentResolver resolver = MainActivity.this.getContentResolver();
            Uri rawContactsUri = ContactsContract.RawContacts.CONTENT_URI;
            // 1.查询的Uri 2.查询的字段 3.查询的条件 4.条件对应的值 5.排序
            Cursor curosr = resolver.query(rawContactsUri,
                            new String[]{ContactsContract.RawContacts._ID,
                                    ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                            null,
                            null,
                            null);
            int count = curosr != null ? curosr.getCount() : 0;
            while(curosr != null && curosr.moveToNext()){
                ContactsBean contactBean = new ContactsBean();

                String name = curosr.getString(curosr.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
                int rawContactId = curosr.getInt(0);
                contactBean.setName(name);
                contactBean.setRawContactId(rawContactId);

                Cursor phoneCursor = resolver.query(ContactsContract.Data.CONTENT_URI,
                        new String[]{ContactsContract.Data.DATA1},
                        ContactsContract.Data.RAW_CONTACT_ID + " = ? and "
                            + ContactsContract.Data.MIMETYPE +" = ?",
                        new String[]{String.valueOf(rawContactId),
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                        null);
                String phoneStr = "";
                while(phoneCursor != null && phoneCursor.moveToNext()){
                    phoneStr += phoneCursor.getString(0) +"\n";
                }
                if (phoneStr.endsWith("\n")){
                    phoneStr = phoneStr.substring(0, phoneStr.length() - 1);
                }
                contactBean.setPhone(phoneStr);
                data.add(contactBean);
                phoneCursor.close();
            }
            curosr.close();

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 更新数据
                    adapter.updateData(data);
                }
            });
        }
    }

    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        // 是否需要再次请求权限
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {
            Log.d("zhou", "requestContactsPermissions: true");
            // 请求权限
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_CONTACT,REQUEST_CONTACTS);
        } else {
            Log.d("zhou", "requestContactsPermissions: false");
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override // 请求的权限结果返回 1.请求的编号 2.请求的权限集合 3.结果
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            // 用来检查请求的权限是否被允许
            if (PermissionUtil.verifyPermissions(grantResults)) {
                Log.d("zhou", "onRequestPermissionsResult: 权限打开");
                new QueryContacts().start();
            } else {
                Log.d("zhou", "onRequestPermissionsResult: 权限被拒绝");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

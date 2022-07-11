package com.example.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.contact.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //view Binding
    private ActivityMainBinding binding;
    //TAG
    private static final String TAG = "CONTACT_TAG";
    //write contact permission request
    private static final int WRITE_CONTACT_PERMISSION_CODE = 100;
    //IMAGE PICK(gallery) instent constant
    private static final int IMAGE_PICK_GALLERY_CODE=200;
    //array of permission to request for write contact
    private String[] contactPermissions;

    private Uri image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init permission array,need only write contact permission
        contactPermissions = new String[]{Manifest.permission.WRITE_CONTACTS};

        binding.thumbnailIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryIntent();
            }
        });

        //fab save click to save the contact
        binding.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveContact();
                if(isWriteContactPermissionEnabled()){
                    //permission already enabled,save contact
                    saveContact();
                }
                else{
                    //permission not enabled, request
                    requestWriteContactPermission();
                }
            }
        });

    }

    private void saveContact() {
        String firstname = binding.FirstNameEt.getText().toString().trim();
        String lastname= binding.lastNameEt.getText().toString().trim();
        String phone = binding.PhoneMobileEt.getText().toString().trim();
        String email = binding.emailEt.getText().toString().trim();
        String address = binding.addressEt.getText().toString().trim();
        ArrayList<ContentProviderOperation> cpo = new ArrayList<>();

        //contact id
        int rawContactId = cpo.size();
        cpo.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,null)
                .build());

        //add firstname.last name
        cpo.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
                .withValue(ContactsContract.RawContacts.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstname)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,lastname)
                .build());

        //add phone number
        cpo.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
                .withValue(ContactsContract.RawContacts.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        //add email
        cpo.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
                .withValue(ContactsContract.RawContacts.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA,email)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());

        //add address
        cpo.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
                .withValue(ContactsContract.RawContacts.Data.MIMETYPE,ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.SipAddress.DATA,address)
                .withValue(ContactsContract.CommonDataKinds.SipAddress.TYPE, ContactsContract.CommonDataKinds.SipAddress.TYPE_WORK)
                .build());

        //get image ,convert image to bytes to store in contact
        byte[] imageBytes = imageUriToBytes();
        if(imageBytes!=null){
            //contact with image
            //add image
            cpo.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
                    .withValue(ContactsContract.RawContacts.Data.MIMETYPE,ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,imageBytes)
                    .build());

        }
        else{
            //contact without image
        }
        //save contact
        try {
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY,cpo);
            Log.d(TAG,"save contact: Saved..");
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,"save contact: "+e.getMessage());
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private byte[] imageUriToBytes() {
        Bitmap bitmap;
        ByteArrayOutputStream baos = null;
        try{
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image_uri);

            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
            return  baos.toByteArray();
        }
        catch (Exception e){
            Log.d(TAG,"imageUriToBytes: "+e.getMessage());
            return null;
        }
        //return baos.toByteArray();
    }

    private void openGalleryIntent() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean isWriteContactPermissionEnabled(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS) ==(PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestWriteContactPermission(){
        ActivityCompat.requestPermissions(this,contactPermissions,WRITE_CONTACT_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // handle permission results

        if(grantResults.length > 0){
            if(requestCode == WRITE_CONTACT_PERMISSION_CODE){
                boolean haveWriteContactPermission  = grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                if(haveWriteContactPermission){
                    //permission granted,save contacts
                    saveContact();
                }
                else{
                    //permission denied,cant save contact
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //handle result either image picked or not
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                //image picked
                image_uri = data.getData();
                //send to imageView
                binding.thumbnailIv.setImageURI(image_uri);
            }
        }
        else{
            //cancelled
            Toast.makeText(this, "Cancelled...", Toast.LENGTH_SHORT).show();
        }
    }
}
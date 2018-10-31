package com.mealapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.baseclass.BaseFragment;
import com.mealapp.container.BaseContainer;
import com.mealapp.databinding.FragmentProfileBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.MenuItemResp;
import com.mealapp.retrofit.model.UserDetail;
import com.mealapp.utility.CircularImageView;
import com.mealapp.utility.Utility;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends BaseFragment {
    //    FileCache fileCache;
    private static final int REQUEST_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    private FragmentProfileBinding mBinding;
    private TextInputEditText textInputEditText_uname, textInputEditText_pwd, textInputEditText_email, textInputEditText_olPwd, textInputEditText_newPwd, textInputEditText_retypePwd, textInputEditText_address;
    private ImageView imageview_closeDialog;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Uri picUri, imageUri;
    private String path;
    private String filePath;
    private File photoFile;
    private Integer id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Profile");
        ((MainActivity) getActivity()).setIconBack();
        ((MainActivity) getActivity()).setMenuActionBar("ProfileMenu");
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        getUserData();
        return mBinding.getRoot();
    }

    private void getUserData() {
        apiService.getUserDetail(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {

                                    Utility.loadImage(response.body().getImageUrl(), mBinding.imgProfilePicProfileFrg, R.drawable.placeholder);
                                    mBinding.edtProfileFirstName.setText(response.body().getFirstName());
                                    mBinding.edtProfileLastName.setText(response.body().getLastName());
                                    mBinding.edtProfileEmail.setText(response.body().getEmail());
                                    id = response.body().getId();
                                }
                            } else if (response.code() == 401) {
                                Utility.toast(getActivity(), "Unauthorized");
                            } else if (response.code() == 403) {
                                Utility.toast(getActivity(), "Forbidden");
                            } else if (response.code() == 404) {
                                Utility.toast(getActivity(), "Not Found");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @OnClick(R.id.btn_my_address)
    public void onClickMyAddress() {
        ManageAddressFragment nextFrag = new ManageAddressFragment();
        Bundle args = new Bundle();
        if (id != null)
            args.putInt("id", id);
        nextFrag.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    @OnClick(R.id.btn_update_user)
    public void onClickUserUpdate() {
        final Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("activated", true);
        requestBodyMap.put("id", id);
        requestBodyMap.put("authorities", new String[]{"ROLE_USER"});
        requestBodyMap.put("email", mBinding.edtProfileEmail.getText().toString());
        requestBodyMap.put("firstName", mBinding.edtProfileFirstName.getText().toString());
        requestBodyMap.put("lastName", mBinding.edtProfileLastName.getText().toString());
        requestBodyMap.put("login", mBinding.edtProfileEmail.getText().toString());


        apiService.getUpdateUser(sharedPreferences.getString(ConstantData.TOKEN, ""), requestBodyMap)
                .enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {

                                    Utility.loadImage(response.body().getImageUrl(), mBinding.imgProfilePicProfileFrg, R.drawable.placeholder);
                                    mBinding.edtProfileFirstName.setText(response.body().getFirstName());
                                    mBinding.edtProfileLastName.setText(response.body().getLastName());
                                    mBinding.edtProfileEmail.setText(response.body().getEmail());
                                }
                            }
                        } else if (response.code() == 401) {
                            Utility.toast(getActivity(), "Unauthorized");
                        } else if (response.code() == 403) {
                            Utility.toast(getActivity(), "Forbidden");
                        } else if (response.code() == 404) {
                            Utility.toast(getActivity(), "Not Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.btn_change_password)
    public void onClickChangePassword() {
        changePassword();
    }

    @OnClick(R.id.imgView_changeProfile)
    public void onClickTakeImage() {
        selectImage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setActionBarTitle("Menu");
        ((MainActivity) getActivity()).setMenuActionBar("HomeMenu");
        ((MainActivity) getActivity()).setIconNavi();
    }

    private void changePassword() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_change_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        final AlertDialog alertbox = dialogBuilder.create();

        Button button = dialogView.findViewById(R.id.buttn_chngcard);

        textInputEditText_newPwd = dialogView.findViewById(R.id.newpwd_textInputEdittext);
        textInputEditText_olPwd = dialogView.findViewById(R.id.oldpwd_textInputEdittext);
        textInputEditText_retypePwd = dialogView.findViewById(R.id.retypepwd_textInputEdittext);
        imageview_closeDialog = dialogView.findViewById(R.id.close_dialog);

        imageview_closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertbox.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEditText_olPwd.getText().length() == 0)
                    textInputEditText_olPwd.setError("Enter old password");
                else if (textInputEditText_newPwd.getText().length() == 0)
                    textInputEditText_newPwd.setError("Enter new password");
                else if (textInputEditText_newPwd.getText().length() < 6)
                    textInputEditText_newPwd.setError("Password length minimum 6 character");
                else if (textInputEditText_retypePwd.getText().length() == 0)
                    textInputEditText_retypePwd.setError("Enter re-type password");
                else if (!textInputEditText_newPwd.getText().toString().equals(textInputEditText_retypePwd.getText().toString())) {
                    textInputEditText_retypePwd.getText().clear();
                    textInputEditText_retypePwd.setError("New password and re-type password are not match");
                } else {

//                    progressDialog.setMessage(getString(R.string.please_wait));
//                    progressDialog.show();

//                    System.out.println("rawdata " + textInputEditText_olPwd.getText().toString() + " " + textInputEditText_newPwd.getText().toString() + " " + user_id);
                    final Map<String, String> requestBodyMap = new HashMap<>();
//            requestBodyMap.put("authorities", String.valueOf(new String[]{"ROLE_VENDOR"}));

                    requestBodyMap.put("currentPassword", textInputEditText_olPwd.getText().toString().trim());
                    requestBodyMap.put("newPassword", textInputEditText_newPwd.getText().toString().trim());
                    apiService.changePassword(sharedPreferences.getString(ConstantData.TOKEN, ""), requestBodyMap)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
//                            progressDialog.dismiss();
                                    if (response.isSuccessful()) {
                                        if (response.code() == 200) {
                                            alertbox.dismiss();
                                            Toast.makeText(getActivity(), "Password change successfully", Toast.LENGTH_SHORT).show();
                                        } else if (response.code() == 401) {
                                            Utility.toast(getActivity(), "Unauthorized");
                                        } else if (response.code() == 403) {
                                            Utility.toast(getActivity(), "Forbidden");
                                        } else if (response.code() == 404) {
                                            Utility.toast(getActivity(), "Not Found");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
//                            progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        alertbox.show();
    }

    private void selectImage() {

        final String[] items = {"Camera", "Gallery"};
        new CircleDialog.Builder(getActivity())
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle("Select Image")
                .setTitleColor(getResources().getColor(R.color.colorPrimary))
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {

                        if (position == 0) {
                            String fileName = "Camera_Example.jpg";
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, fileName);
                            values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

                            picUri = getActivity().getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            startActivityForResult(intent, REQUEST_CAPTURE);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_GALLERY);
                        }
                    }
                })
                .setNegative("Cancel", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = getResources().getColor(R.color.colorPrimaryDark);
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {

            try {

                picUri = data.getData();
                filePath = getPath(getActivity(), picUri);

                photoFile = saveBitmapToFile(new File(filePath));
                path = photoFile.getPath();

                mBinding.imgProfilePicProfileFrg.setImageURI(picUri);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {

            String imageId = convertImageUriToFile(picUri, getActivity());

//                photoFile = new File(picUri.getPath());

            photoFile = new File(path);


            new LoadImagesFromSDCard().execute("" + imageId);


        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Camera picture was not taken", Toast.LENGTH_SHORT).show();
        } else { // Result was a failure
            Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPath(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public File saveBitmapToFile(File file) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 25;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;

        } catch (Exception e) {
            return null;
        }
    }

    public String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);


            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {
                Log.d("TAG", "No image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/
                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    path = cursor.getString(file_ColumnIndex);
                    Log.d("path", path);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + path + "\n";

                }
            }
            if (imageUri != null) {
                cursor = getActivity().managedQuery(imageUri, proj, null, null, null);
            }
            if ((cursor != null) && (cursor.moveToLast())) {
                ContentResolver cr = getActivity().getContentResolver();
                int i = cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BaseColumns._ID + "/" + cursor.getString(3), null);
                Log.d("This", "Number of column deleted : " + i);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            return "" + imageID;
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        Bitmap mBitmap;
        private ProgressDialog Dialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {


                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {

                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (mBitmap != null) {
                mBinding.imgProfilePicProfileFrg.setImageURI(picUri);
            } else {
                Toast.makeText(getActivity(), "picture was not taken", Toast.LENGTH_SHORT).show();
            }

        }

    }
}

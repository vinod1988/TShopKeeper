package com.hy.shopkeeper.common;

public interface AsyncActivity {

    public void showLoadingProgressDialog();

    public void showProgressDialog(CharSequence message);

    public void dismissProgressDialog();
    
    public void displayDialogError(CharSequence message);

}

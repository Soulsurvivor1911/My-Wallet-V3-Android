package piuk.blockchain.android.ui.account;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;

import info.blockchain.wallet.payload.PayloadManager;

import io.reactivex.Observable;
import piuk.blockchain.android.R;
import piuk.blockchain.android.data.rxjava.RxUtil;
import piuk.blockchain.android.ui.customviews.MaterialProgressDialog;
import piuk.blockchain.android.ui.customviews.ToastCustom;
import piuk.blockchain.android.util.ViewUtils;

public class SecondPasswordHandler {

    private Context context;
    private PayloadManager payloadManager;
    private MaterialProgressDialog materialProgressDialog;

    public SecondPasswordHandler(Context context) {
        this.context = context;
        payloadManager = PayloadManager.getInstance();
    }

    public interface ResultListener {
        void onNoSecondPassword();

        void onSecondPasswordValidated(String validateSecondPassword);
    }

    public void validate(final ResultListener listener) {

        if (!payloadManager.getPayload().isDoubleEncrypted()) {
            listener.onNoSecondPassword();
        } else {

            final AppCompatEditText passwordField = new AppCompatEditText(context);
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            new AlertDialog.Builder(context, R.style.AlertDialogStyle)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.enter_double_encryption_pw)
                    .setView(ViewUtils.getAlertDialogEditTextLayout(context, passwordField))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {

                        String secondPassword = passwordField.getText().toString();

                        if (secondPassword.length() > 0) {
                            showProgressDialog(R.string.validating_password);
                            validateSecondPassword(secondPassword)
                                    .compose(RxUtil.applySchedulersToObservable())
                                    .doAfterTerminate(this::dismissProgressDialog)
                                    .subscribe(success -> {
                                        if (success) {
                                            listener.onSecondPasswordValidated(secondPassword);
                                        } else {
                                            showErrorToast();
                                        }
                                    }, throwable -> showErrorToast());
                        } else {
                            showErrorToast();
                        }
                    }).setNegativeButton(android.R.string.cancel, null).show();
        }
    }

    private void showErrorToast() {
        ToastCustom.makeText(
                context,
                context.getString(R.string.double_encryption_password_error),
                ToastCustom.LENGTH_SHORT,
                ToastCustom.TYPE_ERROR);
    }

    private Observable<Boolean> validateSecondPassword(String password) {
        return Observable.fromCallable(() -> payloadManager.validateSecondPassword(password));
    }

    public void showProgressDialog(@StringRes int messageId) {
        dismissProgressDialog();
        materialProgressDialog = new MaterialProgressDialog(context);
        materialProgressDialog.setCancelable(false);
        materialProgressDialog.setMessage(context.getString(messageId));
        materialProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (materialProgressDialog != null && materialProgressDialog.isShowing()) {
            materialProgressDialog.dismiss();
            materialProgressDialog = null;
        }
    }
}

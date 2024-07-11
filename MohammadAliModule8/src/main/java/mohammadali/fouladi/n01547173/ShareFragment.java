package mohammadali.fouladi.n01547173;
// MohammadAli Fouladi N01547173

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SHARED_PREFS_NAME = "profile_prefs";
    private static final String KEY_CHECKBOX = "key_checkbox";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_ID = "key_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CheckBox checkBox;
    private EditText emailEditText;
    private EditText idEditText;

    public ShareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareFragment newInstance(String param1, String param2) {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAlertDialog();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);


        checkBox = view.findViewById(R.id.Moecheckbox);
        emailEditText = view.findViewById(R.id.MoeemailEditText);
        idEditText = view.findViewById(R.id.MoeidEditText);
        ImageButton imageButton = view.findViewById(R.id.MoeimageButton);

        imageButton.setOnClickListener(v -> validate());
        return view;
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setTitle(getString(R.string.Fullname));

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a z", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        builder.setMessage(getString(R.string.current_date_and_time) + currentTime);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        builder.create().show();
    }
    private void validate() {
        String email = emailEditText.getText().toString();
        String id = idEditText.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.invalid_email));
            emailEditText.requestFocus();
            return;
        }

        if (id.length() < 6 || !TextUtils.isDigitsOnly(id)) {
            idEditText.setError(getString(R.string.id_must_be_at_least_6_digits));
            idEditText.requestFocus();
            return;
        }
        boolean isChecked = checkBox.isChecked();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_CHECKBOX, isChecked);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ID, id);
        editor.apply();

        Snackbar.make(getView(), getString(R.string.checkbox)+":"+  isChecked+ "\n"+ getString(R.string.emailshow) + email + getString(R.string.idshow) + id, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.dismiss, v -> {
                    checkBox.setChecked(false);

                    emailEditText.setText("");
                    idEditText.setText("");
                }).show();
    }
}
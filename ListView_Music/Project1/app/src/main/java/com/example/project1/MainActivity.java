package com.example.project1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.text.InputType;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm Button theo ID
        EditText passwordEditText = findViewById(R.id.ed_pass);
        EditText nameEditText     = findViewById(R.id.ed_ten);
        ImageButton showPass = findViewById(R.id.imageButton);
        Button registerButton = findViewById(R.id.btn_dk);
        //  Đặt kiểu nhập mặc định là dấu chấm
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showPass.setOnClickListener(j -> {
            if (isPasswordVisible) {
                // Nếu mật khẩu đang hiển thị -> Ẩn đi (hiển thị dấu chấm "•")
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showPass.setImageResource(R.drawable.password_eye);
            } else {
                // Nếu mật khẩu đang ẩn -> Hiển thị bình thường
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showPass.setImageResource(R.drawable.password_eye);
            }
            // Đặt con trỏ về cuối văn bản
            passwordEditText.setSelection(passwordEditText.getText().length());
            isPasswordVisible = !isPasswordVisible; // Đảo trạng thái
        });

        // Sự kiện khi nhấn vào Button
        registerButton.setOnClickListener( v -> {
                  String  password = passwordEditText.getText().toString().trim();
                  String  name     = nameEditText.getText().toString().trim();

                 // dieu kien nhap ten dang nhap
                if (name.isEmpty()){
                    Toast.makeText(this,"Vui lòng nhập tên đăng nhập trước khi đăng ký!",Toast.LENGTH_SHORT).show();
                }
                else if (name.length() < 6 || name.length() > 16) {
                    Toast.makeText(this, "Tên đăng nhập phải có từ 6 đến 16 ký tự!", Toast.LENGTH_SHORT).show();
               }
                else {
                    // Tiến hành đăng ký
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }

                 // dieu kien nhap password
                if (password.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập mật khẩu trước khi đăng ký!", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 8 || password.length() > 16) {
                    Toast.makeText(this, "Mật khẩu phải có từ 8 đến 16 ký tự!", Toast.LENGTH_SHORT).show();
                }
                else {
                        // Tiến hành đăng ký
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }



                }
        );
    }
}

package com.example.smsintentapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;  // Import untuk logging
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101; // Kode untuk izin SMS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Memeriksa dan meminta izin untuk mengirim SMS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }

        // Inisialisasi tombol
        Button btnSendSms = findViewById(R.id.btnSendSms);
        btnSendSms.setOnClickListener(v -> {
            String phoneNumber = "1234567890";  // Ganti dengan nomor telepon tujuan
            String message = "Halo, ini adalah pesan dari aplikasi Android.";  // Pesan yang ingin dikirim

            // Intent untuk mengirim SMS
            Intent smsIntent = new Intent(Intent.ACTION_SEND);
            smsIntent.putExtra("sms_body", message);
            smsIntent.putExtra("address", phoneNumber);
            smsIntent.setType("vnd.android-dir/mms-sms");

            // Menampilkan log untuk debugging
            Log.d("SmsIntent", "Intent untuk mengirim SMS: " + smsIntent.toString());

            // Mengecek apakah ada aplikasi yang dapat mengirim SMS
            if (smsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(smsIntent);
            } else {
                // Log jika tidak ada aplikasi yang dapat mengirim SMS
                Log.d("SmsIntent", "Tidak ada aplikasi yang tersedia untuk mengirim SMS.");
                // Menampilkan pesan toast jika tidak ada aplikasi yang tersedia
                Toast.makeText(this, "Tidak ada aplikasi yang tersedia untuk mengirim SMS.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin SMS diberikan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Izin SMS ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

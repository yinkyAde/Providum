package com.israel.providum;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    boolean doubleBackPressedOnce = false;
    String decryptedValue;
    String decryptedBreakline;
    String decryptedBreakone;
    String decryptedBreaktwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scanning");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null && result.getContents() != null) {
            final String scannedResult = result.getContents();
            System.out.println(scannedResult);
            decrypt(scannedResult);
            Intent intent = new Intent(MainActivity.this, DocumentScreen.class);
            intent.putExtra("SCANNED RESULT", decryptedBreaktwo);
            System.out.println(decryptedBreaktwo);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Document Scanned", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Document not Scanned", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String decrypt(String scannedResult) {
        Map<Character, Character> replacements = new HashMap<>();
        replacements.put('a', '1');
        replacements.put('b', '2');
        replacements.put('c', '3');
        replacements.put('d', '4');
        replacements.put('e', '5');
        replacements.put('f', '6');
        replacements.put('g', '7');
        replacements.put('h', '8');
        replacements.put('i', '9');
        replacements.put('j', '0');
        replacements.put('k', '!');
        replacements.put('l', '`');
        replacements.put('m', '^');
        replacements.put('n', '(');
        replacements.put('o', ')');
        replacements.put('p', '*');
        replacements.put('q', '+');
        replacements.put('r', '-');
        replacements.put('s', '/');
        replacements.put('t', '=');
        replacements.put('u', '<');
        replacements.put('v', '>');
        replacements.put('w', '~');
        replacements.put('x', '?');
        replacements.put('y', '$');
        replacements.put('z', ';');
        replacements.put(' ', '_');
        // other way
        replacements.put('1', 'a');
        replacements.put('2', 'b');
        replacements.put('3', 'c');
        replacements.put('4', 'd');
        replacements.put('5', 'e');
        replacements.put('6', 'f');
        replacements.put('7', 'g');
        replacements.put('8', 'h');
        replacements.put('9', 'i');
        replacements.put('0', 'j');
        replacements.put('!', 'k');
        replacements.put('`', 'l');
        replacements.put('^', 'm');
        replacements.put('(', 'n');
        replacements.put(')', 'o');
        replacements.put('*', 'p');
        replacements.put('+', 'q');
        replacements.put('-', 'r');
        replacements.put('/', 's');
        replacements.put('=', 't');
        replacements.put('<', 'u');
        replacements.put('>', 'v');
        replacements.put('~', 'w');
        replacements.put('?', 'x');
        replacements.put('$', 'y');
        replacements.put(';', 'z');
        replacements.put('_', ' ');

        StringBuilder output = new StringBuilder();
        for (Character c : scannedResult.toCharArray()) {
            output.append(replacements.getOrDefault(c, c));
            decryptedValue = String.valueOf(output);
            decryptedBreakline = decryptedValue.replaceAll(",", "\n");
            decryptedBreakone = decryptedBreakline.replaceAll("\\{", "\n");
            decryptedBreaktwo = decryptedBreakone.replaceAll("\\}", "\n");
        }
        //System.out.println(output.toString());
        return scannedResult;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, Dashboard.class));
        finish();
    }
}
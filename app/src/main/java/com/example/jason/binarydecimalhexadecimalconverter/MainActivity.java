package com.example.jason.binarydecimalhexadecimalconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText dec;
    private EditText bin;
    private EditText hex;
    private Button convert;


    static String DecimalToBinary(String s) {
        int x=Integer.parseInt(s);
        int nums = (int) (Math.log(x) / Math.log(2));
        String binary = "";
        if (x == 0)
            return "0";
        while (nums + 1 > 0) {
            if (x - Math.pow(2, nums) >= 0) {
                binary += "1";
                x -= Math.pow(2, nums);
            } else
                binary += "0";
            nums--;
        }
        int y=binary.length()%4;
        if (y!=0)
            for(int i=0;i<4-y;i++)
                binary="0"+binary;
        return binary;
    }
    static String DecimalToHexadecimal(String s){
        return BinaryToHexadecimal(DecimalToBinary(s));
    }

    static String BinaryToDecimal(String x) {
        int num = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == '1')
                num += Math.pow(2, x.length() - i - 1);
        }
        return Integer.toString(num);
    }
    static String BinaryToHexadecimal(String x){
        String s="";
        while(x.length()>4){
            String part=BinaryToDecimal(x.substring(x.length()-4,x.length()));
            if(Integer.parseInt(part)>9){
                s=(char)('A'+Integer.parseInt(part)-10)+s;
            }
            else{
                s=part+s;
            }
            x=x.substring(0,x.length()-4);
        }
        int x2=Integer.parseInt(BinaryToDecimal(x));
        if (x2!=0) {
            if (x2 > 9) {
                s += (char) ('A' + x2 - 10);
            } else {
                s = BinaryToDecimal(x) + s;
            }
        }
        return s;
    }
    static String HexadecimalToBinary(String x) {
        x = x.toUpperCase();
        String s = "";
        for (int i = 0; i < x.length(); i++) {
            if ("1234567890".indexOf(x.charAt(i)) > -1) {
                s += DecimalToBinary(""+x.charAt(i));
            } else {
                s += DecimalToBinary(""+(int) (x.charAt(i) - 'A' + 10));
            }
        }
        return s;
    }
    static String HexadecimalToDecimal(String s){
        return BinaryToDecimal(HexadecimalToBinary(s));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dec = (EditText) findViewById(R.id.dec);
        bin = (EditText) findViewById(R.id.bin);
        hex = (EditText) findViewById(R.id.hex);
        convert= (Button) findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((dec.getText().length()>0 && bin.getText().length()==0 && hex.getText().length()==0)||
                        (dec.getText().length()==0 && bin.getText().length()>0 && hex.getText().length()==0)||
                        (dec.getText().length()==0 && bin.getText().length()==0 && hex.getText().length()>0))) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please only input in one box.", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    if (dec.getText().length() > 0) {
                        String decnum=dec.getText().toString();
                        bin.setText(DecimalToBinary(decnum));
                        hex.setText(DecimalToHexadecimal(decnum));
                    }
                    else if (bin.getText().length() > 0) {
                        boolean tf=true;
                        for (int i = 0; i < bin.getText().length(); i++) {
                            if (bin.getText().charAt(i) != '1' && bin.getText().charAt(i) != '0') {
                                Toast toast = Toast.makeText(MainActivity.this, "Invalid Binary Input. Please input only 1 and 0", Toast.LENGTH_LONG);
                                toast.show();
                                tf=false;
                            }
                        }
                        if (tf) {
                            String binnum = bin.getText().toString();
                            dec.setText(BinaryToDecimal(binnum));
                            hex.setText(BinaryToHexadecimal(binnum));
                        }

                    }
                    else {
                        boolean tf=true;
                        for (int i = 0; i < hex.getText().length(); i++) {
                            if ("1234567890ABCDEFabcdefABCDEF".indexOf(hex.getText().charAt(i)) == -1) {
                                Toast toast = Toast.makeText(MainActivity.this, "Invalid Hexadecimal Input.", Toast.LENGTH_LONG);
                                toast.show();
                                tf=false;
                            }

                        }
                        if (tf) {
                            String hexnum = hex.getText().toString();
                            dec.setText(HexadecimalToDecimal(hexnum));
                            bin.setText(HexadecimalToBinary(hexnum));
                        }
                    }
                }
            }
        });
    }
}

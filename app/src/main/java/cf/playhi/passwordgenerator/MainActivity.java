package cf.playhi.passwordgenerator;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText rootKeyEditText = findViewById(R.id.rootKey_editText);
        final EditText keywordEditText = findViewById(R.id.keyword_editText);
        final EditText passwordEditText = findViewById(R.id.password_editText);
        Button goButton = findViewById(R.id.go_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordEditText.setText(R.string.plsWait);
                try {
                    MessageDigest rootKey = MessageDigest.getInstance("SHA-256");
                    rootKey.update(Base64.encode(rootKeyEditText.getText().toString().getBytes(),Base64.DEFAULT));
                    String rootKeyS = String.format("%032X", new BigInteger(1, rootKey.digest()));
                    MessageDigest keyword = MessageDigest.getInstance("MD5");
                    keyword.update(Base64.encode(keywordEditText.getText().toString().getBytes(),Base64.DEFAULT));
                    String keywordS = String.format("%032X", new BigInteger(1, keyword.digest()));
                    MessageDigest password = MessageDigest.getInstance("SHA-256");
                    password.update(Base64.encode((keywordS + rootKeyS).getBytes(),Base64.DEFAULT));
                    String passwordS = String.format("%032X", new BigInteger(1, password.digest()));
                    String result = "";
                    for (int j = 0; j < passwordS.length(); j++) {
                        if ((j&1)!=0){
                            result = result.concat(passwordS.substring(j,j+1).toLowerCase());
                        } else {
                            result = result.concat(passwordS.substring(j,j+1).toUpperCase());
                        }
                    }
                    MessageDigest specialCharacter = MessageDigest.getInstance("MD5");
                    specialCharacter.update(result.substring(result.length()-5,result.length()).getBytes());
                    String specialCharacterS = String.format("%032X", new BigInteger(1, specialCharacter.digest())).toLowerCase();
                    switch (specialCharacterS.substring(2,3)){
                        case "0":
                            result = result.substring(1,12) + "@";
                            break;
                        case "1":
                            result = result.substring(1,12) + ")";
                            break;
                        case "2":
                            result = result.substring(1,12) + "(";
                            break;
                        case "3":
                            result = result.substring(1,12) + ";";
                            break;
                        case "4":
                            result = result.substring(1,12) + "@";
                            break;
                        case "5":
                            result = result.substring(1,12) + "#";
                            break;
                        case "6":
                            result = result.substring(1,12) + "*";
                            break;
                        case "7":
                            result = result.substring(1,12) + ">";
                            break;
                        case "8":
                            result = result.substring(1,12) + "]";
                            break;
                        case "9":
                            result = result.substring(1,12) + "=";
                            break;
                        case "a":
                            result = result.substring(1,12) + "_";
                            break;
                        case "b":
                            result = result.substring(1,12) + "%";
                            break;
                        case "c":
                            result = result.substring(1,12) + "@";
                            break;
                        case "d":
                            result = result.substring(1,12) + "}";
                            break;
                        case "e":
                            result = result.substring(1,12) + "{";
                            break;
                        case "f":
                            result = result.substring(1,12) + "]";
                            break;
                        case "g":
                            result = result.substring(1,12) + "[";
                            break;
                        case "h":
                            result = result.substring(1,12) + "'";
                            break;
                        case "i":
                            result = result.substring(1,12) + "\"";
                            break;
                        case "j":
                            result = result.substring(1,12) + ";";
                            break;
                        case "k":
                            result = result.substring(1,12) + ":";
                            break;
                        case "l":
                            result = result.substring(1,12) + ">";
                            break;
                        case "m":
                            result = result.substring(1,12) + "<";
                            break;
                        case "n":
                            result = result.substring(1,12) + "!";
                            break;
                        case "o":
                            result = result.substring(1,12) + "`";
                            break;
                        case "p":
                            result = result.substring(1,12) + "~";
                            break;
                        case "q":
                            result = result.substring(1,12) + "=";
                            break;
                        case "r":
                            result = result.substring(1,12) + "+";
                            break;
                        case "s":
                            result = result.substring(1,12) + ")";
                            break;
                        case "t":
                            result = result.substring(1,12) + "(";
                            break;
                        case "u":
                            result = result.substring(1,12) + "*";
                            break;
                        case "v":
                            result = result.substring(1,12) + "&";
                            break;
                        case "w":
                            result = result.substring(1,12) + "^";
                            break;
                        case "x":
                            result = result.substring(1,12) + "%";
                            break;
                        case "y":
                            result = result.substring(1,12) + "#";
                            break;
                        case "z":
                            result = result.substring(1,12) + "?";
                            break;
                        default:
                            result = result.substring(1,12) + "-";
                            break;
                    }
                    passwordEditText.setText(result);
                } catch (Exception e){
                    e.printStackTrace();
                    String errorInfo = getResources().getString(R.string.error) + ":" + e.getLocalizedMessage();
                    passwordEditText.setText(errorInfo);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                final EditText rootKeyEditText = findViewById(R.id.rootKey_editText);
                final EditText keywordEditText = findViewById(R.id.keyword_editText);
                rootKeyEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                keywordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}

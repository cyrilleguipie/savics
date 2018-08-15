package com.savics.savics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.savics.savics.adapter.UserListAdapter;
import com.savics.savics.model.User;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private TextInputEditText edit_firstname, edit_lastname, edit_age;
  private RadioGroup rg_sex, rg_diabete;
  private RadioButton radio_male, radio_female, radio_yes, radio_no, radio_unknown;
  private Spinner spinCity, spinCountry;
  private RecyclerView recycler;
  private Button btn_save;
  private CheckBox check_minor;
  UserListAdapter adapter;
  private List<User> users = new ArrayList<>();
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    edit_age = findViewById(R.id.edit_age);
    edit_firstname = findViewById(R.id.edit_firstname);
    edit_lastname = findViewById(R.id.edit_lastname);
    rg_diabete = findViewById(R.id.rg_diabete);
    rg_sex = findViewById(R.id.rg_sex);
    radio_female = findViewById(R.id.radio_female);
    radio_male = findViewById(R.id.radio_male);
    radio_no = findViewById(R.id.radio_no);
    radio_unknown = findViewById(R.id.radio_unknown);
    radio_yes = findViewById(R.id.radio_yes);
    spinCity = findViewById(R.id.spin_city);
    spinCountry = findViewById(R.id.spin_country);
    recycler = findViewById(R.id.recycler);
    btn_save = findViewById(R.id.btn_save);
    check_minor = findViewById(R.id.check_minor);

    recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext()));
    users = User.findAllOrderedByTitle();
    adapter = new UserListAdapter(MainActivity.this, users);
    recycler.setAdapter(adapter);

    check_minor.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        //is chkIos checked?
        if (((CheckBox) v).isChecked()) {
            users.clear();
            users.addAll(User.findMinors());
            adapter.notifyDataSetChanged();

        }else{
          users.clear();
          users.addAll(User.findAllOrderedByTitle());
          adapter.notifyDataSetChanged();
        }

      }
    });

    btn_save.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String firstname = edit_firstname.getText().toString();
        String lastname = edit_lastname.getText().toString();
        String age = edit_age.getText().toString();
        String country = spinCountry.getSelectedItem().toString();
        String city = spinCity.getSelectedItem().toString();
         rg_diabete.getCheckedRadioButtonId();

        String diabete = "";
        int diabeteSelected = rg_diabete.getCheckedRadioButtonId();
        if(diabeteSelected == R.id.radio_yes){
          diabete = "Yes";
        }else if(diabeteSelected == R.id.radio_no){
          diabete = "No";
        }else{
          diabete = "Unknown";
        }

        String sex = "";
        int sexSelected = rg_sex.getCheckedRadioButtonId();
        if(sexSelected == R.id.radio_male){
          sex = "Male";
        }else{
          sex = "Female";
        }

        if(firstname.isEmpty() || lastname.isEmpty() || age.isEmpty() || country.isEmpty()
            || city.isEmpty() || diabete.isEmpty() ||sex.isEmpty()){
          Toast.makeText(MainActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
        }else{
          User u = new User();
          u.setFirstname(firstname);
          u.setLastname(lastname);
          u.setAge(Integer.parseInt(age));
          u.setCity(city);
          u.setSex(sex);
          u.setCountry(country);
          u.setDiabete(diabete);
          User.save(u);
          edit_age.setText("");
          edit_firstname.setText("");
          edit_lastname.setText("");
          if(check_minor.isChecked()){
            users.clear();
            users.addAll(User.findMinors());
            adapter.notifyDataSetChanged();
          }else{
            users.clear();
            users.addAll(User.findAllOrderedByTitle());
            adapter.notifyDataSetChanged();
          }
        }
      }
    });

    ArrayAdapter<CharSequence> city_adapter = ArrayAdapter.createFromResource(this,
        R.array.city_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    spinCity.setAdapter(city_adapter);

    ArrayAdapter<CharSequence> country_adapter = ArrayAdapter.createFromResource(this,
        R.array.country_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    country_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    spinCountry.setAdapter(country_adapter);




  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

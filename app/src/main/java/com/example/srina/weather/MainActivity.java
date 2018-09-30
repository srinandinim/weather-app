package com.example.srina.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    URL url;
    URLConnection connection;
    InputStream stream;
    BufferedReader reader;
    String readerString;
    JSONObject weather;

    EditText code;
    Button enter;
    TextView city, date, quote, ctemp, cutime, timeOne, timeTwo, timeThree, timeFour, timeFive, tempOne, tempTwo, tempThree, tempFour, tempFive, tempLowOne, tempLowTwo, tempLowThree, tempLowFour, tempLowFive;
    ImageView condition, imageOne, imageTwo, imageThree, imageFour, imageFive;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        code = (EditText) (findViewById(R.id.code));
        code.setText("");
        enter = (Button) (findViewById(R.id.enter));
        city = (TextView) (findViewById(R.id.city));
        date = (TextView) (findViewById(R.id.date));

        quote = (TextView) (findViewById(R.id.quote));
        condition = (ImageView) findViewById(R.id.condition);
        ctemp = (TextView) (findViewById(R.id.ctemp));
        cutime = (TextView) (findViewById(R.id.ctime));

        timeOne = (TextView) (findViewById(R.id.timeOne));
        timeTwo = (TextView) (findViewById(R.id.timeTwo));
        timeThree = (TextView) (findViewById(R.id.timeThree));
        timeFour = (TextView) (findViewById(R.id.timeFour));
        timeFive = (TextView) (findViewById(R.id.timeFive));

        tempOne = (TextView) (findViewById(R.id.tempOne));
        tempTwo = (TextView) (findViewById(R.id.tempTwo));
        tempThree = (TextView) (findViewById(R.id.tempThree));
        tempFour = (TextView) (findViewById(R.id.tempFour));
        tempFive = (TextView) (findViewById(R.id.tempFive));

        imageOne = (ImageView) findViewById(R.id.imageOne);
        imageTwo = (ImageView) findViewById(R.id.imageTwo);
        imageThree = (ImageView) findViewById(R.id.imageThree);
        imageFour = (ImageView) findViewById(R.id.imageFour);
        imageFive = (ImageView) findViewById(R.id.imageFive);

        tempLowOne = (TextView) findViewById(R.id.tempLowOne);
        tempLowTwo = (TextView) findViewById(R.id.tempLowTwo);
        tempLowThree = (TextView) findViewById(R.id.tempLowThree);
        tempLowFour = (TextView) findViewById(R.id.tempLowFour);
        tempLowFive = (TextView) findViewById(R.id.tempLowFive);

        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String zip = code.getText().toString();
                AsyncThread weatherThread = new AsyncThread(zip);
                weatherThread.execute();
                city.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                quote.setVisibility(View.VISIBLE);
                condition.setVisibility(View.VISIBLE);
                cutime.setVisibility(View.VISIBLE);
                ctemp.setVisibility(View.VISIBLE);
                timeOne.setVisibility(View.VISIBLE);
                timeTwo.setVisibility(View.VISIBLE);
                timeThree.setVisibility(View.VISIBLE);
                timeFour.setVisibility(View.VISIBLE);
                timeFive.setVisibility(View.VISIBLE);
                tempOne.setVisibility(View.VISIBLE);
                tempTwo.setVisibility(View.VISIBLE);
                tempThree.setVisibility(View.VISIBLE);
                tempFour.setVisibility(View.VISIBLE);
                tempFive.setVisibility(View.VISIBLE);
                imageOne.setVisibility(View.VISIBLE);
                imageTwo.setVisibility(View.VISIBLE);
                imageThree.setVisibility(View.VISIBLE);
                imageFour.setVisibility(View.VISIBLE);
                imageFive.setVisibility(View.VISIBLE);
                tempLowOne.setVisibility(View.VISIBLE);
                tempLowTwo.setVisibility(View.VISIBLE);
                tempLowThree.setVisibility(View.VISIBLE);
                tempLowFour.setVisibility(View.VISIBLE);
                tempLowFive.setVisibility(View.VISIBLE);
            }
        });
    }

    public class AsyncThread extends AsyncTask<Void, Void, JSONObject> {

        String zipCode;

        public AsyncThread(String code) {
            zipCode = code;
        }

        protected JSONObject doInBackground(Void... voids) {
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=" + zipCode + ",us&APPID=fc31b446899114913eb8b94eea4c4dc8");
                try {
                    connection = url.openConnection();
                    stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    readerString = reader.readLine();
                    weather = new JSONObject(readerString);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return weather;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            jsonObject = weather;
            try {
                city.setText(jsonObject.getJSONObject("city").getString("name"));
                weatherPicture();
                temperatureSet();
                dateSet();
                timeSet();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public int temperatureConvert(double kel) {
            int cel = (int) (kel - 273.15);
            int far = cel * 9 / 5 + 32;
            return far;
        }

        public void temperatureSet() {
            for (int i = 0; i < 5; i++) {
                try {
                    int currentTemp = temperatureConvert(weather.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp"));
                    String totalTemp = "" + currentTemp + (char) 0x00B0 + "F";
                    String high = "" + temperatureConvert(weather.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp_max")) + (char) 0x00B0 + "F";
                    String low = "" + temperatureConvert(weather.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp_min")) + (char) 0x00B0 + "F";
                    if (i == 0) {
                        tempOne.setText(high);
                        tempLowOne.setText(low);
                        ctemp.setText(totalTemp);
                    }
                    if (i == 1) {
                        tempTwo.setText(high);
                        tempLowTwo.setText(low);
                    }
                    if (i == 2) {
                        tempThree.setText(high);
                        tempLowThree.setText(low);
                    }
                    if (i == 3) {
                        tempFour.setText(high);
                        tempLowFour.setText(low);
                    }
                    if (i == 4) {
                        tempFive.setText(high);
                        tempLowFive.setText(low);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        public void weatherPicture() {
            for (int i = 0; i < 5; i++) {
                try {
                    int image = 0;
                    int bigimage = 0;
                    String quotes = "";
                    String weatherType = weather.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                    int ctime = Integer.parseInt(weather.getJSONArray("list").getJSONObject(i).getString("dt_txt").split(" ")[1].split(":")[0]);
                    if (weatherType.equals("Rain")) {
                        image = R.drawable.rainy;
                        bigimage = R.drawable.bigrainy;
                        quotes = "\'When life throws you rainy days, play in the puddles.\'- Pooh Bear";
                    } else if (weatherType.equals("Clouds")) {
                        image = R.drawable.cloudy;
                        bigimage = R.drawable.bigcloudy;
                        quotes = "\'Look beyond what you see.\'- Rafiki";
                    } else if (weatherType.equals("Clear")) {
                        if (ctime < 11){
                            image = R.drawable.night;
                            bigimage = R.drawable.bignight;
                           quotes = "\'It never hurts to keep looking for sunshine.\'- Eeyore";
                        }
                        else {
                            image = R.drawable.sunny;
                            bigimage = R.drawable.bigsunny;
                            quotes = "\'Remember you are the one who can fill the world with sunshine.\' - Snow White";
                        }
                    } else if (weatherType.equals("Snow")) {
                        image = R.drawable.snowy;
                        bigimage = R.drawable.bigsnowy;
                        quotes = "\'The cold never bothered me anyway.\'-Elsa";
                    } else if (weatherType.equals("Thunderstorms")) {
                        image = R.drawable.thunderstorms;
                        bigimage = R.drawable.bigthunderstorms;
                        quotes = "\'Hakuna Matata: It means no worries for the rest of your days.\'- Timon";
                    }
                    if (i == 0) {
                        condition.setImageResource(bigimage);
                        imageOne.setImageResource(image);
                        quote.setText(quotes + "");
                    }
                    if (i == 1) {
                        imageTwo.setImageResource(image);
                    }
                    if (i == 2) {
                        imageThree.setImageResource(image);
                    }
                    if (i == 3) {
                        imageFour.setImageResource(image);
                    }
                    if (i == 4) {
                        imageFive.setImageResource(image);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void dateSet() {
            String dt;
            try {
                dt = weather.getJSONArray("list").getJSONObject(0).getString("dt_txt");
                String[] cdate = dt.split(" ")[0].split("-");
                String ctime = dt.split(" ")[1].split(":")[0];
                int ddate = Integer.parseInt(cdate[2]);
                if (Integer.parseInt(ctime) < 5) {
                    ddate = Integer.parseInt(cdate[2]) - 1;
                }
                date.setText(cdate[1] + "-" + ddate + "-" + cdate[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void timeSet() {
            for (int i = 0; i < 5; i++) {
                String dt;
                String unit = "";
                int tnum = 0;
                try {
                    dt = weather.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                    int ctime = Integer.parseInt(dt.split(" ")[1].split(":")[0]);
                    if (ctime == 0) {
                        tnum = 7;
                        unit = "p.m.";
                    } else if (ctime < 5) {
                        tnum = ctime + 7;
                        unit = "p.m.";
                    } else if (ctime == 5) {
                        tnum = 12;
                        unit = "a.m.";
                    } else if (ctime < 18) {
                        tnum = ctime - 5;
                        unit = "a.m.";
                        if (ctime == 17) {
                            unit = "p.m.";
                        }
                    } else if (ctime < 24) {
                        tnum = ctime - 17;
                        unit = "p.m.";
                    }
                    if (i == 0) {
                        cutime.setText("" + tnum + ":00 " + unit);
                        timeOne.setText("" + tnum + " " + unit);
                    }
                    if (i == 1) {
                        timeTwo.setText("" + tnum + " " + unit);
                    }
                    if (i == 2) {
                        timeThree.setText("" + tnum + " " + unit);
                    }
                    if (i == 3) {
                        timeFour.setText("" + tnum + " " + unit);
                    }
                    if (i == 4) {
                        timeFive.setText("" + tnum + " " + unit);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}




package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weather.StreetViewActivity;

import java.util.ArrayList;

public class LocalizationActivity extends AppCompatActivity {

    ListView placesListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);

        placesListView = findViewById(R.id.placesListView);

        Intent intent = getIntent();
        ArrayList<String> placesList = intent.getStringArrayListExtra("PLACES_LIST");

        if (placesList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
            placesListView.setAdapter(adapter);

            placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedPlace = (String) parent.getItemAtPosition(position);

                    String streetViewUrl = prepareStreetViewUrl(selectedPlace);

                    if (!streetViewUrl.isEmpty()) {
                        Intent streetViewIntent = new Intent(LocalizationActivity.this, StreetViewActivity.class);
                        streetViewIntent.putExtra("STREET_VIEW_URL", streetViewUrl);
                        startActivity(streetViewIntent);
                    } else {
                        Toast.makeText(LocalizationActivity.this, "Brak danych o wybranym miejscu", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Brak danych o miejscach do wyświetlenia", Toast.LENGTH_SHORT).show();
        }
    }


    private String prepareStreetViewUrl(String place) {
        if (place.equalsIgnoreCase("Zakrzówek")) {
            return "https://www.google.com/maps/place/Zakrzówek,+Kraków/@50.0376706,19.8968686,14z/data=!3m1!4b1!4m6!3m5!1s0x47165c823f49590b:0xd66b8a41b5e6db48!8m2!3d50.0412396!4d19.9176933!16s%2Fg%2F1tfhf4pt?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Wawel")) {
            return "https://www.google.com/maps/place/Wawel/@50.0551759,19.9336239,17z/data=!4m6!3m5!1s0x47165b4bbd81c7c3:0xcdc02021d897c6e9!8m2!3d50.0551759!4d19.9336239!16s%2Fg%2F11hfls6mvr?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Ojcowski Park Narodowy (ok. Krakowa)")) {
            return "https://www.google.com/maps/place/Ojcowski+Park+Narodowy/@50.207055,19.8152959,17z/data=!3m1!4b1!4m6!3m5!1s0x471655f91045178f:0xcd136a085c8ae7f1!8m2!3d50.2070516!4d19.8178762!16zL20vMGJjMjJo?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Krupówki")) {
            return "https://www.google.com/maps/place/Krupówki,+34-500+Zakopane/@49.2935806,19.9516325,17z/data=!4m6!3m5!1s0x4715f2913105dc39:0x633583beee07e071!8m2!3d49.2938545!4d19.9538936!16s%2Fg%2F121fl2cw?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Gubałówka")) {
            return "https://www.google.com/maps/place/Gubałówka/@49.3074592,19.8632829,13z/data=!3m1!4b1!4m6!3m5!1s0x4715ed9cd20c012d:0x231c41379ea8d072!8m2!3d49.3074272!4d19.9370173!16s%2Fm%2F026pl7y?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Wielka Krokiew")) {
            return "https://www.google.com/maps/place/COS+Wielka+Krokiew/@49.2794137,19.9613639,17z/data=!3m1!4b1!4m6!3m5!1s0x4715f27c6231ae9d:0x73ccad8058b06225!8m2!3d49.2794102!4d19.9639442!16s%2Fm%2F02844_f?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Termy Zakopiańskie")) {
            return "https://www.google.com/maps/place/Termy+Zakopiańskie/@49.2951081,19.9630621,17z/data=!3m1!4b1!4m6!3m5!1s0x4715f292dd475511:0xbdffa2ec978babff!8m2!3d49.2951046!4d19.9656424!16s%2Fg%2F11f_4l3rnp?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Kopiec Kościuszki")) {
            return "https://www.google.com/maps/place/Kopiec+Kościuszki/@50.0548449,19.8901756,17z/data=!3m1!4b1!4m6!3m5!1s0x47165b9196828c9d:0x67084144db045c33!8m2!3d50.0548415!4d19.8927559!16s%2Fm%2F02x5wxc?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Muzeum Obrony Wybrzeża")) {
            return "https://www.google.com/maps/place/Muzeum+Obrony+Wybrzeża/@54.6224899,18.776392,15z/data=!4m10!1m2!2m1!1sMuzeum+Obrony+Wybrzeża!3m6!1s0x46fd06a2742b8ae9:0x29554b9376c12d0e!8m2!3d54.6230102!4d18.8016881!15sChdNdXpldW0gT2Jyb255IFd5YnJ6ZcW8YVoZIhdtdXpldW0gb2Jyb255IHd5YnJ6ZcW8YZIBBm11c2V1beABAA!16s%2Fg%2F121ln3nc?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Bulwar Nadmorski")) {
            return "https://www.google.com/maps/place/Bulwar+Nadmorski,+84-150+Hel/@54.606399,18.7820281,15z/data=!3m1!4b1!4m6!3m5!1s0x46fd042a000030b7:0x35936c9939e3cf85!8m2!3d54.606388!4d18.8004606!16s%2Fg%2F11r96t3pj?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Pomnik Neptuna")) {
            return "https://www.google.com/maps/place/Fontanna+Neptuna/@54.348549,18.6506455,17z/data=!3m1!4b1!4m6!3m5!1s0x46fd73a1d2d03071:0x80de874a0a33d731!8m2!3d54.3485459!4d18.6532258!16s%2Fg%2F122qx_c2?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Latarnia Morska")) {
            return "https://www.google.com/maps/place/Latarnia+Morska+-+Hel/@54.6000247,18.7943935,15z/data=!3m1!4b1!4m6!3m5!1s0x46fd042b6239426b:0xd07454f8444b23b!8m2!3d54.6000137!4d18.812826!16s%2Fm%2F03qnxy1?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Plaża Górki Zachodnie")) {
            return "https://www.google.com/maps/place/Plaża+Górki+Zachodnie/@54.3690704,18.7772486,17z/data=!3m1!4b1!4m6!3m5!1s0x46fd6d56ee71e683:0xee10733b561e010!8m2!3d54.3689758!4d18.7796822!16s%2Fg%2F11h9wswhrs?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Jaśkowa Dolina")) {
            return "https://www.google.com/maps/place/Jaśkowa+Dolina,+Gdańsk/@54.3686092,18.576733,15z/data=!3m1!4b1!4m6!3m5!1s0x46fd748ceb876545:0xabc20256b1e6f2e6!8m2!3d54.3685981!4d18.5951655!16s%2Fg%2F1hc0gfyvl?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Park Oliwski im. Adama Mickiewicza")) {
            return "https://www.google.com/maps/place/Park+Oliwski+im.+Adama+Mickiewicza/@54.411224,18.5595002,17z/data=!3m1!4b1!4m6!3m5!1s0x46fd0acb364b7f71:0x701b855baf5f843!8m2!3d54.4112209!4d18.5620805!16s%2Fg%2F121cs2sj?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Europejskie Centrum Solidarności")) {
            return "https://www.google.com/maps/place/Europejskie+Centrum+Solidarności/@54.3613161,18.6471597,17z/data=!3m1!4b1!4m6!3m5!1s0x46fd73710d9770f9:0xe2010c5f4e6145ad!8m2!3d54.361313!4d18.64974!16s%2Fg%2F120pnc0n?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Zamek Książąt Pomorskich")) {
            return "https://www.google.com/maps/place/Zamek+Książąt+Pomorskich+w+Szczecinie/@53.4260143,9.9460964,7z/data=!4m10!1m2!2m1!1zWmFtZWsgS3NpxIXFvMSFdCBQb21vcnNraWNo!3m6!1s0x47aa090e51ad568d:0xd1ba4155a6db895f!8m2!3d53.4260143!4d14.5603542!15sChtaYW1layBLc2nEhcW8xIV0IFBvbW9yc2tpY2haHSIbemFtZWsga3NpxIXFvMSFdCBwb21vcnNraWNokgENbW92aWVfdGhlYXRlcpoBJENoZERTVWhOTUc5blMwVkpRMEZuU1VSNGRXSjFXWFYzUlJBQuABAA!16s%2Fm%2F0268q2c?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Muzeum Narodowe w Szczecinie")) {
            return "https://www.google.com/maps/place/Muzeum+Narodowe+w+Szczecinie/@53.4272379,14.5545989,16z/data=!4m10!1m2!2m1!1sMuzeum+Narodowe+w+Szczecinie!3m6!1s0x47aa090c69d9cf7d:0xbd147d34e252a897!8m2!3d53.43007!4d14.564905!15sChxNdXpldW0gTmFyb2Rvd2UgdyBTemN6ZWNpbmllkgEGbXVzZXVt4AEA!16s%2Fg%2F112yf792j?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Wały Chrobrego")) {
            return "https://www.google.com/maps/place/Wały+Chrobrego/@53.4298509,14.5624909,17z/data=!3m1!4b1!4m6!3m5!1s0x47aa090b4747e3b5:0x20b10d9a360fd1eb!8m2!3d53.4298477!4d14.5650712!16s%2Fg%2F121yh25n?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Filharmonia im. Mieczysława Karłowicza w Szczecinie")) {
            return "https://www.google.com/maps/place/Filharmonia+im.+Mieczysława+Karłowicza+w+Szczecinie/@53.4291882,14.5553997,17z/data=!3m1!4b1!4m6!3m5!1s0x47aa090e00edd027:0xcd7acfc1e9586c23!8m2!3d53.429185!4d14.55798!16s%2Fg%2F11bw7pmbc3?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Brama Seklerska")) {
            return "https://www.google.com/maps/place/Brama+Seklerska+w+Tarnowie/@50.0105699,20.9626845,15z/data=!3m1!4b1!4m6!3m5!1s0x473d84a9686dcd47:0x27bbea7fb27d19b0!8m2!3d50.0105576!4d20.981117!16s%2Fg%2F1tg80798?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Rynek Główny w Tarnowie")) {
            return "https://www.google.com/maps/place/Rynek+Główny+w+Tarnowie/@50.0125984,20.9856157,17z/data=!3m1!4b1!4m6!3m5!1s0x473d84aa807be253:0xf9550989a568c209!8m2!3d50.012595!4d20.988196!16s%2Fg%2F11f55jsb6c?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Góra Świętego Marcina")) {
            return "https://www.google.com/maps/place/Góra+Świętego+Marcina/@49.9897533,20.9318217,13z/data=!3m1!4b1!4m6!3m5!1s0x473d9b40164ba5e5:0xbd50a5da42c58415!8m2!3d49.989722!4d21.005556!16s%2Fm%2F0zbw5kr?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Pomnik Władysława Łokietka")) {
            return "https://www.google.com/maps/place/Pomnik+Władysława+Łokietka+w+Tarnowie/@51.2143119,17.1120751,7z/data=!4m10!1m2!2m1!1zUG9tbmlrIFfFgmFkeXPFgmF3YSDFgW9raWV0a2E!3m6!1s0x473d84aa72842b21:0xb35d62830902bb7e!8m2!3d50.0128633!4d20.9863033!15sCh1Qb21uaWsgV8WCYWR5c8WCYXdhIMWBb2tpZXRrYZIBCXNjdWxwdHVyZeABAA!16s%2Fg%2F1wn_4wl8?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Twierdza Zamość")) {
            return "https://www.google.com/maps/place/Twierdza+Zamość/@50.7164736,23.2391097,15z/data=!4m10!1m2!2m1!1zVHdpZXJkemEgWmFtb8WbxIc!3m6!1s0x47236745a03c859b:0xab1405eb5f8049a6!8m2!3d50.7164736!4d23.2571341!15sChFUd2llcmR6YSBaYW1vxZvEh5IBEnRvdXJpc3RfYXR0cmFjdGlvbuABAA!16s%2Fm%2F02vv309?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Kamienice Ormiańskie")) {
            return "https://www.google.com/maps/place/Kamienice+Ormiańskie/@50.7174484,23.2507516,17z/data=!3m1!4b1!4m6!3m5!1s0x47236788c2ee08b5:0xb7ecb898c639c98c!8m2!3d50.717445!4d23.2533319!16s%2Fg%2F1239vh6n?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Ratusz w Zamościu")) {
            return "https://www.google.com/maps/place/Ratusz+w+Zamościu/@50.7174285,23.2499268,17z/data=!3m1!4b1!4m6!3m5!1s0x472367a48958aa2b:0xc5f289e8a4ca8c8f!8m2!3d50.7174251!4d23.2525071!16s%2Fg%2F1tqcjztr?hl=pl-PL&entry=ttu";
        }  else if (place.equalsIgnoreCase("Ogród zoologiczny w Zamościu im. Stefana Milera")) {
            return "https://www.google.com/maps/place/Ogród+zoologiczny+w+Zamościu+im.+Stefana+Milera/@50.7152123,23.2365008,17z/data=!3m1!4b1!4m6!3m5!1s0x472367bde4db6f3d:0x3ea1ddd7439c04d9!8m2!3d50.7152089!4d23.2390811!16s%2Fg%2F11gmc86d67?hl=pl-PL&entry=ttu";
        } else if(place.equalsIgnoreCase("Pałac Kultury i Nauki w Warszawie")) {
            return "https://www.google.pl/maps/place/Pałac+Kultury+i+Nauki/@52.2318413,21.0034147,17z/data=!3m1!4b1!4m6!3m5!1s0x471ecc8c92692e49:0xc2e97ae5311f2dc2!8m2!3d52.231838!4d21.005995!16zL20vMDg2Mm0?entry=ttu";
        } else if(place.equalsIgnoreCase("Centrum Nauki Kopernik")) {
            return "https://www.google.pl/maps/place/Centrum+Nauki+Kopernik/@52.2418585,21.0261468,17z/data=!3m1!4b1!4m6!3m5!1s0x471ecc5ba250ccd1:0x8d6f1b9b8410960!8m2!3d52.2418552!4d21.0287271!16s%2Fm%2F05mwh1p?entry=ttu";
        } else if(place.equalsIgnoreCase("Muzeum Powstania Warszawskiego")) {
            return "https://www.google.pl/maps/place/Muzeum+Powstania+Warszawskiego/@52.2328646,20.978454,17z/data=!3m1!4b1!4m6!3m5!1s0x471ecc8339f5a289:0xa98636b63a62c625!8m2!3d52.2328613!4d20.9810343!16zL20vMDc2MG5t?entry=ttu";
        } else if(place.equalsIgnoreCase("Łazienki Królewskie")) {
            return "https://www.google.pl/maps/place/Łazienki+Królewskie/@52.2146766,21.0303138,17z/data=!3m1!4b1!4m6!3m5!1s0x471ecd1e6e5e7ee3:0x8c56b6615ca1aa71!8m2!3d52.2146733!4d21.0328941!16zL20vMDZudzJy?entry=ttu";
        } else if(place.equalsIgnoreCase("Ogród Japoński")) {
            return "https://www.google.pl/maps/place/Ogród+Japoński/@51.1096724,17.0606122,15z/data=!3m1!4b1!4m6!3m5!1s0x470fe826831d5b83:0xffe3a9c4f46ed8d8!8m2!3d51.1096604!4d17.0790447!16s%2Fg%2F12105m9p?entry=ttu";
        } else if(place.equalsIgnoreCase("Punkt widokowy na Mostku Pokutnic")) {
            return "https://www.google.pl/maps/place/Punkt+widokowy+na+Mostku+Pokutnic+na+Katedrze+Św.+Marii+Magdaleny/@51.1096351,17.0320859,17z/data=!3m1!4b1!4m6!3m5!1s0x470fc275e41eba65:0xc23501fccec8d233!8m2!3d51.1096318!4d17.0346662!16s%2Fm%2F0gw_07l?entry=ttu";
        } else if(place.equalsIgnoreCase("Muzeum Sztuki Mieszczańskiej")) {
            return "https://www.google.pl/maps/place/Muzeum+Sztuki+Mieszczańskiej/@51.1095796,17.0136627,15z/data=!3m1!4b1!4m6!3m5!1s0x470fc275b16ab765:0x9a1fcb4c5e4ede68!8m2!3d51.1096489!4d17.0317456!16s%2Fm%2F0gw_4n2?entry=ttu";
        } else if(place.equalsIgnoreCase("Wyspa Słodowa")) {
            return "https://www.google.pl/maps/place/Wyspa+Słodowa/@51.1160612,17.0348878,17z/data=!3m1!4b1!4m6!3m5!1s0x470fe9dea42bd9c7:0x1e99bc60684405a2!8m2!3d51.1161092!4d17.0375204!16s%2Fm%2F0gwz25v?entry=ttu";
        }
        return "";
    }
}
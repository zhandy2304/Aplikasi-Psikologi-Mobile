package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Navigation_Bar_psikeater extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar_psikeater);

        bottomNavigation = findViewById(R.id.bottom_navigation_psikeater);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_chat_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_person_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new mainscreen_psikeater_fragmen();
                        break;
                    case 2:
                        fragment = new Chat_psikeater_fragmen();
                        break;
                    case 3:
                        fragment = new Profil_Psikeater_fragmen();
                        break;
                }
                loadFragment(fragment);
            }
        });

        bottomNavigation.setCount(2, "99");

        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_frame_layout,fragment)
                .commit();
    }
}
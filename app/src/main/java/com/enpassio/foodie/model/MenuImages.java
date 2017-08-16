package com.enpassio.foodie.model;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 8/16/2017.
 */

public class MenuImages {
    private static final String nutellaPieImageUrl = "http://cooknshare.com/wp-content/uploads/2015/10/C1915293-1444846793783477large.jpg";
    private static final String browniesImageUrl = "https://cafedelites.com/wp-content/uploads/2016/08/Fudgy-Cocoa-Brownies-35.jpg";
    private static final String yellowCake = "http://www.cookiemadness.net/wp-content/uploads/2014/03/yellow-cake-2.jpg";
    private static final String cheesecakeImageUrl = "http://food.fnr.sndimg.com/content/dam/images/food/fullset/2013/12/9/0/FNK_Cheesecake_s4x3.jpg.rend.hgtvcom.616.462.suffix/1387411272847.jpeg";

    public static ArrayList<String> getMenuImages() {
        ArrayList<String> menuImages = new ArrayList<String>();
        menuImages.add(nutellaPieImageUrl);
        menuImages.add(browniesImageUrl);
        menuImages.add(yellowCake);
        menuImages.add(cheesecakeImageUrl);

        return menuImages;
    }

    public String getBrowniesImageUrl() {
        return browniesImageUrl;
    }

    public String getCheesecakeImageUrl() {
        return cheesecakeImageUrl;
    }

    public String getNutellaPieImageUrl() {
        return nutellaPieImageUrl;
    }

    public String getYellowCake() {
        return yellowCake;
    }
}

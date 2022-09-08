package ru.leroy.screenerapi.util;

import net.bytebuddy.utility.RandomString;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.message.RateNames;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class UsersUtil {
    final UserEntity usr = new UserEntity();

    public static Long randomId() {
        return new Random().nextLong();
    }

    public static String randomEmail() {
        return RandomString.make().concat("@gmail.com");
    }

    public static String randomPassword() {
        return RandomString.make().concat("-pass-from-test");
    }

    public static String randomRate() {
        final ArrayList<String> rates = new ArrayList<>();
        rates.add(RateNames.FREE_RATE);
        rates.add(RateNames.PRO_RATE);
        return rates.stream()
            .skip((int) (rates.size() * Math.random()))
            .findFirst()
            .orElseThrow();
    }

    public UserEntity buildRandomUser() {
        this.usr.setId(UsersUtil.randomId());
        this.usr.setRate(UsersUtil.randomRate());
        this.usr.setEmail(UsersUtil.randomEmail());
        this.usr.setPassword(UsersUtil.randomPassword());
        this.usr.setExpiration(Instant.now());
        return usr;
    }
}
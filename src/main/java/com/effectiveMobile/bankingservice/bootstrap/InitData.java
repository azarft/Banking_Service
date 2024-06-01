package com.effectiveMobile.bankingservice.bootstrap;

import com.effectiveMobile.bankingservice.entities.Account;
import com.effectiveMobile.bankingservice.entities.Email;
import com.effectiveMobile.bankingservice.entities.PhoneNumber;
import com.effectiveMobile.bankingservice.entities.User;
import com.effectiveMobile.bankingservice.repositories.AccountRepository;
import com.effectiveMobile.bankingservice.repositories.EmailRepository;
import com.effectiveMobile.bankingservice.repositories.PhoneNumberRepository;
import com.effectiveMobile.bankingservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        Set<User> savedUsers = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            String name = getRandomName();
            String surname = getRandomName() + "ov";

            User user = User.builder()
                    .login(name + i)
                    .fullName(name + " " + surname)
                    .dateOfBirth(generateRandomDateBefore2005())
                    .password(passwordEncoder.encode(name+surname+i))
                    .build();
            User savedUser = userRepository.save(user);
            savedUsers.add(savedUser);

            Email email = Email.builder()
                    .email(name + '.' + surname + getRandomEmailEnding())
                    .user(savedUser)
                    .build();
            emailRepository.save(email);

            BigDecimal initialBalance = getRandomBalance();
            Account account = Account.builder()
                    .initialDeposit(initialBalance)
                    .balance(initialBalance)
                    .maxBalancePercentage(2.07)
                    .user(savedUser)
                    .build();
            accountRepository.save(account);

            PhoneNumber phoneNumber = PhoneNumber.builder()
                    .phoneNumber(generateRandomPhoneNumber())
                    .user(savedUser)
                    .build();
            phoneNumberRepository.save(phoneNumber);
        }
    }

    public static LocalDate generateRandomDateBefore2005() {
        int startYear = 1900;
        int endYear = 2005;

        int randomYear = ThreadLocalRandom.current().nextInt(startYear, endYear + 1);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 13);
        int randomDay = ThreadLocalRandom.current().nextInt(1, 29);

        LocalDate randomDate = LocalDate.of(randomYear, randomMonth, randomDay);
        while (randomDay > randomDate.lengthOfMonth()) {
            randomDay = ThreadLocalRandom.current().nextInt(1, randomDate.lengthOfMonth() + 1);
            randomDate = LocalDate.of(randomYear, randomMonth, randomDay);
        }

        return randomDate;
    }

    public static String getRandomName() {
        String[] Names = {
                "Aleksandr", "Aleksei", "Andrei", "Anna", "Anton", "Arkadii", "Arsenii", "Artem", "Boris", "Valentin",
                "Valentina", "Valerii", "Varvara", "Vasilii", "Vera", "Viktor", "Viktoriia", "Vitalii", "Vladimir", "Vladislav",
                "Viacheslav", "Galina", "Georgii", "Grigorii", "Daniil", "Daria", "Denis", "Evgenii", "Ekaterina", "Elena",
                "Elizaveta", "Zinaida", "Ivan", "Igor", "Inna", "Irina", "Kirill", "Konstantin", "Larisa", "Lev",
                "Lidiia", "Liliia", "Liubov", "Makar", "Maksim", "Margarita", "Marina", "Mariia", "Mikhail", "Nadezhda",
                "Nataliia", "Nikita", "Nikolai", "Oleg", "Olga", "Pavel", "Polina", "Roman", "Svetlana", "Sergei"
        };
        Random random = new Random();
        int index = random.nextInt(Names.length);
        return Names[index];
    }
    public static BigDecimal getRandomBalance() {
        Random random = new Random();
        int scale = 2;
        int precision = 10;
        BigDecimal max = new BigDecimal(Math.pow(10, precision)).setScale(scale, RoundingMode.HALF_UP);
        BigDecimal randomBigDecimal = max.multiply(new BigDecimal(random.nextDouble())).setScale(scale, RoundingMode.HALF_UP);
        return randomBigDecimal;
    }

    public String getRandomEmailEnding() {
        String[] emailEndings = {"@gmail.com", "@yahoo.com", "@hotmail.com", "@outlook.com", "@icloud.com"};
        Random random = new Random();
        int index = random.nextInt(emailEndings.length);
        return emailEndings[index];
    }

    public String generateRandomPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("+");

        // Generating country code (e.g., +1 for US)
        int countryCode = random.nextInt(99) + 1; // Random country code between 1 and 99
        phoneNumber.append(countryCode).append("-");

        // Generating area code (e.g., 3 digits)
        int areaCode = random.nextInt(900) + 100; // Random area code between 100 and 999
        phoneNumber.append(areaCode).append("-");

        // Generating local phone number (e.g., 7 digits)
        int localNumber = random.nextInt(9000000) + 1000000; // Random 7-digit number
        phoneNumber.append(localNumber);

        return phoneNumber.toString();
    }

}

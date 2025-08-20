package com.intro2se.yummy.db.migration;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class V3__add_user_data_seeding extends BaseJavaMigration {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement statement = context.getConnection().createStatement()) {
            String userSeeding = MessageFormat.format(
                    """
                            INSERT INTO USERS (user_name, password, email, full_name, phone_number, user_type) VALUES
                            (''customer1'', ''{0}'', ''customer1@example.com'', ''John Doe'', ''1234567890'', ''CUSTOMER''),
                            (''customer2'', ''{1}'', ''customer2@example.com'', ''Jane Smith'', ''1234567891'', ''CUSTOMER''),
                            (''owner1'', ''{2}'', ''owner1@example.com'', ''Alice Johnson'', ''1234567892'', ''RESTAURANT_OWNER''),
                            (''owner2'', ''{3}'', ''owner2@example.com'', ''Bob Williams'', ''1234567893'', ''RESTAURANT_OWNER''),
                            (''admin1'', ''{4}'', ''admin1@example.com'', ''Charlie Brown'', NULL, ''ADMIN''),
                            (''admin2'', ''{5}'', ''admin2@example.com'', ''Diana Prince'', NULL, ''ADMIN''),
                            (''driver1'', ''{6}'', ''driver1@example.com'', ''Eve Adams'', ''1234567894'', ''DELIVERY_DRIVER''),
                            (''driver2'', ''{7}'', ''driver2@example.com'', ''Frank White'', ''1234567895'', ''DELIVERY_DRIVER''),
                            (''customer3'', ''{8}'', ''customer3@example.com'', ''Grace Lee'', ''1234567896'', ''CUSTOMER''),
                            (''customer4'', ''{9}'', ''customer4@example.com'', ''Henry Moore'', ''1234567897'', ''CUSTOMER''),
                            (''owner3'', ''{10}'', ''owner3@example.com'', ''Ivy Green'', ''1234567898'', ''RESTAURANT_OWNER''),
                            (''driver3'', ''{11}'', ''driver3@example.com'', ''Jack Black'', ''1234567899'', ''DELIVERY_DRIVER''),
                            (''admin3'', ''{12}'', ''admin3@example.com'', ''Karen Taylor'', NULL, ''ADMIN''),
                            (''customer5'', ''{13}'', ''customer5@example.com'', ''Liam Brown'', ''1234567800'', ''CUSTOMER''),
                            (''customer6'', ''{14}'', ''customer6@example.com'', ''Mia Davis'', ''1234567801'', ''CUSTOMER''),
                            (''owner4'', ''{15}'', ''owner4@example.com'', ''Noah Scott'', ''1234567802'', ''RESTAURANT_OWNER''),
                            (''driver4'', ''{16}'', ''driver4@example.com'', ''Olivia Johnson'', ''1234567803'', ''DELIVERY_DRIVER''),
                            (''customer7'', ''{17}'', ''customer7@example.com'', ''Paul Martinez'', ''1234567804'', ''CUSTOMER''),
                            (''customer8'', ''{18}'', ''customer8@example.com'', ''Quinn Garcia'', ''1234567805'', ''CUSTOMER''),
                            (''owner5'', ''{19}'', ''owner5@example.com'', ''Rachel Lopez'', ''1234567806'', ''RESTAURANT_OWNER'');""",
                    passwordEncoder.encode("password0"), passwordEncoder.encode("password1"),
                    passwordEncoder.encode("password2"), passwordEncoder.encode("password3"),
                    passwordEncoder.encode("password4"), passwordEncoder.encode("password5"),
                    passwordEncoder.encode("password6"), passwordEncoder.encode("password7"),
                    passwordEncoder.encode("password8"), passwordEncoder.encode("password9"),
                    passwordEncoder.encode("password10"), passwordEncoder.encode("password11"),
                    passwordEncoder.encode("password12"), passwordEncoder.encode("password13"),
                    passwordEncoder.encode("password14"), passwordEncoder.encode("password15"),
                    passwordEncoder.encode("password16"), passwordEncoder.encode("password17"),
                    passwordEncoder.encode("password18"), passwordEncoder.encode("password19")
            );

            statement.execute(userSeeding);
        }
    }
}
package com.example.android.bakingapplication.repository.local;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


public class RealmMigrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.get("quantity")
                    .addField("quantity_tmp", long.class)
                    .transform(obj -> {
                        Double oldQuantity = obj.getDouble("quantity");
                        obj.setLong("quantity_tmp", 1);
                    })
                    .removeField("quantity")
                    .renameField("quantity_tmp", "quantity");
            oldVersion++;
        }
    }
}

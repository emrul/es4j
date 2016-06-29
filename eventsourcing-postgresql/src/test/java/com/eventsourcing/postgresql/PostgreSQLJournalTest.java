/**
 * Copyright (c) 2016, All Contributors (see CONTRIBUTORS file)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.eventsourcing.postgresql;

import com.eventsourcing.JournalTest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.testng.annotations.Test;

import javax.sql.DataSource;

@Test
public class PostgreSQLJournalTest extends JournalTest<PostgreSQLJournal> {

    private static DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/eventsourcing?user=eventsourcing&password=eventsourcing");

        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(30);
        config.setDataSource(dataSource);

        return new HikariDataSource(config);
    }

    public PostgreSQLJournalTest() {
        super(new PostgreSQLJournal(dataSource()));
    }
}

package com.mystays.authorizationserver.scheduler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TokenPurge {

    private static String purgeQuery = "DELETE FROM mystays.oauth2_authorization WHERE refresh_token_expires_at < NOW()";

    final JdbcTemplate jdbcTemplate;
    public TokenPurge(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        jdbcTemplate.update(purgeQuery);
    }
}

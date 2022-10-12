package com.mystays.authorizationserver.scheduler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional

public class TokenPurge {

    final JdbcTemplate jdbcTemplate;

    public TokenPurge(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        System.out.println("Scheduler");
        jdbcTemplate.update("DELETE FROM mystays.oauth2_authorization WHERE refresh_token_expires_at < NOW()");
    }
}

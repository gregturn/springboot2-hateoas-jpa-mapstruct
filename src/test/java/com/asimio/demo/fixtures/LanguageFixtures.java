package com.asimio.demo.fixtures;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import com.asimio.demo.domain.Film;
import com.asimio.demo.domain.Language;

public class LanguageFixtures {

    private static final Integer DEFAULT_LANG_ID = Integer.valueOf(20);
    private static final String DEFAULT_NAME = "English";
    private static final Date DEFAULT_LAST_UPDATED_TMSTP = Date.from(Instant.ofEpochMilli(1514768400));

    public static Language createLanguage(Set<Film> films) {
        Language result = new Language();
        result.setLanguageId(DEFAULT_LANG_ID);
        result.setName(DEFAULT_NAME);
        result.setLastUpdate(DEFAULT_LAST_UPDATED_TMSTP);
        result.setFilms(films);
        return result;
    }
}
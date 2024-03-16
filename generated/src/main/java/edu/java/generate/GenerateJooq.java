package edu.java.generate;

import java.nio.file.Path;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;

@SuppressWarnings("MultipleStringLiterals")
public class GenerateJooq {

    public static final String OUTPUT_PACKAGE_NAME = "edu.java.scrapper.domain.jooq";
    public static final Path OUTPUT_PATH = Path.of("generated/src/main/java");
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/scrapper";
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "postgres";

    protected GenerateJooq() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws Exception {
        Generate generate = new Generate()
            .withGeneratedAnnotation(true)
            .withGeneratedAnnotationDate(false)
            .withDaos(false)
            .withJavadoc(true)
            .withPojos(false)
            .withTables(true)
            .withRecords(false)
            .withKeys(false)
            .withIndexes(false);

// Не применимо, из-за особенности работы org.jooq.meta.extensions.liquibase.LiquibaseDatabase:
// Имена таблиц и колонок в сгенерированном коде получаются в ВЕРХНЕМ_РЕГИСТРЕ, и поэтому
// сгенерированные запросы в Postgres завершаются с ошибкой "нет такой таблицы"
//
// https://github.com/jOOQ/jOOQ/issues/12698#issuecomment-986859259
// https://github.com/jOOQ/jOOQ/issues/14475#issuecomment-1377366145
//
//        Database database = new Database()
//            .withName("org.jooq.meta.extensions.liquibase.LiquibaseDatabase")
//            .withProperties(
//                new Property().withKey("rootPath").withValue("migrations"),
//                new Property().withKey("scripts").withValue("liquibase.xml")
//            );

        Database database = new Database()
            .withName("org.jooq.meta.postgres.PostgresDatabase")
            .withIncludes(".*")
            .withExcludes("databasechangelog.*")
            .withInputSchema("public");

        Configuration configuration = new Configuration()
            .withJdbc(new Jdbc()
                .withUrl(JDBC_URL)
                .withUser(DB_USERNAME)
                .withPassword(DB_PASSWORD)
            )
            .withGenerator(new Generator()
                .withDatabase(database)
                .withTarget(new Target()
                    .withPackageName(OUTPUT_PACKAGE_NAME)
                    .withDirectory(OUTPUT_PATH.toString())
                    .withClean(true)
                )
                .withGenerate(generate)
            );
        GenerationTool.generate(configuration);
    }
}

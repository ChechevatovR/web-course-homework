/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables;


import edu.java.scrapper.domain.jooq.Public;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LinksGithub extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.links_github</code>
     */
    public static final LinksGithub LINKS_GITHUB = new LinksGithub();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.links_github.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.links_github.latest_issue_number</code>.
     */
    public final TableField<Record, Integer> LATEST_ISSUE_NUMBER = createField(DSL.name("latest_issue_number"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.links_github.latest_pr_number</code>.
     */
    public final TableField<Record, Integer> LATEST_PR_NUMBER = createField(DSL.name("latest_pr_number"), SQLDataType.INTEGER.nullable(false), this, "");

    private LinksGithub(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private LinksGithub(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.links_github</code> table reference
     */
    public LinksGithub(String alias) {
        this(DSL.name(alias), LINKS_GITHUB);
    }

    /**
     * Create an aliased <code>public.links_github</code> table reference
     */
    public LinksGithub(Name alias) {
        this(alias, LINKS_GITHUB);
    }

    /**
     * Create a <code>public.links_github</code> table reference
     */
    public LinksGithub() {
        this(DSL.name("links_github"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Internal.createUniqueKey(LinksGithub.LINKS_GITHUB, DSL.name("links_github_pk"), new TableField[] { LinksGithub.LINKS_GITHUB.ID }, true);
    }

    @Override
    public LinksGithub as(String alias) {
        return new LinksGithub(DSL.name(alias), this);
    }

    @Override
    public LinksGithub as(Name alias) {
        return new LinksGithub(alias, this);
    }

    @Override
    public LinksGithub as(Table<?> alias) {
        return new LinksGithub(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public LinksGithub rename(String name) {
        return new LinksGithub(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public LinksGithub rename(Name name) {
        return new LinksGithub(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public LinksGithub rename(Table<?> name) {
        return new LinksGithub(name.getQualifiedName(), null);
    }
}
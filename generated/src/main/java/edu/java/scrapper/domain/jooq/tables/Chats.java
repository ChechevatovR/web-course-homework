/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables;


import edu.java.scrapper.domain.jooq.Public;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Identity;
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
public class Chats extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.chats</code>
     */
    public static final Chats CHATS = new Chats();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.chats.id</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.chats.telegram_chat_id</code>.
     */
    public final TableField<Record, Long> TELEGRAM_CHAT_ID = createField(DSL.name("telegram_chat_id"), SQLDataType.BIGINT.nullable(false), this, "");

    private Chats(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Chats(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.chats</code> table reference
     */
    public Chats(String alias) {
        this(DSL.name(alias), CHATS);
    }

    /**
     * Create an aliased <code>public.chats</code> table reference
     */
    public Chats(Name alias) {
        this(alias, CHATS);
    }

    /**
     * Create a <code>public.chats</code> table reference
     */
    public Chats() {
        this(DSL.name("chats"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<Record, Integer> getIdentity() {
        return (Identity<Record, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Internal.createUniqueKey(Chats.CHATS, DSL.name("chats_pk"), new TableField[] { Chats.CHATS.ID }, true);
    }

    @Override
    public Chats as(String alias) {
        return new Chats(DSL.name(alias), this);
    }

    @Override
    public Chats as(Name alias) {
        return new Chats(alias, this);
    }

    @Override
    public Chats as(Table<?> alias) {
        return new Chats(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Chats rename(String name) {
        return new Chats(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Chats rename(Name name) {
        return new Chats(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Chats rename(Table<?> name) {
        return new Chats(name.getQualifiedName(), null);
    }
}
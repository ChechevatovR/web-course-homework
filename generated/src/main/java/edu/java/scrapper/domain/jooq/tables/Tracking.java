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
public class Tracking extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.tracking</code>
     */
    public static final Tracking TRACKING = new Tracking();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.tracking.chat_id</code>.
     */
    public final TableField<Record, Integer> CHAT_ID = createField(DSL.name("chat_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.tracking.link_id</code>.
     */
    public final TableField<Record, Integer> LINK_ID = createField(DSL.name("link_id"), SQLDataType.INTEGER.nullable(false), this, "");

    private Tracking(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Tracking(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.tracking</code> table reference
     */
    public Tracking(String alias) {
        this(DSL.name(alias), TRACKING);
    }

    /**
     * Create an aliased <code>public.tracking</code> table reference
     */
    public Tracking(Name alias) {
        this(alias, TRACKING);
    }

    /**
     * Create a <code>public.tracking</code> table reference
     */
    public Tracking() {
        this(DSL.name("tracking"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Internal.createUniqueKey(Tracking.TRACKING, DSL.name("tracking_pk"), new TableField[] { Tracking.TRACKING.CHAT_ID, Tracking.TRACKING.LINK_ID }, true);
    }

    @Override
    public Tracking as(String alias) {
        return new Tracking(DSL.name(alias), this);
    }

    @Override
    public Tracking as(Name alias) {
        return new Tracking(alias, this);
    }

    @Override
    public Tracking as(Table<?> alias) {
        return new Tracking(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Tracking rename(String name) {
        return new Tracking(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tracking rename(Name name) {
        return new Tracking(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tracking rename(Table<?> name) {
        return new Tracking(name.getQualifiedName(), null);
    }
}

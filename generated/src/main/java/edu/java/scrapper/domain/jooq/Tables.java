/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq;


import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.LinksGithub;
import edu.java.scrapper.domain.jooq.tables.Tracking;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>public.chats</code>.
     */
    public static final Chats CHATS = Chats.CHATS;

    /**
     * The table <code>public.links</code>.
     */
    public static final Links LINKS = Links.LINKS;

    /**
     * The table <code>public.links_github</code>.
     */
    public static final LinksGithub LINKS_GITHUB = LinksGithub.LINKS_GITHUB;

    /**
     * The table <code>public.tracking</code>.
     */
    public static final Tracking TRACKING = Tracking.TRACKING;
}

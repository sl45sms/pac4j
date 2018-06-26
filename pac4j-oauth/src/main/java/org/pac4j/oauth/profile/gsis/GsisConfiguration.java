package org.pac4j.oauth.profile.gsis;

import org.pac4j.oauth.config.OAuth20Configuration;

/**
 * Gsis OAuth configuration.
 *
 * @author Panagiotis Skarvelis
 * @since 3.1.0
 */
public class GsisConfiguration extends OAuth20Configuration {

    public final static String DEFAULT_FIELDS = "userid,taxid,lastname,firstname,fathename,mothername,birthyear";

    protected String fields = DEFAULT_FIELDS;

    public final static String DEFAULT_SCOPE = "read";

    public GsisConfiguration() {
        setScope(DEFAULT_SCOPE);
    }

    public String getFields() {
        return fields;
    }

    public void setFields(final String fields) {
        this.fields = fields;
    }
}

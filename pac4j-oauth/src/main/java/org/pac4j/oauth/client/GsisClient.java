package org.pac4j.oauth.client;


import org.pac4j.oauth.profile.gsis.GsisConfiguration;
import org.pac4j.oauth.profile.gsis.GsisProfile;
import org.pac4j.oauth.profile.gsis.GsisProfileDefinition;


import org.pac4j.scribe.builder.api.GsisApi20; 


/**
 * <p>This class is the OAuth client to authenticate users in Gsis.</p>
 * <p>It returns a {@link org.pac4j.oauth.profile.gsis.GsisProfile}.</p>
 *
 * @author Panagiotis Skarvelis (sl45sms[at]gmail[dot]com)
 * @since 3.1
 *
 */
public class GsisClient extends OAuth20Client<GsisProfile> {


    public GsisClient() {
        configuration = new GsisConfiguration();
    }

    public GsisClient(final String key, final String secret) {
        configuration = new GsisConfiguration();
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {

        configuration.setProfileDefinition(new GsisProfileDefinition());
        configuration.setApi(new GsisApi20("https://test.gsis.gr/oauth2server/oauth/authorize", 
                                           "https://test.gsis.gr/oauth2server/oauth/token"));
        super.clientInit();
    }

    @Override
    public GsisConfiguration getConfiguration() {
        return (GsisConfiguration) configuration;
    }

    public String getScope() {
        return this.configuration.getScope();
    }

    public void setScope(final String scope) {
        this.configuration.setScope(scope);
    }

    public String getFields() {
        return getConfiguration().getFields();
    }

    public void setFields(final String fields) {
        getConfiguration().setFields(fields);
    }

}

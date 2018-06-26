package org.pac4j.oauth.profile.gsis;

import org.pac4j.oauth.profile.OAuth20Profile;




/**
 * <p>This class is the user profile for Gsis with appropriate getters.</p>
 * <p>It is returned by the {@link org.pac4j.oauth.client.GsisClient}.</p>
 *
 * @author Panagiotis Skarvelis (sl45sms[at]gmail[dot]com)
 * @since 3.1
 */
public class GsisProfile extends OAuth20Profile {
    private static final long serialVersionUID = -6319265305949082980L;
    
    @Override
    public String getDisplayName() {
        return String.format("%s %s", getFirstName(), getLastName()).trim();
    }

    @Override
    public String getId() {
        return (String) getAttribute(GsisProfileDefinition.USER_ID);
    }
    
    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public String getFamilyName() {
        return getLastName();
    }

    @Override
    public String getFirstName() {
        return (String) getAttribute(GsisProfileDefinition.FIRST_NAME);
    }
    
    public String getTaxId() {
        return (String) getAttribute(GsisProfileDefinition.TAX_ID);
    }
    
    public String getLastName() {
        return (String) getAttribute(GsisProfileDefinition.LAST_NAME);
    }

    public String getBirthYear() {
        return (String) getAttribute(GsisProfileDefinition.BIRTH_YEAR);
    }

    public String getMotherName() {
        return (String) getAttribute(GsisProfileDefinition.MOTHER_NAME);
    }
 
    public String getFatherName() {
        return (String) getAttribute(GsisProfileDefinition.FATHER_NAME);
    }
}

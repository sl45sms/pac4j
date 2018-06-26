package org.pac4j.oauth.profile.gsis;


import com.github.scribejava.core.model.OAuth2AccessToken;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;

import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class is the Gsis profile definition.
 *
 * @author Panagiotis Skarvelis (sl45sms[at]gmail[dot]com)
 * @since 3.1
 */
public class GsisProfileDefinition extends OAuth20ProfileDefinition<GsisProfile, GsisConfiguration> {


    public static final String LAST_NAME = "lastname";
    public static final String FIRST_NAME = "firstname";
    public static final String FATHER_NAME = "fathername";
    public static final String MOTHER_NAME = "mothername";
    public static final String TAX_ID = "taxid";
    public static final String USER_ID = "userid";
    public static final String BIRTH_YEAR = "birthyear";
   

    //TODO set somewhere the server
    protected final static String BASE_URL = "https://test.gsis.gr/oauth2server/userinfo?format=xml";

    public GsisProfileDefinition() {
        super(x -> new GsisProfile());
        Arrays.stream(new String[] {LAST_NAME,FIRST_NAME,FATHER_NAME,MOTHER_NAME,TAX_ID,USER_ID,BIRTH_YEAR })
                .forEach(a -> primary(a, Converters.STRING));
    }

    @Override
    public String getProfileUrl(final OAuth2AccessToken accessToken, final GsisConfiguration configuration) {
        return BASE_URL + "&fields=" + configuration.getFields();
    }

    @Override
    public GsisProfile extractUserProfile(final String body) {
        final GsisProfile profile = newProfile();
        
        Map<String, String> map = new HashMap<String, String>();
        String xml = body;
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            //    if (log.isDebugEnabled()) log.debug("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("userinfo");
            Node nNode = nList.item(0);
            //    if (log.isDebugEnabled()) log.debug("Current Element :"+ nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // <root><userinfo userid="rg1201" taxid="068933130   
                //" lastname="ΒΑΒΟΥΛΑ" firstname="ΕΥΤΥΧΙΑ" fathername="ΕΜΜΑΝΟΥΗΛ" mothername="ΑΝΝΑ" birthyear="1950"/></root>
                map.put("userid", eElement.getAttribute("userid"));
                map.put("taxid", eElement.getAttribute("taxid"));
                map.put("lastname", eElement.getAttribute("lastname"));
                map.put("firstname", eElement.getAttribute("firstname"));
                map.put("fathername", eElement.getAttribute("fathername"));
                map.put("mothername", eElement.getAttribute("mothername"));
                map.put("birthyear", eElement.getAttribute("birthyear"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!map.isEmpty()) {
            profile.setId(ProfileHelper.sanitizeIdentifier(profile, map.get("taxid")));            
            for (final String attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute, map.get(attribute));
            }
        }
        return profile;
    }
}

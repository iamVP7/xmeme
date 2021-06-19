package com.xmeme.clientobjects;

import com.xmeme.api.processor.XMemeException;
import com.xmeme.utils.APIErrorMessage;
import com.xmeme.utils.Constants;
import com.xmeme.utils.IOCommonUtil;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientMeme {

    private String name;
    private String url;
    private String caption;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return this.name + this.url + this.caption;
    }

    public void validate() throws XMemeException.MandatoryKeyNotFound, XMemeException.ImproperValueSet {
        checkURL();
        checkCaption();
        checkName();
    }

    /*
    URL should not be empty and also should not be more than 1000 chars
     */
    private void checkURL() throws XMemeException.MandatoryKeyNotFound, XMemeException.ImproperValueSet {
        if (!IOCommonUtil.isValidString(this.url)) {
            throw new XMemeException.MandatoryKeyNotFound(APIErrorMessage.ERRORCODE.URL_NOT_PRESENT, Constants.CLIENT_KEY_URL);
        } else if (this.url.length() > 1000 || !(this.url.startsWith(Constants.HTTPS) || this.url.startsWith(Constants.HTTP))) {
            throw new XMemeException.ImproperValueSet(APIErrorMessage.ERRORCODE.IMPROPER_URL, Constants.CLIENT_KEY_URL);
        }
    }

    /*
    Caption should not be empty
     */
    private void checkCaption() throws XMemeException.MandatoryKeyNotFound, XMemeException.ImproperValueSet {
        if (!IOCommonUtil.isValidString(this.caption)) {
            throw new XMemeException.MandatoryKeyNotFound(APIErrorMessage.ERRORCODE.CAPTION_NOT_PRESENT, Constants.CLIENT_KEY_CAPTION);
        } else if (this.caption.length() > 95) {
            throw new XMemeException.ImproperValueSet(APIErrorMessage.ERRORCODE.IMPROPER_CAPTION, Constants.CLIENT_KEY_CAPTION);
        }
    }


    /*
    User Name  should not be empty
     */
    private void checkName() throws XMemeException.MandatoryKeyNotFound, XMemeException.ImproperValueSet{
        if (!IOCommonUtil.isValidString(name)){
            throw new XMemeException.MandatoryKeyNotFound(APIErrorMessage.ERRORCODE.AUTHOR_NOT_PRESENT, Constants.MEME_CREATOR_NAME);
        } else if (this.name.length() > 45) {
            throw new XMemeException.ImproperValueSet(APIErrorMessage.ERRORCODE.IMPROPER_NAME, Constants.MEME_CREATOR_NAME);
        }
    }
}

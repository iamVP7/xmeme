package com.xmeme.api.processor;

import com.xmeme.clientobjects.ClientMeme;
import com.xmeme.handler.MemeCreatorManager;
import com.xmeme.handler.MemeManager;
import com.xmeme.pojo.MemeCreator;
import com.xmeme.pojo.Memes;
import com.xmeme.utils.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

public class MemeAPIHandler {

    private static final Logger MEME_API_LOGGER = Logger.getLogger(MemeAPIHandler.class.getName());

    /**
     * @param clientClientMeme is the client JSON converted object
     * @return 409 in case same user enters same URL & Captin
     */
    public Response createMeme(ClientMeme clientClientMeme) {

        if (IOCommonUtil.isValidObject(clientClientMeme)) {
            try {
                clientClientMeme.validate();
                MemeCreator searchCreator = new MemeCreator();
                searchCreator.setOwnerName(clientClientMeme.getName());

                MemeCreator memeCreator = getMemerCreator(searchCreator);
                Memes createdMeme = checkAndCreateMeme(clientClientMeme, memeCreator);
                if (IOCommonUtil.isValidObject(createdMeme)) {
                    return getMemeCreatedResponse(createdMeme);
                }
            } catch (XMemeException exception) {
                XMemeLogger.warning(MEME_API_LOGGER, exception);
                return ResponseConstructors.getExceptionBaseResponse(exception);
            }
        }
        return ResponseConstructors.getExceptionBaseResponse(APIErrorMessage.ERRORCODE.INTERNAL_ERROR);
    }

    /**
     * <p> get meme creation success message</p>
     *
     * @param createdMeme is the created Meme Object
     * @return will be returing the status code of 201 and id
     */
    private Response getMemeCreatedResponse(final Memes createdMeme) {
        JSONObject responseObject = IOCommonUtil.addJSONKeyValue(null, Constants.CLIENT_MEME_ID, createdMeme.getMemeId());
        ResponseConstructors responseConstructor = new ResponseConstructors.ResponseBuilder()
                .setResponseCode(Constants.HTTP_CREATE_SUCCESS)
                .setClientJSON(responseObject)
                .build();
        return responseConstructor.getResponse();
    }

    /**
     * <p>create the meme after checking</p>
     *
     * @param clientClientMeme is the client meme object
     * @param memeCreator      is the creator who created the meme
     * @return creates the meme
     * @throws XMemeException is thrown when the MEME already exist
     */
    private Memes checkAndCreateMeme(final ClientMeme clientClientMeme, final MemeCreator memeCreator) throws XMemeException {
        List<Memes> existingMeme = getExistingMeme(clientClientMeme, memeCreator);
        if (IOCommonUtil.isValidList(existingMeme) && IOCommonUtil.isValidObject(existingMeme.get(Constants.FIRST_INDEX)) &&
                IOCommonUtil.isValidLong(existingMeme.get(Constants.FIRST_INDEX).getMemeId())) {
            throw new XMemeException.MemeAlreadyExist();
        }
        return createMeme(clientClientMeme, memeCreator);
    }

    /**
     * <p>Get meme creator data</p>
     *
     * @param clientClientMeme is the client meme object
     * @return MemeCreator if already exist or creates newly and sents
     */
    private MemeCreator getMemerCreator(MemeCreator searchCreator) {
        MemeCreator creator = null;
        try {
            creator = searchMemeCreator(searchCreator);
        } catch (Exception exception) {
            XMemeLogger.warning(MEME_API_LOGGER, exception);
        }
        if (!IOCommonUtil.isValidObject(creator)) {
            creator = new MemeCreatorManager().addMemeCreator(searchCreator.getOwnerName());
        }
        return creator;
    }

    /**
     * @param searchCreator is the memecreator object which we need to search from DB
     * @return if found new CreatorObject else null
     */
    private MemeCreator searchMemeCreator(final MemeCreator searchCreator) {
        return new MemeCreatorManager().getCreator(searchCreator);
    }

    /**
     * <p>Get exising meme from the db</p>
     *
     * @param clientClientMeme is the client meme object
     * @return Meme which is exists or null is not
     */
    private List<Memes> getExistingMeme(final ClientMeme clientClientMeme, final MemeCreator memeCreator) {
        try {
            Memes memeToCheck = new Memes(clientClientMeme, memeCreator);
            return new MemeManager().getExistingMeme(memeToCheck, Constants.DEFAULT_LONG);
        } catch (Exception exception) {
            XMemeLogger.warning(MEME_API_LOGGER, exception);
        }
        return null;
    }

    /**
     * <p>create the meme in database</p>
     *
     * @param clientClientMeme is the client object to create the meme
     * @param memeCreator      is the creator who is creating
     * @return Meme is created
     */
    private Memes createMeme(final ClientMeme clientClientMeme, final MemeCreator memeCreator) {
        try {
            return new MemeManager().addMeme(clientClientMeme, memeCreator);
        } catch (Exception exception) {
            XMemeLogger.warning(MEME_API_LOGGER, exception);
        }
        return null;
    }

    /**
     * <p>this will be giving the 100 memes as response based on page order</p>
     *
     * @param pageOrder will be null for first then on 1 or 2..
     * @return JSONArray as response.
     */
    public Response getAllMemes(final String pageOrder) {
        List<Memes> allExisitingMemes = new MemeManager().getAllMemes(getPageOrder(pageOrder));
        return getAllMemesResponse(allExisitingMemes );
    }

    /**
     * @param allExisitingMemes is the list of meme objects which we got from DB
     * @return response to the client
     */
    private Response getAllMemesResponse(final List<Memes> allExisitingMemes ){
        JSONArray responseJSON = getClientMemeJSON(allExisitingMemes);
        return new ResponseConstructors.ResponseBuilder()
                .setResponseCode(Constants.HTTP_FEATCH_SUCCESS)
                .setClientJSON(responseJSON)
                .build().getResponse();
    }

    /**
     * <p>change page order string to number</p>
     *
     * @param pageOrder will be null for first then on 1 or 2..
     * @return int of 1 or 2.. else 0.
     */
    private int getPageOrder(final String pageOrder) {
        if (IOCommonUtil.isValidString(pageOrder)) {
            try {
                int tempPageOrder = Integer.parseInt(pageOrder);
                if (tempPageOrder > 0) {
                    return tempPageOrder;
                }
            } catch (Exception exception) {
                XMemeLogger.info(MEME_API_LOGGER, exception);
            }
        }
        return Constants.MINIMUM_PAGE_ORDER;
    }

    /**
     * <p>convert the list of memes to JSONArray for client response</p>
     *
     * @param allExisitingMemes fetched rows of Memes
     * @return JSONArray for client response
     */
    private JSONArray getClientMemeJSON(final List<Memes> allExisitingMemes) {
        JSONArray responseJSONArray = new JSONArray();
        if (IOCommonUtil.isValidList(allExisitingMemes)) {
            for (Memes allExisitingMeme : allExisitingMemes) {
                JSONObject singleMemeJSON = getClientJSON(allExisitingMeme);
                responseJSONArray.put(singleMemeJSON);
            }
        }
        return responseJSONArray;
    }

    /**
     * @param memeIDToFetch the memeID which we have to fetch
     * @return JSONObject of the meme detail if exist or 404 header code if not
     */
    public Response getSpecificMeme(String memeIDToFetch) {
        try {
            if (IOCommonUtil.isValidString(memeIDToFetch)) {
                long memeID = Long.parseLong(memeIDToFetch);
                if (IOCommonUtil.isValidLong(memeID)) {
                    List<Memes> memeObjecFetch = new MemeManager().getExistingMeme(null, memeID);
                    if (IOCommonUtil.isValidList(memeObjecFetch)) {
                        JSONObject responseJSON = getClientJSON(memeObjecFetch.get(Constants.FIRST_INDEX));
                        return new ResponseConstructors.ResponseBuilder()
                                .setResponseCode(Constants.HTTP_FEATCH_SUCCESS)
                                .setClientJSON(responseJSON)
                                .build()
                                .getResponse();
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return ResponseConstructors.notFound();
    }

    /**
     * @param memeObjecFetch this is the Meme detail and its author detail container
     * @return JSONObject which will have memeid, url, caption, author name
     */
    private JSONObject getClientJSON(final Memes memeObjecFetch) {

        if (IOCommonUtil.isValidObject(memeObjecFetch)) {
            return memeObjecFetch.valueAsJSON();
        }
        return new JSONObject();
    }

    /**
     * @param clientClientMeme is the client JSON converted object
     * @return 409 in case same user enters same URL & Captin
     */
    public Response updateMeme(final ClientMeme clientClientMeme, final String memeID) {

        if (IOCommonUtil.isValidObject(clientClientMeme)) {
            try {
                clientClientMeme.validate();
                List<Memes> exisitingMemes = new MemeManager().getExistingMeme(null, Long.parseLong(memeID));
                if (IOCommonUtil.isValidObject(exisitingMemes)) {
                    Memes updatedMeme = getMemeToUpdate(exisitingMemes.get(Constants.FIRST_INDEX), clientClientMeme);
                    Memes newUPdatedMeme = new MemeManager().updateMeme(updatedMeme);
                    if (IOCommonUtil.isValidObject(newUPdatedMeme)) {
                        return ResponseConstructors.updateSuccess();
                    }
                } else {
                    return ResponseConstructors.notFound();
                }
            } catch (XMemeException exception) {
                XMemeLogger.warning(MEME_API_LOGGER, exception);
                return ResponseConstructors.getExceptionBaseResponse(exception);
            }
        }
        return ResponseConstructors.notModified();
    }

    /**
     * @param exisitingMeme is the existing meme object in DB
     * @param clientClientMeme is the object which we need to update
     * @return a new updated exisitingMeme object to upudate in db
     */
    private Memes getMemeToUpdate(Memes exisitingMeme, final ClientMeme clientClientMeme) {
        if (IOCommonUtil.isValidObject(clientClientMeme.getUrl())) {
            exisitingMeme.setUrl(clientClientMeme.getUrl());
        }
        if (IOCommonUtil.isValidObject(clientClientMeme.getCaption())) {
            exisitingMeme.setCaption(clientClientMeme.getCaption());
        }
        return exisitingMeme;
    }

    /**
     *
     * @param authorID is the author id which we need to fetch
     * @return if authorid not found 404 or 200 with response json
     */
    public Response getMemesByAuthor(final String authorID) {

        try {
            MemeCreator searchCreator = new MemeCreator();
            searchCreator.setId(Long.parseLong(authorID));
            MemeCreator creator = searchMemeCreator(searchCreator);
            if(IOCommonUtil.isValidObject(creator)){
                List<Memes> allFetchedMems = getExistingMeme(null, creator);
                return getAllMemesResponse(allFetchedMems );
            }
        } catch (Exception exceptionObj) {
            XMemeLogger.warning(MEME_API_LOGGER, exceptionObj);
        }
        return ResponseConstructors.notFound();
    }
}

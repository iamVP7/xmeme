package com.xmeme.api.handler;


import com.xmeme.api.processor.MemeAPIHandler;
import com.xmeme.clientobjects.ClientMeme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("") // NO I18N
public class MemeAPI {
    private static final String URL_FETCH_SPECIFIC_MEME = "/{meme_id}"; // NO I18N
    private static final String URL_MORE_MEMES = "/pageorder/{page_order_id}"; // NO I18N
    private static final String URL_MEMES_BY_AUTHOR = "/by/{author_id}"; // NO I18N

    private static final String PP_MEME_ID = "meme_id"; // NO I18N
    private static final String PP_PAGE_ORDER_ID = "page_order_id"; // NO I18N
    private static final String PP_AUTHOR_ID = "author_id"; // NO I18N

    /**
    This will give last 100 memes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemes() {
        return new MemeAPIHandler().getAllMemes(null);
    }

    /**
     *
     * <p></p>This will give 'n'th last 100 memes</p>
     *
     * @param pageOrderID is the n which we want; if 1 then 101 -200 memes will come
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(URL_MORE_MEMES)
    public Response getMoreMemes(@PathParam(PP_PAGE_ORDER_ID) String pageOrderID) {
        return new MemeAPIHandler().getAllMemes(pageOrderID);
    }

    /*
    This will be used to Create meme
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMems(ClientMeme clientClientMeme) {
        return new MemeAPIHandler().createMeme(clientClientMeme);
    }


    /**
     *
     * <p>This will be used to get specific Meme</p>
     *
     * @param memeID this is the meme id which is generated during insert
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(URL_FETCH_SPECIFIC_MEME)
    public Response getSpecificMeme(@PathParam(PP_MEME_ID) String memeID) {
        return new MemeAPIHandler().getSpecificMeme(memeID);
    }

    /**
    <p> API to update the description and author</p>
     */
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path(URL_FETCH_SPECIFIC_MEME)
    public Response updateSpecificMeme(ClientMeme clientClientMem,@PathParam(PP_MEME_ID) String memeID) {
        return new MemeAPIHandler().updateMeme(clientClientMem,memeID);
    }

    /**
     *
     * @param authorID is the author id which we need to fetch
     * @return if authorid not found 404 or 200 with response json
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(URL_MEMES_BY_AUTHOR)
    public Response getAuthorSpecificMems(@PathParam(PP_AUTHOR_ID) String authorID){
        return new MemeAPIHandler().getMemesByAuthor(authorID);
    }
}

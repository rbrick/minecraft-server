package me.rbrickis.minecraft.server.api.json;

import com.google.gson.JsonObject;

public final class ChatBuilder {

    private JsonObject parent = null;
    private JsonObject chatObject;

    public ChatBuilder() {
        this.chatObject = new JsonObject();
    }

    public ChatBuilder(JsonObject parent) {
        this.parent = parent;
        this.chatObject = new JsonObject();
    }


    public ChatBuilder text(String message) {
        chatObject.addProperty("text", message);
        return this;
    }

    public ChatBuilder extra() {
        return this;
    }


}

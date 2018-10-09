package io.dreamz.motd.server.api.chat;

import com.google.gson.JsonObject;

public class ClickEvent {

    public enum Action {
        OPEN_URL,
        RUN_COMMAND;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private Action action;
    private String value;

    public ClickEvent(Action action, String value) {
        this.action = action;
        this.value = value;
    }


    public JsonObject toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("action", action.toString());
        object.addProperty("value", value);

        return object;
    }
}

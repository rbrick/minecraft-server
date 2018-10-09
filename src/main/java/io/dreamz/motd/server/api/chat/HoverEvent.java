package io.dreamz.motd.server.api.chat;

import com.google.gson.JsonObject;

public class HoverEvent {
        public enum Action {
            SHOW_TEXT; // only this is supported

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }

        private Action action;
        private String value;

        public HoverEvent(Action action, String value) {
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

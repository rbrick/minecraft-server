package io.dreamz.motd.server.api.chat;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;


import java.util.List;

public class TextObject {

    private String text;
    private ChatColor color;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;
    private boolean italic = false, bold = false, underlined = false, obfuscated = false;
    private List<TextObject> extra = Lists.newArrayList();

    public TextObject(String text) {
        this.text = text;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public void setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public void setExtra(List<TextObject> extra) {
        this.extra.addAll(extra);
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("text", text);

        if (color != null)
            object.addProperty("color", color.name().toLowerCase());
        if (clickEvent != null)
            object.add("clickEvent", clickEvent.toJson());
        if (hoverEvent != null)
            object.add("hoverEvent", hoverEvent.toJson());
        if (italic)
            object.addProperty("italic", true);
        if (bold)
            object.addProperty("bold", true);
        if (underlined)
            object.addProperty("underlined", true);
        if (obfuscated)
            object.addProperty("obfuscated", true);

        if (!extra.isEmpty()) {
            JsonArray array = new JsonArray();

            for (TextObject ex : extra) {
                array.add(ex.toJson());
            }

            object.add("extra", array);
        }
        return object;
    }
}
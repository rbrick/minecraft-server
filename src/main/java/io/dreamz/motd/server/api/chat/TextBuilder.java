package io.dreamz.motd.server.api.chat;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.util.List;

public final class TextBuilder {
    private TextObject root;

    // The current text object. This will change when we append text for example
    private TextObject current;

    // The storage of the extra items
    private List<TextObject> extra = Lists.newArrayList();

    private TextBuilder(TextObject root) {
        this.root = root;
        this.current = root;
    }


    public TextBuilder color(ChatColor color) {
        this.current.setColor(color);
        return this;
    }

    public TextBuilder italicize(boolean b) {
        this.current.setItalic(b);
        return this;
    }

    public TextBuilder bold(boolean b) {
        this.current.setBold(b);
        return this;
    }


    public TextBuilder underline(boolean b) {
        this.current.setUnderlined(b);
        return this;
    }

    public TextBuilder obfuscate(boolean b) {
        this.current.setObfuscated(b);
        return this;
    }

    public TextBuilder clickEvent(ClickEvent clickEvent) {
        this.current.setClickEvent(clickEvent);
        return this;
    }

    public TextBuilder hoverEvent(HoverEvent hoverEvent) {
        this.current.setHoverEvent(hoverEvent);
        return this;
    }

    public TextBuilder append(String text) {
        // essentially this completes what ever object we were on. No turning back!
        this.extra.add(this.current = new TextObject(text));
        return this;
    }


    public TextObject build() {
        // currently we're only adding the extras to the root.
        this.root.setExtra(extra);
        return this.root;
    }

    public static TextBuilder of(String text) {
        return new TextBuilder(new TextObject(text));
    }

}

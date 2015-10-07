package me.rbrickis.minecraft.server.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All packets should be marked with this
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketInfo {

    /**
     * Is the packet Incoming (Being received from the client), or Outgoing (Being sent towards the client)
     *
     * @return A {@code Direction}
     */
    Direction direction();


    /**
     * @return Link to the packets Wiki.vg page.
     */
    String info() default "http://wiki.vg/Main_Page";


    /**
     * @return The protocol state the packet is in
     */
    State state();

    /**
     * @return The ID of the packet
     */
    int id();

}

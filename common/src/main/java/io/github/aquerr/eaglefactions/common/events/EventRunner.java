package io.github.aquerr.eaglefactions.common.events;

import com.flowpowered.math.vector.Vector3i;
import io.github.aquerr.eaglefactions.api.entities.Faction;
import io.github.aquerr.eaglefactions.api.entities.FactionMemberType;
import io.github.aquerr.eaglefactions.api.entities.FactionPlayer;
import io.github.aquerr.eaglefactions.api.events.*;
import io.github.aquerr.eaglefactions.common.EagleFactionsPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.world.World;

import java.util.Optional;

/**
 * An util class used for running Eagle Factions events.
 */
public final class EventRunner
{
    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionLeaveEvent(final Player player, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionLeaveEvent event = new FactionLeaveEventImpl(player, faction, eventCause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionJoinEvent(final Player player, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionJoinEvent event = new FactionJoinEventImpl(player, faction, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionChestEvent(final Player player, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionChestEvent event = new FactionChestEventImpl(player, faction, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionClaimEvent(final Player player, final Faction faction, final World world, final Vector3i chunkPosition)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionClaimEvent event = new FactionClaimEventImpl(player, faction, world, chunkPosition, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionCreateEvent(final Player player, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionCreateEvent event = new FactionCreateEventImpl(player, faction, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionKickEvent(final FactionPlayer kickedPlayer, final Player kickedBy, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(kickedBy).build();
        final Cause eventCause = Cause.of(eventContext, kickedBy, faction);
        final FactionKickEvent event = new FactionKickEventImpl(kickedPlayer, kickedBy, faction, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
    public static boolean runFactionUnclaimEvent(final Player player, final Faction faction, final World world, final Vector3i chunkPosition)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, faction);
        final FactionClaimEvent.Unclaim event = new FactionUnclaimEventImpl(player, faction, world, chunkPosition, eventCause);
        return Sponge.getEventManager().post(event);
    }

    /**
     * @return True if cancelled, false if not
     */
	public static boolean runFactionAreaEnterEvent(final MoveEntityEvent moveEntityEvent, final Player player, final Optional<Faction> enteredFaction, final Optional<Faction> leftFaction)
	{
	    final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause eventCause = Cause.of(eventContext, player, enteredFaction, leftFaction);
        final FactionAreaEnterEvent event = new FactionAreaEnterEventImpl(moveEntityEvent, player, enteredFaction, leftFaction, eventCause);
        return Sponge.getEventManager().post(event);
	}

	public static boolean runFactionRenameEvent(final Player player, final Faction faction, final String newName)
    {
        final EventContext eventContext = getEventContextForPlayer(player).build();
        final Cause cause = Cause.of(eventContext, player, faction);
        final FactionRenameEvent event = new FactionRenameEventImpl(player, faction, newName, cause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionDisbandEvent(final Object source, final Faction playerFaction, final boolean forceRemovedByAdmin, final boolean removedDueToInactiviy)
    {
        // Some special code here... because DisbandEvent can also be fired by FactionsRemover.
        // TODO: Maybe it can be written better?

        EventContext.Builder eventContextBuilder = EventContext.builder();
        final Cause.Builder causeBuilder = Cause.builder();
        if (source instanceof Player)
        {
            final Player player = (Player)source;
            eventContextBuilder = getEventContextForPlayer(player);
            causeBuilder.append(player).append(playerFaction);
        }
        else
        {
            causeBuilder.append(EagleFactionsPlugin.getPlugin()).append(playerFaction);
        }

        final EventContext eventContext = eventContextBuilder.build();
        final Cause cause = causeBuilder.build(eventContext);

        FactionDisbandEvent event;

        if (source instanceof Player)
        {
            event = new FactionDisbandEventImpl((Player)source, playerFaction, forceRemovedByAdmin, removedDueToInactiviy, cause);
        }
        else
        {
            event = new FactionDisbandEventImpl(null, playerFaction, forceRemovedByAdmin, removedDueToInactiviy, cause);
        }
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionDemoteEventPre(final Player demotedBy, final FactionPlayer demotedPlayer, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(demotedBy).build();
        final Cause cause = Cause.of(eventContext, demotedBy, faction, demotedPlayer);
        final FactionDemoteEvent.Pre event = new FactionDemoteEventImpl.Pre(faction, demotedBy, demotedPlayer, cause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionDemoteEventPost(final Player demotedBy, final FactionPlayer demotedPlayer, final FactionMemberType demotedTo, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(demotedBy).build();
        final Cause cause = Cause.of(eventContext, demotedBy, faction, demotedPlayer);
        final FactionDemoteEvent event = new FactionDemoteEventImpl.Post(faction, demotedBy, demotedPlayer, demotedTo, cause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionPromoteEventPre(final Player promotedBy, final FactionPlayer promotedPlayer, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(promotedBy).build();
        final Cause cause = Cause.of(eventContext, promotedBy, faction, promotedPlayer);
        final FactionPromoteEvent.Pre event = new FactionPromoteEventImpl.Pre(promotedBy, promotedPlayer, faction, cause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionPromoteEventPost(final Player promotedBy, final FactionPlayer promotedPlayer, final FactionMemberType promotedToRank, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(promotedBy).build();
        final Cause cause = Cause.of(eventContext, promotedBy, faction, promotedPlayer);
        final FactionPromoteEvent event = new FactionPromoteEventImpl.Post(promotedBy, promotedPlayer, faction, promotedToRank, cause);
        return Sponge.getEventManager().post(event);
    }

    public static boolean runFactionInviteEvent(final Player inviter, final Player invited, final Faction faction)
    {
        final EventContext eventContext = getEventContextForPlayer(inviter).build();
        final Cause cause = Cause.of(eventContext, inviter, faction, invited);
        final FactionInviteEvent event = new FactionInviteEventImpl(inviter, invited, faction, cause);
        return Sponge.getEventManager().post(event);
    }

    private static EventContext.Builder getEventContextForPlayer(final Player player)
    {
        return EventContext.builder().add(EventContextKeys.OWNER, player).add(EventContextKeys.PLAYER, player).add(EventContextKeys.CREATOR, player);
    }
}

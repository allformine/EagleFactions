package io.github.aquerr.eaglefactions.common.events;

import io.github.aquerr.eaglefactions.api.entities.Faction;
import io.github.aquerr.eaglefactions.api.entities.FactionPlayer;
import io.github.aquerr.eaglefactions.api.events.FactionKickEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;

public class FactionKickEventImpl extends FactionAbstractEvent implements FactionKickEvent
{
    private final FactionPlayer kickedPlayer;

    FactionKickEventImpl(final FactionPlayer kickedPlayer, final Player kickedBy, final Faction faction, final Cause cause)
    {
        super(kickedBy, faction, cause);
        this.kickedPlayer = kickedPlayer;
    }

    public FactionPlayer getKickedPlayer()
    {
        return this.kickedPlayer;
    }
}

package io.github.aquerr.eaglefactions.common.events;

import io.github.aquerr.eaglefactions.api.entities.Faction;
import io.github.aquerr.eaglefactions.api.events.FactionCreateEvent;
import io.github.aquerr.eaglefactions.common.EagleFactionsPlugin;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;

public class FactionCreateEventImpl extends FactionAbstractEvent implements FactionCreateEvent
{
    FactionCreateEventImpl(final Player creator, final Faction faction, final Cause cause)
    {
        super(creator, faction, cause);
    }

    @Override
    public boolean isCreatedByItems()
    {
        //Factions cannot be created differently. All of them are created by items or none of them.
        return EagleFactionsPlugin.getPlugin().getConfiguration().getFactionsConfig().getFactionCreationByItems();
    }
}

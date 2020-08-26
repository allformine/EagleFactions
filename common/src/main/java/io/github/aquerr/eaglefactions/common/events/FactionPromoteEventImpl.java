package io.github.aquerr.eaglefactions.common.events;

import io.github.aquerr.eaglefactions.api.entities.Faction;
import io.github.aquerr.eaglefactions.api.entities.FactionMemberType;
import io.github.aquerr.eaglefactions.api.entities.FactionPlayer;
import io.github.aquerr.eaglefactions.api.events.FactionPromoteEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;

public class FactionPromoteEventImpl extends FactionAbstractEvent implements FactionPromoteEvent
{
    private final FactionPlayer promotedPlayer;

    protected FactionPromoteEventImpl(Player creator, FactionPlayer promotedPlayer, Faction faction, Cause cause)
    {
        super(creator, faction, cause);
        this.promotedPlayer = promotedPlayer;
    }

    public static class Pre extends FactionPromoteEventImpl implements FactionPromoteEvent.Pre
    {
        protected Pre(Player creator, final FactionPlayer promotedPlayer, Faction faction, Cause cause)
        {
            super(creator, promotedPlayer, faction, cause);
        }
    }

    public static class Post extends FactionPromoteEventImpl implements FactionPromoteEvent.Post
    {
        private final FactionMemberType promotedToRank;

        protected Post(Player creator, FactionPlayer promotedPlayer, Faction faction, final FactionMemberType promotedToRank, Cause cause)
        {
            super(creator, promotedPlayer, faction, cause);
            this.promotedToRank = promotedToRank;
        }

        @Override
        public FactionMemberType getPromotedToRank()
        {
            return this.promotedToRank;
        }
    }
}

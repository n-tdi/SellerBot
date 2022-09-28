package world.ntdi.sellerbot.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.ntdi.sellerbot.SellerBot;

import java.util.HashMap;
import java.util.Map;

public class JoinEvent extends ListenerAdapter {
    private final Logger LOGGER = LoggerFactory.getLogger(JoinEvent.class);
    private Map<Invite, Integer> invites = new HashMap<>();

    public JoinEvent() {
        Guild g = SellerBot.getJda.getGuildById("");
        for (Invite invite : g.retrieveInvites().complete()) {
            invites.put(invite, invite.getUses());
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        Guild g = e.getJDA().getGuildById("");
        for (Invite invite : invites.keySet()) {
            if (invite.getUses() > invites.get(invite)) {
                User inviter = invite.getInviter();
                User invited = e.getUser();
                break;
            }
        }
    }

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent e) {
        invites.put(e.getInvite(), e.getInvite().getUses());
    }
}

/*
 * Jacob_'s Capture the Flag for Minecraft Classic and ClassiCube
 * Copyright (c) 2010-2014 Jacob Morgan
 * Based on OpenCraft v0.2
 *
 * OpenCraft License
 * 
 * Copyright (c) 2009 Graham Edgecombe, S�ren Enevoldsen and Brett Russell.
 * All rights reserved.
 *
 * Distribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Distributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *       
 *     * Distributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *       
 *     * Neither the name of the OpenCraft nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.opencraft.server.cmd.impl;

import org.opencraft.server.Server;
import org.opencraft.server.cmd.Command;
import org.opencraft.server.cmd.CommandParameters;
import org.opencraft.server.game.impl.CTFGameMode;
import org.opencraft.server.model.MapController;
import org.opencraft.server.model.Player;
import org.opencraft.server.model.World;

public class StatusCommand implements Command{

    private static final StatusCommand INSTANCE = new StatusCommand();

    /**
     * Gets the singleton instance of this command.
     * @return The singleton instance of this command.
     */
    public static StatusCommand getCommand() {
            return INSTANCE;
    }

    public void execute(Player player, CommandParameters params) {
        int redPlayers = 0;
        int bluePlayers = 0;
        String hasOtherFlag = null;
        String hasOurFlag = null;
        for (Player p : World.getWorld().getPlayerList().getPlayers())
        {
            if(p.team == 0)
                redPlayers++;
            else if(p.team == 1)
                bluePlayers++;
            if(p.hasFlag == true)
            {
                if(p.team == player.team)
                    hasOtherFlag = p.getName();
                else
                    hasOurFlag = p.getName();
            }
        }
        if(hasOtherFlag == null)
            hasOtherFlag = "No one";
        if(hasOurFlag == null)
            hasOurFlag = "No one";
        player.getActionSender().sendChatMessage("- &d"+redPlayers+" players on red:");
        Object[] names = World.getWorld().getPlayerList().getPlayers().toArray();
        if(names.length > 0)
        {
            String msg = "";
            for(Object map : names)
            {
                if(((Player)map).team == 0)
                    msg += ((Player)map).getName()+", ";
            }
            if(!msg.isEmpty())
                player.getActionSender().sendChatMessage("- &c"+msg);
        }
        player.getActionSender().sendChatMessage("- &d"+bluePlayers+" players on blue:");
        Object[] othernames = World.getWorld().getPlayerList().getPlayers().toArray();
        String othermsg = "";
        for(Object map : othernames)
        {
            if(((Player)map).team == 1)
                othermsg += ((Player)map).getName()+", ";
        }
        if(!othermsg.isEmpty())
            player.getActionSender().sendChatMessage("- &c"+othermsg);
        player.getActionSender().sendChatMessage("&a"+hasOtherFlag+" has the other flag.");
        player.getActionSender().sendChatMessage("&a"+hasOurFlag+" has your flag.");
        player.getActionSender().sendChatMessage("&cRed: "+((CTFGameMode)World.getWorld().getGameMode()).getRedCaptures()+" -- Blue: "+((CTFGameMode)World.getWorld().getGameMode()).getBlueCaptures());
    }

}

package de.cubeattack.cuberunner.commands.signs;

import java.util.UUID;

import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.game.Arena;
import org.bukkit.Location;
import org.bukkit.block.Sign;

public abstract class CRSignDisplay extends CRSign {
   protected Arena arena;

   public CRSignDisplay(Location location, CRSign.SignType type) {
      super(location, type);
   }

   public CRSignDisplay(UUID uuid, Location location, CRSign.SignType type) {
      super(uuid, location, type);
   }

   protected abstract void updateDisplay(Language var1, Sign var2);
}

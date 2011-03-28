package redecouverte.farmmaster;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class EPlayerListener extends PlayerListener {

    private static final Logger logger = Logger.getLogger("Minecraft");
    private final FarmMaster parent;
    
    public EPlayerListener(FarmMaster parent) {
        this.parent = parent;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            try {
                if (parent.naturalMode()) {
                    Block b = event.getClickedBlock();

                    if (b.getType() == Material.SOIL) {

                        int y = b.getY() + 1;
                        if (y > 126) {
                            return;
                        }

                        Block plantBlock = b.getWorld().getBlockAt(b.getX(), y, b.getZ());

                        if (parent.addPlantNaturally(plantBlock, event.getItem())) {
                            ItemStack is = new ItemStack(event.getItem().getType());
                            is.setAmount(1);
                            event.getPlayer().getInventory().removeItem(is);
                        }

                    }
                }

            } catch (Exception e) {
                logger.log(Level.WARNING, "FarmMaster: error: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }
}

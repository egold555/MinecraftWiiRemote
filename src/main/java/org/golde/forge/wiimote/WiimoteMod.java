package org.golde.forge.wiimote;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = WiimoteMod.MODID, clientSideOnly = true, name = "Wiimote Mod", version = "1.0.0", useMetadata = true)
public class WiimoteMod {

	public static final String MODID = "wiimote";
	
	private WiimoteHandler wiimoteHandler = new WiimoteHandler();
	private static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		logger.info("Starting mod!");
		if(e.getSide() == Side.CLIENT) {
			
			boolean connected = wiimoteHandler.tryConnect();
			MinecraftForge.EVENT_BUS.register(this);
			logger.info("Connected: " + connected);
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				
				@Override
				public void run() {
					logger.info("Shutting down");
					wiimoteHandler.shutdown();
				}
				
			});
			
		}
		
	}
	
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent e) {
		wiimoteHandler.clientTick();
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
}

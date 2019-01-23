package org.golde.forge.wiimote;

import java.time.chrono.MinguoEra;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.github.awvalenti.wiiusej.WiiusejNativeLibraryLoadingException;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class WiimoteHandler implements WiimoteListener {

	private Minecraft mc = Minecraft.getMinecraft();

	private WiimoteButtonsEvent wiimoteButtonsEvent;

	private WiiUseApiManager wiiApi;
	private Wiimote wiimote;

	public boolean tryConnect() {
		try {
			wiiApi = new WiiUseApiManager();
			Wiimote[] wiimotes = wiiApi.getWiimotes(1);
			if(wiimotes.length > 0) {
				wiimote = wiimotes[0];
				wiimote.activateIRTRacking();
				wiimote.activateMotionSensing();
				wiimote.addWiiMoteEventListeners(this);
				Random rand = new Random();
				wiimote.setLeds(true, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
				WiimoteMod.getLogger().info("Successfully connected to WiiMote!");
				return true;
			}
			WiimoteMod.getLogger().error("Failed to connect to WiiMote! Pair the remote!");
			return false;
		} 
		catch (WiiusejNativeLibraryLoadingException e) {
			WiimoteMod.getLogger().error("Failed to connect to the WiiMote!", e);
			return false;
		}

	}

	public void clientTick() {
		if(Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.movementInput != null) {
			if(Minecraft.getMinecraft().player.movementInput instanceof net.minecraft.util.MovementInputFromOptions) {
				Minecraft.getMinecraft().player.movementInput = new MovementWiiMote(Minecraft.getMinecraft().gameSettings);
				WiimoteMod.getLogger().info("Set movement");
			}
			else if(Minecraft.getMinecraft().player.movementInput instanceof MovementWiiMote) {
				((MovementWiiMote)Minecraft.getMinecraft().player.movementInput).updatePlayerMoveState(wiimoteButtonsEvent);
			}

			if(wiimoteButtonsEvent.isButtonBPressed()) {
				Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
			}

		}
	}

	public void shutdown() {
		wiimote.setLeds(false, true, true, false);
		wiiApi.shutdown();
	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent wiimoteButtonsEvent) {
		this.wiimoteButtonsEvent = wiimoteButtonsEvent;
	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIrEvent(IREvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub

	}

}

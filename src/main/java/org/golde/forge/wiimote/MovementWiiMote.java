package org.golde.forge.wiimote;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;

@SideOnly(Side.CLIENT)
public class MovementWiiMote extends MovementInput {

	private final GameSettings gameSettings;

    public MovementWiiMote(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState(WiimoteButtonsEvent wiibtns)
    {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.isKeyDown() || wiibtns.isButtonRightPressed())
        {
            ++this.moveForward;
            this.forwardKeyDown = true;
        }
        else
        {
            this.forwardKeyDown = false;
        }

        if (this.gameSettings.keyBindBack.isKeyDown() || wiibtns.isButtonLeftPressed())
        {
            --this.moveForward;
            this.backKeyDown = true;
        }
        else
        {
            this.backKeyDown = false;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown() || wiibtns.isButtonUpPressed())
        {
            ++this.moveStrafe;
            this.leftKeyDown = true;
        }
        else
        {
            this.leftKeyDown = false;
        }

        if (this.gameSettings.keyBindRight.isKeyDown() || wiibtns.isButtonDownPressed())
        {
            --this.moveStrafe;
            this.rightKeyDown = true;
        }
        else
        {
            this.rightKeyDown = false;
        }

        this.jump = this.gameSettings.keyBindJump.isKeyDown() || wiibtns.isButtonTwoPressed();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown() || wiibtns.isButtonOnePressed();

        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
	
}

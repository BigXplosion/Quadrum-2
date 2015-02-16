package quadrum.lib.data;

import org.apache.logging.log4j.Level;

import net.minecraft.potion.Potion;

import quadrum.Quadrum;

public class Effect {

	private Potion potion;
	private boolean failed = false;

	public int id;
	public float probability;
	public int duration;
	public int amplifier;

	public Potion getPotionEffect() {
		if (potion == null && !failed) {
			if (id >= 0 && id < Potion.potionTypes.length) {
				Potion potion1 = Potion.potionTypes[id];
				if (potion1 == null) {
					Quadrum.log(Level.WARN, "%s is an invalid potion id", id);
					return null;
				}
				potion = Potion.potionTypes[id];
			} else {
				Quadrum.log(Level.WARN, "%s is an invalid potion id", id);
				failed = true;
			}
		}
		return potion;
	}
}

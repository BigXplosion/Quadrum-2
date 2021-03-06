package quadrum.lib.data;

import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MeltingData {

	private Fluid actualFluid;

	private boolean failed;

	public String fluid;
	public int light;

	public Fluid getFluid() {
		if (actualFluid == null && !failed) {
			for (Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
				if (fluid.equalsIgnoreCase(entry.getKey())) {
					Fluid fluid1 = entry.getValue();
					if (fluid1.getBlock() != null) {
						actualFluid = fluid1;
						return actualFluid;
					} else
						failed = true;
				}
			}
			// LOG
			failed = true;
		}
		return actualFluid;
	}
}

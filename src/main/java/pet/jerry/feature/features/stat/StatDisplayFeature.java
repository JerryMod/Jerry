package pet.jerry.feature.features.stat;

import pet.jerry.data.base.SkyBlock;
import pet.jerry.hud.TextHUDElement;
import pet.jerry.value.BooleanValue;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class StatDisplayFeature extends TextHUDElement {
	private final BooleanValue showSymbol = new BooleanValue("Show symbol", "show_symbol", true);
	private final Function<SkyBlock, Object> valueSupplier;
	private final String symbol;

	public StatDisplayFeature(Function<SkyBlock, Object> valueSupplier) {
		this(valueSupplier, "", 0xFFFFFF);
	}

	public StatDisplayFeature(Function<SkyBlock, Object> valueSupplier, String symbol) {
		this(valueSupplier, symbol, 0xFFFFFF);
	}

	public StatDisplayFeature(Function<SkyBlock, Object> valueSupplier, String symbol, int colour) {
		super(null);
		this.valueSupplier = valueSupplier;
		this.symbol = symbol;
		this.getTextColour().setColour(colour);
		if(!symbol.isEmpty())
			this.add(showSymbol);
	}

	@Override
	protected List<String> getDisplayedText(SkyBlock skyBlock) {
		return Collections.singletonList(this.showSymbol.getValue() ? applySymbol(this.symbol, valueSupplier.apply(skyBlock).toString())
				: valueSupplier.apply(skyBlock).toString());
	}

	protected String applySymbol(String symbol, String value) {
		return symbol + value;
	}
}

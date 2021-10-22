package com.nisovin.shopkeepers.shopobjects.living.types;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.lang.Messages;
import com.nisovin.shopkeepers.property.BooleanProperty;
import com.nisovin.shopkeepers.property.Property;
import com.nisovin.shopkeepers.shopkeeper.AbstractShopkeeper;
import com.nisovin.shopkeepers.shopobjects.ShopObjectData;
import com.nisovin.shopkeepers.shopobjects.living.LivingShops;
import com.nisovin.shopkeepers.shopobjects.living.SKLivingShopObject;
import com.nisovin.shopkeepers.shopobjects.living.SKLivingShopObjectType;
import com.nisovin.shopkeepers.ui.editor.Button;
import com.nisovin.shopkeepers.ui.editor.Session;
import com.nisovin.shopkeepers.ui.editor.ShopkeeperActionButton;
import com.nisovin.shopkeepers.util.data.InvalidDataException;
import com.nisovin.shopkeepers.util.inventory.ItemUtils;

public class CreeperShop extends SKLivingShopObject<Creeper> {

	private final Property<Boolean> poweredProperty = new BooleanProperty()
			.key("powered")
			.defaultValue(false)
			.onValueChanged(this::applyPowered)
			.build(properties);

	public CreeperShop(	LivingShops livingShops, SKLivingShopObjectType<CreeperShop> livingObjectType,
						AbstractShopkeeper shopkeeper, ShopCreationData creationData) {
		super(livingShops, livingObjectType, shopkeeper, creationData);
	}

	@Override
	public void load(ShopObjectData shopObjectData) throws InvalidDataException {
		super.load(shopObjectData);
		poweredProperty.load(shopObjectData);
	}

	@Override
	public void save(ShopObjectData shopObjectData) {
		super.save(shopObjectData);
		poweredProperty.save(shopObjectData);
	}

	@Override
	protected void onSpawn() {
		super.onSpawn();
		this.applyPowered();
	}

	@Override
	public List<Button> createEditorButtons() {
		List<Button> editorButtons = super.createEditorButtons();
		editorButtons.add(this.getPoweredEditorButton());
		return editorButtons;
	}

	// POWERED STATE

	public boolean isPowered() {
		return poweredProperty.getValue();
	}

	public void setPowered(boolean powered) {
		poweredProperty.setValue(powered);
	}

	public void cyclePowered() {
		this.setPowered(!this.isPowered());
	}

	private void applyPowered() {
		Creeper entity = this.getEntity();
		if (entity == null) return; // Not spawned
		entity.setPowered(this.isPowered());
	}

	private ItemStack getPoweredEditorItem() {
		ItemStack iconItem;
		if (this.isPowered()) {
			iconItem = new ItemStack(Material.LIGHT_BLUE_WOOL);
		} else {
			iconItem = new ItemStack(Material.LIME_WOOL);
		}
		ItemUtils.setDisplayNameAndLore(iconItem, Messages.buttonCreeperCharged, Messages.buttonCreeperChargedLore);
		return iconItem;
	}

	private Button getPoweredEditorButton() {
		return new ShopkeeperActionButton() {
			@Override
			public ItemStack getIcon(Session session) {
				return getPoweredEditorItem();
			}

			@Override
			protected boolean runAction(InventoryClickEvent clickEvent, Player player) {
				cyclePowered();
				return true;
			}
		};
	}
}

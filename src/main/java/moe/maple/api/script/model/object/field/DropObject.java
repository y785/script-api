package moe.maple.api.script.model.object.field;

import moe.maple.api.script.model.object.user.ItemObject;

import java.util.Optional;

public interface DropObject<Type, Item> extends FieldedObject<Type> {

    default boolean isMoney() { return getItem().isEmpty(); }

    int getMoney();

    Optional<ItemObject<Item>> getItem();

    default int getItemId() { return getItem().map(ItemObject::getId).orElse(-1); }
}

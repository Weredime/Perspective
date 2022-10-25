package io.github.weredime.mods.perspective;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

@Config(name = "perspective")
public class ModConfig implements ConfigData {
    @Comment("This option lets you toggle freelook on or off.")
    boolean freelookEnabled = true;
    @Comment("The initial perspective to go to when freelook is enabled.")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    PerspectiveSetting initialPerspective = PerspectiveSetting.THIRD_PERSON;

    static enum PerspectiveSetting {
        FIRST_PERSON,
        SECOND_PERSON,
        THIRD_PERSON;
        @Override
        public String toString() {
            switch(this) {
                case FIRST_PERSON: return Text.translatable("text.autoconfig.perspective.option.initialPerspective.FIRST_PERSON").getString();
                case SECOND_PERSON: return Text.translatable("text.autoconfig.perspective.option.initialPerspective.SECOND_PERSON").getString();
                case THIRD_PERSON: return Text.translatable("text.autoconfig.perspective.option.initialPerspective.THIRD_PERSON").getString();
                default: throw new IllegalArgumentException("Invalid enum");
            }
        }
        public Perspective toMCEnum() {
            switch(this) {
                case FIRST_PERSON: return Perspective.FIRST_PERSON;
                case SECOND_PERSON: return Perspective.THIRD_PERSON_FRONT;
                case THIRD_PERSON: return Perspective.THIRD_PERSON_BACK;
                default: throw new IllegalArgumentException("Invalid enum");
            }
        }
    }
}

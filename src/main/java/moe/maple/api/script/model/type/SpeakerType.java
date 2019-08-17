package moe.maple.api.script.model.type;

// Enums are bad.
public enum SpeakerType {
    SAY(0x0),
    SAYIMAGE(0x1),
    ASKYESNO(0x2),
    ASKTEXT(0x3),
    ASKNUMBER(0x4),
    ASKMENU(0x5),
    ASKQUIZ(0x6),
    ASKSPEEDQUIZ(0x7),
    ASKAVATAR(0x8),
    ASKMEMBERSHOPAVATAR(0x9),
    ASKPET(0xA),
    ASKPETALL(0xB),
    SCRIPT(0xC),
    ASKACCEPT(0xD),
    ASKBOXTEXT(0xE),
    ASKSLIDEMENU(0xF),
    ASKCENTER(0x10),
    ;
    public final int value;

    SpeakerType(int value) {
        this.value = value;
    }

    public static SpeakerType getByValue(int value) {
        for (SpeakerType type : values()) {
            if (type.value == value)
                return type;
        }
        throw new IllegalArgumentException("Cant find, wololol");
    }
}

package moe.maple.api.script.model.object.data.safety;

/**
 * @author umbreon22
 * A container for Validators.
 * Created on 8/21/2019.
 */
public interface ValidationHub {
    FaceTemplateValidator getFaceValidator();
    HairTemplateValidator getHairValidator();
    MobTemplateValidator getMobValidator();
    FieldTemplateValidator getFieldValidator();
    SkillValidator getSkillValidator();
}

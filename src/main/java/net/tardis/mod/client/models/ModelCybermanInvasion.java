package net.tardis.mod.client.models;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.tardis.mod.client.util.ModelUtil;

public class ModelCybermanInvasion extends ModelBiped
{
  //fields
    ModelRenderer LampGlow;
    ModelRenderer EyeGlow1;
    ModelRenderer EyeGlow2;
    ModelRenderer Head1;
    ModelRenderer Head2;
    ModelRenderer Head3;
    ModelRenderer Head4;
    ModelRenderer Head5;
    ModelRenderer Head6;
    ModelRenderer Head7;
    ModelRenderer Head8;
    ModelRenderer Head9;
    ModelRenderer Head10;
    ModelRenderer Head11;
    ModelRenderer Head12;
    ModelRenderer Head13;
    ModelRenderer Head14;
    ModelRenderer Head15;
    ModelRenderer Head16;
    ModelRenderer Head17;
    ModelRenderer Head18;
    ModelRenderer Head19;
    ModelRenderer Head20;
    ModelRenderer Head21;
    ModelRenderer Head22;
    ModelRenderer Head23;
    ModelRenderer Neck;
    ModelRenderer Torso;
    ModelRenderer ChestUnitGlow;
    ModelRenderer ChestUnit1;
    ModelRenderer ChestUnit2;
    ModelRenderer ChestUnit3;
    ModelRenderer ChestUnit4;
    ModelRenderer ChestUnit5;
    ModelRenderer ChestUnit6;
    ModelRenderer ChestUnit7;
    ModelRenderer Crotch;
    ModelRenderer LeftArm;
    ModelRenderer RightArm;
    ModelRenderer LeftLeg;
    ModelRenderer LeftToe;
    ModelRenderer LeftBoot;
    ModelRenderer LeftLeg1;
    ModelRenderer LeftToe1;
    ModelRenderer LeftBoot1;
    
  
  public ModelCybermanInvasion()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      LampGlow = new ModelRenderer(this, 66, 9);
      LampGlow.addBox(-0.5F, -8F, -3.2F, 1, 1, 1);
      LampGlow.setRotationPoint(0F, -0.5F, 0F);
      LampGlow.setTextureSize(128, 128);
      LampGlow.mirror = true;
      setRotation(LampGlow, 0F, 0F, 0F);
      EyeGlow1 = new ModelRenderer(this, 46, 8);
      EyeGlow1.addBox(-1.5F, -4F, -3.2F, 1, 1, 1);
      EyeGlow1.setRotationPoint(0F, -0.5F, 0F);
      EyeGlow1.setTextureSize(128, 128);
      EyeGlow1.mirror = true;
      setRotation(EyeGlow1, 0F, 0.0872665F, 0F);
      EyeGlow2 = new ModelRenderer(this, 46, 8);
      EyeGlow2.addBox(0.5F, -4F, -3.2F, 1, 1, 1);
      EyeGlow2.setRotationPoint(0F, -0.5F, 0F);
      EyeGlow2.setTextureSize(128, 128);
      EyeGlow2.mirror = true;
      setRotation(EyeGlow2, 0F, -0.0872665F, 0F);
      Head1 = new ModelRenderer(this, 68, 0);
      Head1.addBox(0F, -6F, -1F, 2, 1, 1);
      Head1.setRotationPoint(1.5F, -0.5F, 3.5F);
      Head1.setTextureSize(128, 128);
      Head1.mirror = true;
      setRotation(Head1, 0F, 0.7853982F, 0F);
      Head2 = new ModelRenderer(this, 46, 0);
      Head2.addBox(-1.5F, -6F, -3.5F, 3, 1, 7);
      Head2.setRotationPoint(0F, -0.5F, 0F);
      Head2.setTextureSize(128, 128);
      Head2.mirror = true;
      setRotation(Head2, 0F, 0F, 0F);
      Head3 = new ModelRenderer(this, 68, 0);
      Head3.addBox(-2F, -6F, -1F, 2, 1, 1);
      Head3.setRotationPoint(-1.5F, -0.5F, 3.5F);
      Head3.setTextureSize(128, 128);
      Head3.mirror = true;
      setRotation(Head3, 0F, -0.7853982F, 0F);
      Head4 = new ModelRenderer(this, 40, 0);
      Head4.addBox(-2F, -7F, -1F, 2, 7, 1);
      Head4.setRotationPoint(-1.5F, -0.5F, 3F);
      Head4.setTextureSize(128, 128);
      Head4.mirror = true;
      setRotation(Head4, 0F, -0.7853982F, 0F);
      Head5 = new ModelRenderer(this, 40, 0);
      Head5.addBox(0F, -7F, -1F, 2, 7, 1);
      Head5.setRotationPoint(1.5F, -0.5F, 3F);
      Head5.setTextureSize(128, 128);
      Head5.mirror = true;
      setRotation(Head5, 0F, 0.7853982F, 0F);
      Head6 = new ModelRenderer(this, 40, 0);
      Head6.addBox(-2F, -7F, 0F, 2, 7, 1);
      Head6.setRotationPoint(-1.5F, -0.5F, -3F);
      Head6.setTextureSize(128, 128);
      Head6.mirror = true;
      setRotation(Head6, 0F, 0.7853982F, 0F);
      Head7 = new ModelRenderer(this, 40, 0);
      Head7.addBox(0F, -7F, 0F, 2, 7, 1);
      Head7.setRotationPoint(1.5F, -0.5F, -3F);
      Head7.setTextureSize(128, 128);
      Head7.mirror = true;
      setRotation(Head7, 0F, -0.7853982F, 0F);
      Head8 = new ModelRenderer(this, 68, 0);
      Head8.addBox(0F, -6F, 0F, 2, 1, 1);
      Head8.setRotationPoint(1.5F, -0.5F, -3.5F);
      Head8.setTextureSize(128, 128);
      Head8.mirror = true;
      setRotation(Head8, 0F, -0.7853982F, 0F);
      Head9 = new ModelRenderer(this, 68, 0);
      Head9.addBox(-2F, -6F, 0F, 2, 1, 1);
      Head9.setRotationPoint(-1.5F, -0.5F, -3.5F);
      Head9.setTextureSize(128, 128);
      Head9.mirror = true;
      setRotation(Head9, 0F, 0.7853982F, 0F);
      Head10 = new ModelRenderer(this, 68, 15);
      Head10.addBox(-2F, -8.6F, -1F, 4, 2, 2);
      Head10.setRotationPoint(0F, -0.5F, 0F);
      Head10.setTextureSize(128, 128);
      Head10.mirror = true;
      setRotation(Head10, 0F, 0F, 0F);
      Head11 = new ModelRenderer(this, 20, 11);
      Head11.addBox(-5F, -3.5F, -0.5F, 10, 1, 1);
      Head11.setRotationPoint(0F, -0.5F, 0F);
      Head11.setTextureSize(128, 128);
      Head11.mirror = true;
      setRotation(Head11, 0F, 0F, 0F);
      Head12 = new ModelRenderer(this, 20, 11);
      Head12.addBox(-5F, -8.5F, -0.5F, 10, 1, 1);
      Head12.setRotationPoint(0F, -0.5F, 0F);
      Head12.setTextureSize(128, 128);
      Head12.mirror = true;
      setRotation(Head12, 0F, 0F, 0F);
      Head13 = new ModelRenderer(this, 68, 2);
      Head13.addBox(-5.5F, -8F, -0.5F, 1, 5, 1);
      Head13.setRotationPoint(0F, -0.5F, 0F);
      Head13.setTextureSize(128, 128);
      Head13.mirror = true;
      setRotation(Head13, 0F, 0F, 0F);
      Head14 = new ModelRenderer(this, 68, 2);
      Head14.addBox(4.5F, -8F, -0.5F, 1, 5, 1);
      Head14.setRotationPoint(0F, -0.5F, 0F);
      Head14.setTextureSize(128, 128);
      Head14.mirror = true;
      setRotation(Head14, 0F, 0F, 0F);
      Head15 = new ModelRenderer(this, 74, 0);
      Head15.addBox(-3F, -7.5F, -3F, 1, 8, 6);
      Head15.setRotationPoint(0F, -0.5F, 0F);
      Head15.setTextureSize(128, 128);
      Head15.mirror = true;
      setRotation(Head15, 0F, 0F, 0F);
      Head16 = new ModelRenderer(this, 92, 15);
      Head16.addBox(-3.5F, -7F, -2.5F, 1, 7, 5);
      Head16.setRotationPoint(0F, -0.5F, 0F);
      Head16.setTextureSize(128, 128);
      Head16.mirror = true;
      setRotation(Head16, 0F, 0F, 0F);
      Head17 = new ModelRenderer(this, 20, 0);
      Head17.addBox(-2.5F, -7F, -2F, 5, 6, 4);
      Head17.setRotationPoint(0F, -0.5F, 0F);
      Head17.setTextureSize(128, 128);
      Head17.mirror = true;
      setRotation(Head17, 0F, 0F, 0F);
      Head18 = new ModelRenderer(this, 110, 0);
      Head18.addBox(-3F, 0F, -3F, 3, 1, 6);
      Head18.setRotationPoint(-1F, -9F, 0F);
      Head18.setTextureSize(128, 128);
      Head18.mirror = true;
      setRotation(Head18, 0F, 0F, -1.396263F);
      Head19 = new ModelRenderer(this, 110, 0);
      Head19.addBox(0F, 0F, -3F, 3, 1, 6);
      Head19.setRotationPoint(1F, -9F, 0F);
      Head19.setTextureSize(128, 128);
      Head19.mirror = true;
      setRotation(Head19, 0F, 0F, 1.396263F);
      Head20 = new ModelRenderer(this, 50, 8);
      Head20.addBox(-1F, -8.5F, -3F, 2, 2, 6);
      Head20.setRotationPoint(0F, -0.5F, 0F);
      Head20.setTextureSize(128, 128);
      Head20.mirror = true;
      setRotation(Head20, 0F, 0F, 0F);
      Head21 = new ModelRenderer(this, 0, 0);
      Head21.addBox(-1.5F, -7F, -3F, 3, 7, 6);
      Head21.setRotationPoint(0F, -0.5F, 0F);
      Head21.setTextureSize(128, 128);
      Head21.mirror = true;
      setRotation(Head21, 0F, 0F, 0F);
      Head22 = new ModelRenderer(this, 74, 0);
      Head22.addBox(2F, -7.5F, -3F, 1, 8, 6);
      Head22.setRotationPoint(0F, -0.5F, 0F);
      Head22.setTextureSize(128, 128);
      Head22.mirror = true;
      setRotation(Head22, 0F, 0F, 0F);
      Head23 = new ModelRenderer(this, 92, 15);
      Head23.addBox(2.5F, -7F, -2.5F, 1, 7, 5);
      Head23.setRotationPoint(0F, -0.5F, 0F);
      Head23.setTextureSize(128, 128);
      Head23.mirror = true;
      setRotation(Head23, 0F, 0F, 0F);
      Neck = new ModelRenderer(this, 16, 51);
      Neck.addBox(-2F, -1F, -1.5F, 4, 1, 3);
      Neck.setRotationPoint(0F, 0F, 0F);
      Neck.setTextureSize(128, 128);
      Neck.mirror = true;
      setRotation(Neck, 0F, 0F, 0F);
      Torso = new ModelRenderer(this, 16, 16);
      Torso.addBox(-3.5F, 0F, -2F, 7, 12, 4);
      Torso.setRotationPoint(0F, 0F, 0F);
      Torso.setTextureSize(128, 128);
      Torso.mirror = true;
      setRotation(Torso, 0F, 0F, 0F);
      ChestUnitGlow = new ModelRenderer(this, 16, 41);
      ChestUnitGlow.addBox(-0.5F, -0.5F, -0.7F, 1, 1, 1);
      ChestUnitGlow.setRotationPoint(0F, 3F, -3F);
      ChestUnitGlow.setTextureSize(128, 128);
      ChestUnitGlow.mirror = true;
      setRotation(ChestUnitGlow, 0F, 0F, 0.7853982F);
      ChestUnit1 = new ModelRenderer(this, 16, 32);
      ChestUnit1.addBox(-1F, 2F, -3.5F, 2, 7, 2);
      ChestUnit1.setRotationPoint(0F, 0F, 0F);
      ChestUnit1.setTextureSize(128, 128);
      ChestUnit1.mirror = true;
      setRotation(ChestUnit1, 0F, 0F, 0F);
      ChestUnit2 = new ModelRenderer(this, 26, 32);
      ChestUnit2.addBox(-2F, 1F, 0F, 2, 6, 2);
      ChestUnit2.setRotationPoint(-1F, 2F, -3F);
      ChestUnit2.setTextureSize(128, 128);
      ChestUnit2.mirror = true;
      setRotation(ChestUnit2, 0F, 0.1745329F, 0F);
      ChestUnit3 = new ModelRenderer(this, 34, 32);
      ChestUnit3.addBox(-1.5F, 3.5F, 0.2F, 1, 2, 1);
      ChestUnit3.setRotationPoint(-1F, 0F, -3.5F);
      ChestUnit3.setTextureSize(128, 128);
      ChestUnit3.mirror = true;
      setRotation(ChestUnit3, 0F, 0.1745329F, 0F);
      ChestUnit4 = new ModelRenderer(this, 22, 41);
      ChestUnit4.addBox(-1.5F, 6.5F, 0.2F, 1, 2, 1);
      ChestUnit4.setRotationPoint(-1F, 0F, -3.5F);
      ChestUnit4.setTextureSize(128, 128);
      ChestUnit4.mirror = true;
      setRotation(ChestUnit4, 0F, 0.1745329F, 0F);
      ChestUnit5 = new ModelRenderer(this, 26, 32);
      ChestUnit5.addBox(0F, 1F, 0F, 2, 6, 2);
      ChestUnit5.setRotationPoint(1F, 2F, -3F);
      ChestUnit5.setTextureSize(128, 128);
      ChestUnit5.mirror = true;
      setRotation(ChestUnit5, 0F, -0.1745329F, 0F);
      ChestUnit6 = new ModelRenderer(this, 34, 32);
      ChestUnit6.addBox(0.5F, 3.5F, 0.2F, 1, 2, 1);
      ChestUnit6.setRotationPoint(1F, 0F, -3.5F);
      ChestUnit6.setTextureSize(128, 128);
      ChestUnit6.mirror = true;
      setRotation(ChestUnit6, 0F, -0.1745329F, 0F);
      ChestUnit7 = new ModelRenderer(this, 22, 41);
      ChestUnit7.addBox(0.5F, 6.5F, 0.2F, 1, 2, 1);
      ChestUnit7.setRotationPoint(1F, 0F, -3.5F);
      ChestUnit7.setTextureSize(128, 128);
      ChestUnit7.mirror = true;
      setRotation(ChestUnit7, 0F, -0.1745329F, 0F);
      Crotch = new ModelRenderer(this, 11, 46);
      Crotch.addBox(-3.5F, 12F, -1.5F, 7, 1, 3);
      Crotch.setRotationPoint(0F, 0F, 0F);
      Crotch.setTextureSize(128, 128);
      Crotch.mirror = true;
      setRotation(Crotch, 0F, 0F, 0F);
      LeftArm = new ModelRenderer(this, 51, 16);
      LeftArm.addBox(0F, -1F, -1.5F, 2, 13, 3);
      LeftArm.setRotationPoint(3.5F, 1.5F, 0F);
      LeftArm.setTextureSize(128, 128);
      LeftArm.mirror = true;
      setRotation(LeftArm, 0F, 0F, -0.0174533F);
      RightArm = new ModelRenderer(this, 40, 16);
      RightArm.addBox(-2F, -1F, -1.5F, 2, 13, 3);
      RightArm.setRotationPoint(-3.5F, 1.5F, 0F);
      RightArm.setTextureSize(128, 128);
      RightArm.mirror = true;
      setRotation(RightArm, 0F, 0F, 0.0174533F);
      LeftLeg = new ModelRenderer(this, 0, 32);
      LeftLeg.addBox(-1.5F, 0F, -1.5F, 3, 11, 3);
      LeftLeg.setRotationPoint(2F, 13F, 0F);
      LeftLeg.setTextureSize(128, 128);
      LeftLeg.mirror = true;
      setRotation(LeftLeg, 0F, 0F, 0F);
      LeftToe = new ModelRenderer(this, 0, 50);
      LeftToe.addBox(-1.5F, 9.5F, -2F, 3, 1, 1);
      LeftToe.setRotationPoint(2F, 13F, 0F);
      LeftToe.setTextureSize(128, 128);
      LeftToe.mirror = true;
      setRotation(LeftToe, 0F, 0F, 0F);
      LeftBoot = new ModelRenderer(this, 0, 48);
      LeftBoot.addBox(-1.5F, 10F, -2.5F, 3, 1, 1);
      LeftBoot.setRotationPoint(2F, 13F, 0F);
      LeftBoot.setTextureSize(128, 128);
      LeftBoot.mirror = true;
      setRotation(LeftBoot, 0F, 0F, 0F);
      LeftLeg1 = new ModelRenderer(this, 0, 16);
      LeftLeg1.addBox(-1.5F, 0F, -1.5F, 3, 11, 3);
      LeftLeg1.setRotationPoint(-2F, 13F, 0F);
      LeftLeg1.setTextureSize(128, 128);
      LeftLeg1.mirror = true;
      setRotation(LeftLeg1, 0F, 0F, 0F);
      LeftToe1 = new ModelRenderer(this, 0, 50);
      LeftToe1.addBox(-1.5F, 9.5F, -2F, 3, 1, 1);
      LeftToe1.setRotationPoint(-2F, 13F, 0F);
      LeftToe1.setTextureSize(128, 128);
      LeftToe1.mirror = true;
      setRotation(LeftToe1, 0F, 0F, 0F);
      LeftBoot1 = new ModelRenderer(this, 0, 48);
      LeftBoot1.addBox(-1.5F, 10F, -2.5F, 3, 1, 1);
      LeftBoot1.setRotationPoint(-2F, 13F, 0F);
      LeftBoot1.setTextureSize(128, 128);
      LeftBoot1.mirror = true;
      setRotation(LeftBoot1, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
	this.bipedHead.isHidden = true;
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    try {
    	Field[] fields = ModelCybermanInvasion.class.getDeclaredFields();
    	for(Field fi : fields) {
    		Object obj = fi.get(this);
    		if(obj != null && obj instanceof ModelRenderer) {
    			if(fi.getName().toLowerCase().contains("head")) {
    				ModelUtil.setChild(this.bipedHead, (ModelRenderer)obj);
    			}
    		}
    	}
    }
    catch(Exception e) {}
    ModelUtil.setChild(this.bipedHead, this.LampGlow);
    ModelUtil.setChild(this.bipedHead, this.EyeGlow1);
    ModelUtil.setChild(this.bipedHead, this.EyeGlow2);
    ModelUtil.setChild(this.bipedBody, this.Torso);
    LampGlow.render(f5);
    EyeGlow1.render(f5);
    EyeGlow2.render(f5);
    Head1.render(f5);
    Head2.render(f5);
    Head3.render(f5);
    Head4.render(f5);
    Head5.render(f5);
    Head6.render(f5);
    Head7.render(f5);
    Head8.render(f5);
    Head9.render(f5);
    Head10.render(f5);
    Head11.render(f5);
    Head12.render(f5);
    Head13.render(f5);
    Head14.render(f5);
    Head15.render(f5);
    Head16.render(f5);
    Head17.render(f5);
    Head18.render(f5);
    Head19.render(f5);
    Head20.render(f5);
    Head21.render(f5);
    Head22.render(f5);
    Head23.render(f5);
    Neck.render(f5);
    Torso.render(f5);
    ChestUnitGlow.render(f5);
    ChestUnit1.render(f5);
    ChestUnit2.render(f5);
    ChestUnit3.render(f5);
    ChestUnit4.render(f5);
    ChestUnit5.render(f5);
    ChestUnit6.render(f5);
    ChestUnit7.render(f5);
    Crotch.render(f5);
    LeftArm.render(f5);
    RightArm.render(f5);
    LeftLeg.render(f5);
    LeftToe.render(f5);
    LeftBoot.render(f5);
    LeftLeg.render(f5);
    LeftToe.render(f5);
    LeftBoot.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}

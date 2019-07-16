package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooLcdDisplay extends AbstractElegoo {
	
	public ElegooLcdDisplay(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super("LCD", blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
		String lineNo = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String charNo = tb.toCode();
		
		String ret = "";
		if ( !(charNo.equals("0") && lineNo.equals("0")) ){
			ret = "lcd.setCursor( (" + charNo + ") - 1, (" + lineNo + ") - 1 );";
		}
		
		tb = this.getRequiredTranslatorBlockAtSocket(0, "lcd.print(", ");\n");
		ret += tb.toCode();
		//Deal with line and character positioning

		translator.addHeaderFile("LiquidCrystal_I2C.h");

		translator.addDefinitionCommand("LiquidCrystal_I2C lcd(0x3F, 16, 2);");
		translator.addSetupCommand("lcd.init();");
		translator.addSetupCommand("lcd.backlight();");
		
		return ret;
	}
	
}
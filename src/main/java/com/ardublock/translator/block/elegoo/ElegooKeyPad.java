package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooKeyPad extends AbstractElegoo {

	public ElegooKeyPad(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super("Clavier", blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException {
		ArgValueResult startPin = getArgValue(0);
		if (startPin.argType != ArgType.number)
			throw new BlockException(blockId, controlDisplayName + " - la 1ere broche doit être un nombre");
		int firstPin = Integer.parseInt(startPin.value);

		translator.addHeaderFile("Keypad.h");
		String definitions = "// Installer la Librairie KeyPad dans le Gestionnaire de librairie\n"
				+ "const byte KEYPAD_ROWS = 4;\n" //
				+ "const byte KEYPAD_COLS = 4;\n" //
				+ "char KEYPAD_KEYS[KEYPAD_ROWS][KEYPAD_COLS] = {\n" + "{'1', '2', '3', 'A'},\n" //
				+ "{'4', '5', '6', 'B'},\n" //
				+ "{'7', '8', '9', 'C'},\n" //
				+ "{'*', '0', '#', 'D'}\n" //
				+ "};\n" //
				+ "byte KEYPAD_ROW_PINS[KEYPAD_ROWS] = {";
		for (int i = 0; i < 4; i++) {
			if (i > 0)
				definitions += ", ";
			definitions += "" + (firstPin + i);
		}

		definitions += "};\n" + "byte KEYPAD_COL_PINS[KEYPAD_COLS] = {";
		for (int i = 0; i < 4; i++) {
			if (i > 0)
				definitions += ", ";
			definitions += "" + (firstPin + i + 4);
		}
		definitions += "};\n"
				+ "Keypad KEYPAD_Instance = Keypad( makeKeymap(KEYPAD_KEYS), KEYPAD_ROW_PINS, KEYPAD_COL_PINS, KEYPAD_ROWS, KEYPAD_COLS);\n"
				+ "char KEYPAD_LastChar = \"\";\n" //
				+ "boolean KEYPAD_isKeyPressed() {\n"//
				+ "	KEYPAD_LastChar = KEYPAD_Instance.getKey();\n" //
				+ "	return KEYPAD_LastChar != '\\0';\n" //
				+ "}\n" //
				+ "char KEYPAD_GetKey() {\n"//
				+ "	return KEYPAD_LastChar;\n"//
				+ "}\n";//

		translator.addDefinitionCommand(definitions);
		return "";
	}
}
